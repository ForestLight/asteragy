#include "stdafx.h"
#include "server.h"
#include <iostream>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>
#include <boost/bind.hpp>
#include <boost/range.hpp>
#include <boost/algorithm/string/find.hpp>
#include <boost/algorithm/string/split.hpp>
#include <boost/asio.hpp>
#include <boost/lexical_cast.hpp>
#include "game.h"

namespace as = boost::asio;
namespace algo = boost::algorithm;

using boost::system::error_code;
using boost::lexical_cast;
using std::string;

namespace
{
	Game game; //暫定
	int player = 0;
}

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
	using std::string;
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
			returnEmptyResponse(404);
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

		map_t::const_iterator itId = args.find("id");
		if (itId == args.end())
		{
			returnEmptyResponse(400);
			return;
		}
		int gameId = boost::lexical_cast<int>(itId->second);
		map_t::const_iterator itPlayer = args.find("turn");
		if (itPlayer == args.end())
		{
			returnEmptyResponse(400);
			return;
		}
		int player = boost::lexical_cast<int>(itPlayer->second);

		string const& cmd = args["cmd"];
		std::clog << cmd << "\tturn:" << player << std::endl;
		if (cmd == "sendaction")
		{
			game.SendAction(args["action"] + "\r\n" + args["delete"], player);
			returnEmptyResponse(200);
			std::clog << " action: " << args["action"] << "delete: " << args["delete"] << std::endl;
		}
		else if (cmd == "getaction")
		{
			//getActionFromConsole();
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

/*
@brief getactionの応答を行う。
*/
void Connection::getAction()
{
	string s;
	if (game.GetAction(s, player))
	{
		returnResponse(s);
	}
	else
	{
		returnEmptyResponse(400);
	}
}

/*
@brief コンソールからgetactionの応答を行う。
*/
void Connection::getActionFromConsole()
{
	std::cout << "get action> ";
	string s;
	getline(std::cin, s);
	returnResponse(s);
}

void Connection::queryNewGame()
{
	static int player = 0;
	std::ostringstream os;
	os << "0\r\n" << player << "\r\n";
	returnResponse(os.str());
	game.JoinPlayer();
	player = !player;
}
