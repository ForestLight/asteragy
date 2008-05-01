#ifndef HTTPREQUEST_H
#define HTTPREQUEST_H

#include "stdafx.h"

#include <locale>
#include <iosfwd>
#include <string>
#include <map>
#include <boost/asio.hpp>
#include <boost/range.hpp>
#include <boost/mpl/identity.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>

#if defined _MSC_VER && _MSC_VER >= 1020
#pragma once
#endif

typedef std::map<std::string, std::string> map_t;

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
	void returnStreamResponse(std::istream& is, char const*);

	typedef boost::mpl::identity<void (Connection::*)(
		boost::system::error_code const& e, std::size_t bytesTransferred)>::type handler_type;
	void asyncReadUntil(handler_type hander, char const* s);
	void asyncWrite(handler_type hander);

	void queryNewGame();
	void getAction();
	void getActionFromConsole();

	void downloadStaticContents(std::string const& requestPath);

	tcp::socket socket;
	boost::asio::streambuf request; //受信用バッファ
	boost::asio::streambuf response; //送信用バッファ
	map_t args; //URL末尾の?以降で渡される引数
	map_t header; //HTTPリクエストヘッダ
	bool responsed;

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
	extern std::ctype<char> const& cacheCType;

	inline bool isSpace(char c)
	{
		return cacheCType.is(std::ctype<char>::space, c);
	}
}

#endif //HTTPREQUEST_H
