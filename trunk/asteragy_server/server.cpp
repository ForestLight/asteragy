#include "stdafx.h"
#include "server.h"

namespace as = boost::asio;
namespace algo = boost::algorithm;

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
void Server::AcceptHandler(boost::system::error_code const& e)
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
void Connection::handleRead(boost::system::error_code const& e, std::size_t)
{
	using std::clog;
	using std::endl;
	using std::string;

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
		using std::ctype;
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
		if (!boost::equal(my_url, boost::begin(requestPath)))
		{
			if (boost::size(requestPath) > 0 && requestPath[0] != '/')
			{
				returnEmptyResponse(400);
			}
			else
			{
				returnEmptyResponse(404);
			}
			return;
		}
		string::const_iterator it = boost::begin(requestPath) + boost::size(my_url);
		parseArgs(args, boost::make_iterator_range(it, boost::end(requestPath)));
		perseHeader(header, is);

		//TODO: �N���C�A���g��HTTP/1.1���w�肵�Ă���Ƃ��AHost�t�B�[���h�̊m�F�B

		string const& cmd = args["cmd"];
		if (cmd == "postaction")
		{
			asyncReadUntil(&Connection::handleReadPostAction, "\r\n");
		}
		else if (cmd == "getaction")
		{
			getAction();
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
void Connection::handleReadPostAction(boost::system::error_code const& e, std::size_t bytesTransferred)
{
	std::istream is(&request);
	std::string s; //�b��
	std::getline(is, s);
	std::cout << "postaction ���: " << s << std::endl;
}

/*
@brief �����I�����̃n���h���i�����̕K�v�Ȃ����̗p�j
*/
void Connection::handleWrite(boost::system::error_code const& e, std::size_t)
{
	if (e)
	{
		std::clog << "Conection::handleWrite: " << e.message() << std::endl;
	}
	else
	{
		boost::system::error_code ignored_ec;
		socket.shutdown(tcp::socket::shutdown_both, ignored_ec);
	}
}

/*
@brief getaction�̉������s���B
���݁A�b��I�ɃR���\�[������A�N�V����������̓��͂��s���A������N���C�A���g�ɕԂ��悤�ɂ��Ă���B
*/
void Connection::getAction()
{
	std::cout << "get action> ";
	std::string s;
	std::getline(std::cin, s);
	returnResponse(s);
}
