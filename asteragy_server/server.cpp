#include "stdafx.h"
#include "server.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <set>
#include <algorithm>
#include <stdexcept>
#include <cstdlib>
#include <ctime>
#include <boost/bind.hpp>
#include <boost/range.hpp>
#include <boost/algorithm/string/find.hpp>
#include <boost/algorithm/string/split.hpp>
#include <boost/asio.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/implicit_cast.hpp>
#include <boost/ptr_container/ptr_map.hpp>
#include "game.h"

#define USE_IMGAE_DOWNLOADING

namespace as = boost::asio;
namespace algo = boost::algorithm;

using boost::system::error_code;
using boost::lexical_cast;
using boost::implicit_cast;
using std::string;

char const* contentRoot;

namespace
{
	typedef boost::ptr_map<int, Game> game_map_t;
	game_map_t gameMap;

	double lifeLimit = 5 * 60; //単位：秒、この時間以上経過するとGame削除の対象になる。

	template<typename Map, typename Key>
	inline typename Map::mapped_type const& map_at(Map const& m, Key key)
	{
		typename Map::const_iterator it = m.find(key);
		if (it == m.end())
			throw std::out_of_range("Key not found");
		else
			return it->second;
	}

	template<typename Map, typename Key>
	inline typename Map::mapped_type& map_at(Map& m, Key key)
	{
		return const_cast<Map::mapped_type&>(
			map_at(implicit_cast<Map const&>(m), key));
	}

	template<typename Key, typename T, typename Com, typename CA, typename Al>
	inline typename boost::ptr_map<Key, T, Com, CA, Al>::const_mapped_reference
	map_at(boost::ptr_map<Key, T, Com, CA, Al> const& m, Key key)
	{
		typedef boost::ptr_map<Key, T, Com, CA, Al> map_t;
		typename map_t::const_iterator it = m.find(key);
		if (it == m.end())
			throw std::out_of_range("Key not found");
		else
			return *it->second;
	}

	template<typename Key, typename T, typename Com, typename CA, typename Al>
	inline typename boost::ptr_map<Key, T, Com, CA, Al>::mapped_reference
	map_at(boost::ptr_map<Key, T, Com, CA, Al>& m, Key key)
	{
		typedef boost::ptr_map<Key, T, Com, CA, Al> map_t;
		return const_cast<map_t::mapped_reference>(
			map_at(implicit_cast<map_t const&>(m), key));
	}
}

std::set<string> allowedList;

/*
@brief クライアントの接続を待つ。
接続が来たらAcceptHandlerが呼ばれる。
*/
void Server::nextAccept()
{
	connection.reset(new Connection(service));
	acceptor.async_accept(connection->SocketRef(),
		boost::bind(&Server::AcceptHandler, this, as::placeholders::error));
}

/*
@brief 接続が来た。
connection->Start()へ行くと共に、次の接続待ちに入る。
*/
void Server::AcceptHandler(error_code const& e)
{
	if (!e)
	{
		connection->Start();
		nextAccept();
	}
}

/*
@brief 接続開始。まずはHTTPリクエストの受信待ち。
少なくともヘッダと内容の区切り、2つのCR LFが現れるまで読込を行う。
受信できたら、Connection::handleReadへ移る。
*/
void Connection::Start()
{
	asyncReadUntil(&Connection::handleRead, "\r\n\r\n");
}

