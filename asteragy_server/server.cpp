#include "stdafx.h"
#include "server.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>
#include <cstdlib>
#include <ctime>
#include <boost/bind.hpp>
#include <boost/range.hpp>
#include <boost/algorithm/string/find.hpp>
#include <boost/algorithm/string/split.hpp>
#include <boost/asio.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/ptr_container/ptr_map.hpp>
#include "game.h"

namespace as = boost::asio;
namespace algo = boost::algorithm;

using boost::system::error_code;
using boost::lexical_cast;
using std::string;

char const* contentRoot;

namespace
{
	typedef boost::ptr_map<int, Game> game_map_t;
	game_map_t gameMap;

	double lifeLimit = 5 * 60; //�P�ʁF�b�A���̎��Ԉȏ�o�߂����Game�폜�̑ΏۂɂȂ�B
}

/*
@brief �N���C�A���g�̐ڑ���҂B
�ڑ���������AcceptHandler���Ă΂��B
*/
void Server::nextAccept()
{
	connection.reset(new Connection(service));
	acceptor.async_accept(connection->SocketRef(),
		boost::bind(&Server::AcceptHandler, this, as::placeholders::error));
}

/*
@brief �ڑ��������B
connection->Start()�֍s���Ƌ��ɁA���̐ڑ��҂��ɓ���B
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
@brief �ڑ��J�n�B�܂���HTTP���N�G�X�g�̎�M�҂��B
���Ȃ��Ƃ��w�b�_�Ɠ��e�̋�؂�A2��CR LF�������܂œǍ����s���B
��M�ł�����AConnection::handleRead�ֈڂ�B
*/
void Connection::Start()
{
	asyncReadUntil(&Connection::handleRead, "\r\n\r\n");
}

/*
@brief Connection::Start��asyncReadUntil�̃n���h���B
URL������HTTP�w�b�_�̉�͂��s���A�K�؂ȃ����o�֐��֐U�蕪������A
�G���[�̃X�e�[�^�X�R�[�h���N���C�A���g�ɕԂ����肷��B

postaction: ����ɃR���e���g��ǂݍ���ł���Connection::handleReadPostAction�ցB
getaction: Connection::getAction�ցB
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
		enum {method, path, version}; //��̊e�v�f�̓��e��\��
		algo::split(requestElements, requestLine, utility::isSpace);
		if (requestElements.empty())
		{
			Start(); //�ŏ��̍s���󂾂��������1�s�ǂݍ���ł݂�B
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

		//TODO: �N���C�A���g��HTTP/1.1���w�肵�Ă���Ƃ��AHost�t�B�[���h�̊m�F�B

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
		Game& game = gameMap[gameId];
		map_t::const_iterator itPlayer = args.find("turn");
		if (itPlayer == args.end())
		{
			returnEmptyResponse(400);
			return;
		}
		int player = boost::lexical_cast<int>(itPlayer->second);

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
		//�O�̂���
		assert(responsed);
		if (!responsed)
		{
			returnEmptyResponse(500);
		}
	}
	catch(std::exception const& e)
	{
		clog << "Connection::handleRead - 2: " << e.what() << endl;
		//�o�b�t�@�̏���
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
	catch(...)
	{
		clog << "Unknown exception" << endl;
		//�o�b�t�@�̏���
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
}
void Connection::downloadStaticContents(std::string const& requestPath)
{
#ifdef USE_IMGAE_DOWNLOADING
	string localPath = contentRoot + requestPath;

	//ToDo: �������p�X��e��
	
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
@brief �����I�����̃n���h���i�����̕K�v�Ȃ����̗p�j
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
