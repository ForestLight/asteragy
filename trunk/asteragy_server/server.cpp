#include "stdafx.h"
#include "server.h"

namespace as = boost::asio;
namespace algo = boost::algorithm;

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
void Server::AcceptHandler(boost::system::error_code const& e)
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
		enum {method, path, version}; //上の各要素の内容を表す
		using std::ctype;
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

		//TODO: クライアントがHTTP/1.1を指定しているとき、Hostフィールドの確認。

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
		//バッファの消去
		response.consume(response.size() * sizeof(as::streambuf::char_type));
		returnEmptyResponse(500);
	}
}

/*
@brief postactionで、内容を受信したときに呼ばれる。
*/
void Connection::handleReadPostAction(boost::system::error_code const& e, std::size_t bytesTransferred)
{
	std::istream is(&request);
	std::string s; //暫定
	std::getline(is, s);
	std::cout << "postaction 受領: " << s << std::endl;
}

/*
@brief 書込終了時のハンドラ（応答の必要ないもの用）
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
@brief getactionの応答を行う。
現在、暫定的にコンソールからアクション文字列の入力を行い、それをクライアントに返すようにしている。
*/
void Connection::getAction()
{
	std::cout << "get action> ";
	std::string s;
	std::getline(std::cin, s);
	returnResponse(s);
}