/*
@brief Connection::StartのasyncReadUntilのハンドラ。
URL引数とHTTPヘッダの解析を行い、適切なメンバ関数へ振り分けたり、
エラーのステータスコードをクライアントに返したりする。

postaction: さらにコンテントを読み込んでからConnection::handleReadPostActionへ。
getaction: Connection::getActionへ。
*/
void Connection::handleRead(error_code const& e, std::size_t)
{
	using std::clog;
	using std::endl;
	using boost::begin;
	using boost::size;

	if (e)
	{
		clog << "Conection::handleRead: " << e.message() << endl;
		return;
	}
	try
	{
		responsed = false;

		std::istream is(&request);
		string requestLine;
		getline(is, requestLine);

		std::vector<string> requestElements;
		requestElements.reserve(3);
		enum {method, path, version}; //上の各要素の内容を表す
		algo::split(requestElements, requestLine, utility::isSpace);
		if (requestElements.empty())
		{
			Start(); //最初の行が空だったらもう1行読み込んでみる。
			return;
		}
		if (requestElements.size() != 3)
		{
			//
		}

		string const& requestPath = requestElements[path];
		if (requestPath.size() == 0 || requestPath[0] != '/')
		{
			returnEmptyResponse(400);
			return;
		}
		else if (requestPath.size() == 1 || requestPath[1] != '?')
		{
			downloadStaticContents(requestPath);
			return;
		}
		string::const_iterator it = begin(requestPath) + 2;
		parseArgs(args, boost::make_iterator_range(it, boost::end(requestPath)));
		perseHeader(header, is);

		//TODO: クライアントがHTTP/1.1を指定しているとき、Hostフィールドの確認。

		map_t::const_iterator scmd = args.find("scmd");
		if (scmd != args.end())
		{
			std::clog << scmd->second << std::endl;
			if (scmd->second == "querygame")
			{
				queryNewGame();
			}
			else
			{
				returnEmptyResponse(400);
			}
			return;
		}

		int gameId = lexical_cast<int>(map_at(args, "id"));
		int player = lexical_cast<int>(map_at(args, "turn"));
		Game& game = map_at(gameMap, gameId);

		string const& cmd = args["cmd"];
		clog << game.GetPlayerCount() << ' ' << cmd << "\t id: " << gameId << "\tturn: " << player << endl;
		if (cmd == "sendaction")
		{
			game.SendAction(args["action"] + "\r\n" + args["delete"], player);
			returnEmptyResponse(200);
			clog << " action: " << args["action"] << "delete: " << args["delete"] << endl;
		}
		else if (cmd == "getaction")
		{
			string s;
			game.GetAction(s, player);
			returnResponse(s);
			std::clog << s << std::endl;
		}
		else if (cmd == "endturn")
		{
			game.EndTurn(player);
			returnEmptyResponse(200);
		}
		else if (cmd == "postoption")
		{
			game.PostOption(args["opt"]);
			returnEmptyResponse(200);
		}
		else if (cmd == "getoption")
		{
			std::string const& opt = game.GetOption();
			if (opt.empty())
				returnEmptyResponse(200);
			else
				returnResponse(opt);
		}
		else if (cmd == "sendinitfield")
		{
			game.SendInitField(args["field"]);
			returnEmptyResponse(200);
		}
		else if (cmd == "getinitfield")
		{
			std::string const& opt = game.GetInitField();
			if (opt.empty())
				returnEmptyResponse(200);
			else
				returnResponse(opt);
		}
		else if (cmd == "querystart")
		{
			if (game.Ready())
			{
				returnResponse("ok\r\n");
			}
			else
			{
				returnEmptyResponse(200);
			}
		}
		else if (cmd == "endturn")
		{
			game.EndTurn(player);
		}
		else
		{
			returnEmptyResponse(400);
		}
		//念のため
		assert(responsed);
		if (!responsed)
		{
			returnEmptyResponse(500);
		}
	}
	catch(std::out_of_range const&)
	{
		//存在しないidを指定されたときを予期
		returnEmptyResponse(400);
	}
	catch(std::exception const& e)
	{
		clog << "Connection::handleRead - 2: " << e.what() << endl;
		//バッファの消去
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
	catch(...)
	{
		clog << "Unknown exception" << endl;
		//バッファの消去
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
}
void Connection::downloadStaticContents(std::string const& requestPath)
{
#ifdef USE_IMGAE_DOWNLOADING
	string localPath = contentRoot + requestPath;

	//ToDo: 怪しいパスを弾く
	
	const char* type;
	string::size_type pos = requestPath.rfind('.');
	if (pos == string::npos)
	{
		type = "application/octet-stream";
	}
	else
	{
		string::const_iterator it = requestPath.begin() + pos + 1;
		if (std::equal(it, requestPath.end(), "txt"))
		{
			type = "text/plain";
		}
		else if (std::equal(it, requestPath.end(), "gif"))
		{
			type = "image/gif";
		}
		else
		{
			type = "application/octet-stream";
		}
	}
	std::clog << "request: " << requestPath << ' ' << type << std::endl;

	std::ifstream is(localPath.c_str(), std::ios::binary);
	if (!is)
	{
		returnEmptyResponse(404);
	}
	else
	{
		returnStreamResponse(is, type);
	}
#else
	returnEmptyResponse(404);
#endif
}

/*
@brief 書込終了時のハンドラ（応答の必要ないもの用）
*/
void Connection::handleWrite(error_code const& e, std::size_t)
{
	if (e)
	{
		std::clog << "Conection::handleWrite: " << e.message() << std::endl;
	}
	else
	{
		error_code ignored_ec;
		socket.shutdown(tcp::socket::shutdown_both, ignored_ec);
	}
}

void Connection::queryNewGame()
{
	static int playId = -1;
	std::ostringstream os;
	if (playId < 0)
	{
		playId = std::rand();
		gameMap.insert(playId, new Game);
	}
	Game& g = gameMap[playId];
	int turnId = g.JoinPlayer();
	os << playId << "\r\n" << turnId << "\r\n";
	returnResponse(os.str());
	std::clog << os.str() << std::endl;
	if (turnId >= 1)
	{
		playId = -1;
	}
}

void RemoveGame()
{
	for (game_map_t::iterator it = gameMap.begin(); it != gameMap.end(); )
	{
		if (std::fabs(std::difftime(std::time(0), it->second->LastAccessTime())) > lifeLimit)
		{
			gameMap.erase(it++);
		}
		else
		{
			++it;
		}
	}
}
