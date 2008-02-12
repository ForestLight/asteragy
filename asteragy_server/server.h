#ifndef HTTPREQUEST_H
#define HTTPREQUEST_H

#include "stdafx.h"

#ifdef _MSC_VER
#	pragma once
#endif

class Connection : public boost::enable_shared_from_this<Connection>
{
private:
	typedef boost::asio::ip::tcp tcp;
public:
	Connection(boost::asio::io_service& s) : socket(s) {}

	tcp::socket& SocketRef()
	{
		return socket;
	}

	void Start();

private:
	void handleRead(boost::system::error_code const& e, std::size_t bytesTransferred);
	void handleWrite(boost::system::error_code const& e, std::size_t bytesTransferred);

	void handleReadPostAction(boost::system::error_code const& e, std::size_t bytesTransferred);

	void returnResponse(std::string const& s);
	void returnEmptyResponse(int stetusCode);

	typedef boost::mpl::identity<void (Connection::*)(
		boost::system::error_code const& e, std::size_t bytesTransferred)>::type handler_type;
	void asyncReadUntil(handler_type hander, char const* s);
	void asyncWrite(handler_type hander);

	void getAction();

	tcp::socket socket;
	boost::asio::streambuf request; //受信用バッファ
	boost::asio::streambuf response; //送信用バッファ
	std::map<std::string, std::string> args; //URL末尾の?以降で渡される引数
	std::map<std::string, std::string> header; //HTTPリクエストヘッダ

	Connection(Connection const&);
	Connection& operator =(Connection const&);
};

typedef boost::shared_ptr<Connection> ConnectionPtr;

class Server
{
private:
	typedef boost::asio::ip::tcp tcp;
public:
	Server(boost::asio::io_service& s) :
		acceptor(s, tcp::endpoint(tcp::v4(), 80)), connection(), service(s)
	{
		nextAccept();
	}

	void AcceptHandler(boost::system::error_code const&);

private:
	void nextAccept();

	tcp::acceptor acceptor;
	ConnectionPtr connection;
	boost::asio::io_service& service;

	Server(Server const&);
	Server& operator =(Server const&);
};

void output_http_date_header(std::ostream& os);
void parseArgs(std::map<std::string, std::string>& m,
	boost::iterator_range<std::string::const_iterator> const& r);
void perseHeader(std::map<std::string, std::string>& header, std::istream& is);
void output_error(std::ostream& os, int code);

namespace utility
{
	extern boost::function<bool (char)> isSpace;
}

#endif //HTTPREQUEST_H
