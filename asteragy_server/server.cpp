#include "stdafx.h"
#include "server.h"
#include <iostream>
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
using std::string;

namespace
{
	Game game; //�b��
	int player = 0;
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
		boost::iterator_range<char const*> my_url = boost::as_literal("/?");
		if (!std::equal(begin(my_url), end(my_url), begin(requestPath)))
		{
			if (size(requestPath) > 0 && requestPath[0] != '/')
			{
				returnEmptyResponse(400);
			}
			else
			{
				returnEmptyResponse(404);
			}
			return;
		}
		string::const_iterator it = begin(requestPath) + size(my_url);
		parseArgs(args, boost::make_iterator_range(it, boost::end(requestPath)));
		perseHeader(header, is);

		//TODO: �N���C�A���g��HTTP/1.1���w�肵�Ă���Ƃ��AHost�t�B�[���h�̊m�F�B

		map_t::const_iterator scmd = args.find("scmd");
		if (scmd != args.end())
		{
			if (scmd->second == "querygame")
			{
				//queryNewGame();
			}
		}

		map_t::const_iterator posId = args.find("id");
		if (posId == args.end())
		{
			returnEmptyResponse(400);
			return;
		}
		int gameId = boost::lexical_cast<int>(posId->second);
		int player = gameId & 1;

		string const& cmd = args["cmd"];
		if (cmd == "postaction")
		{
			asyncReadUntil(&Connection::handleReadPostAction, "\r\n");
		}
		else if (cmd == "getaction")
		{
			getActionFromConsole(); //getAction();
		}
		else if (cmd == "endturn")
		{
			game.EndTurn(player);
		}
		else
		{
			returnEmptyResponse(400);
		}
	}
	catch(std::exception const& e)
	{
		clog << "Connection::handleRead - 2: " << e.what() << endl;
		//�o�b�t�@�̏���
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
}

/*
@brief postaction�ŁA���e����M�����Ƃ��ɌĂ΂��B
*/
void Connection::handleReadPostAction(error_code const& e, std::size_t)
{
	if (!e)
	{
		std::istream is(&request);
		std::string s;
		while (std::getline(is, s))
		{
			game.PostAction(s, player);
		}
		std::cout << "postaction ���: " << s << std::endl;
		returnEmptyResponse(200);
	}
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

/*
@brief getaction�̉������s���B
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
@brief �R���\�[������getaction�̉������s���B
*/
void Connection::getActionFromConsole()
{
	std::cout << "get action> ";
	string s;
	getline(std::cin, s);
	returnResponse(s);
}
