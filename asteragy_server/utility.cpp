#include "stdafx.h"
#include <locale>
#include <ostream>
#include <iterator>
#include <vector>
#include <map>
#include <algorithm>
#include <ctime>
#include <boost/foreach.hpp>
#include <boost/lambda/lambda.hpp>
#include <boost/ref.hpp>
#include <boost/algorithm/string/trim.hpp>
#include <boost/algorithm/string/split.hpp>
#include "server.h"

//#ifdef USE_BOOST_POSIX_TIME
//#include <boost/date_time/posix_time/posix_time.hpp>
//namespace ptime = boost::posix_time;
//#else
#include <ctime>
//#endif

namespace algo = boost::algorithm;
namespace as = boost::asio;

using std::string;
using std::ctype;
using boost::begin;
using boost::end;
using boost::as_literal;
using boost::bind;
using boost::iterator_range;
using boost::ref;

typedef ctype<char> ctypec_t;

namespace utility
{
	ctypec_t const& cacheCType = std::use_facet<ctypec_t>(std::locale::classic());
}

/*
@biref HTTPレスポンス用にDate: を追加する。
@param[in,out] os 出力先のストリーム
*/
void output_http_date_header(std::ostream& os)
{
	static char const format[] = "Date: %a, %d %b %Y %H:%M:%S GMT\r\n";
	using std::locale;

	//locale prev = os.getloc();
	//os.imbue(locale(locale::classic(), new ptime::time_facet(format)));
	//os << ptime::second_clock::universal_time();
	//os.imbue(prev);

	std::time_t t = std::time(0);
	std::use_facet<std::time_put<char> >(locale::classic())
		.put(std::ostreambuf_iterator<char>(os),
			os, ' ', std::gmtime(&t), begin(as_literal(format)), end(as_literal(format)));

	//char buf[80];
	//std::time_t t = std::time(0);
	//std::strftime(buf, sizeof buf, format, std::gmtime(&t));
	//os << buf << std::endl;
}

/*
@brief "hoge=foo&piyo=bar"形式の文字列を分解する。
@param[out] out 分解後のデータ
@param[in] r 入力文字列
上の例の場合、outは次のようになる。
out["hoge"] = "foo"
out["piyo"] = "bar"
*/
void parseArgs(map_t& out, iterator_range<string::const_iterator> const& r)
{
	namespace bll = boost::lambda;
	typedef iterator_range<string::const_iterator> str_cit_range;
	map_t m;
	//'&'で区切ってvに入れる。
	std::vector<str_cit_range> v;
	algo::split(v, r, bll::_1 == '&');
	//各々を最初の=で区切ってmapのキーと値にする。
	BOOST_FOREACH(str_cit_range const& e, v)
	{
		string::const_iterator it = std::find(begin(e), end(e), '=');
		if (it != end(e))
		{
			m.insert(map_t::value_type(
				string(begin(e), it), string(it + 1, end(e))));
		}
		else
		{
			m.insert(map_t::value_type(
				string(begin(e), end(e)), string()));
		}
	}
	out.swap(m);
}

/*
@brief HTTPヘッダの文字列を分解する。
@param[out] out 分解後のデータ
@param[in] r 入力文字列
例えば次のような文字列を入力とすると、
Hoge: Foo
Piyo: Bar
outは次のようになる。
out["Hoge"] = "Foo"
out["Piyo"] = "Bar"
*/
void perseHeader(map_t& out, std::istream& is)
{
	map_t m;
	for (;;)
	{
		string s;
		getline(is, s);
		string::iterator it = std::find(begin(s), end(s), ':');
		if (it == end(s) || it == begin(s))
		{
			break;
		}
		string fieldName(begin(s), it);
		string fieldValue(it + 1, end(s));
		algo::trim_if(fieldValue, utility::isSpace);

		map_t::iterator pos = m.lower_bound(fieldName);
		if (pos != m.end() && !(fieldName < pos->first))
		{
			pos->second.reserve(pos->second.size() + 2 + fieldValue.size());
			(pos->second += ", ") += fieldValue;
		}
		else
		{
			m.insert(pos, std::make_pair(fieldName, fieldValue));
		}
	}
	out.swap(m);
}

/*
@brief ようするにContent-Length = 0の出力を行う関数。
@param[in] code ステータスコード
codeが変な値（有り得ない値、対応していない値）の場合、
500 Internal Server Errorとして扱う。
*/
void Connection::returnEmptyResponse(int code)
{
	std::ostream os(&response);
	os << "HTTP/1.0 ";
	switch (code)
	{
	case 200:
		os << "200 OK";
		break;
/*
	case 202:
		os << "202 Accepted";
		break;
	case 204:
		os << "204 No Content";
		break;
*/
	case 400:
		os << "400 Bad Request";
		break;
	case 404:
		os << "404 Not Found";
		break;
	case 500:
	default:
		os << "500 Internal Server Error";
		break;
	}
	os << "\r\n"
		"Connection: Close\r\n";
	output_http_date_header(os);
	os << "Content-Length: 0\r\n"
		"\r\n";
	asyncWrite(&Connection::handleWrite);
	responsed = true;
}

/*
@brief レスポンスを返す。
@param[in] s コンテント
*/
void Connection::returnResponse(string const& s)
{
	std::ostream os(&response);
	os << "HTTP/1.1 200 OK\r\n"
			"Connection: Close\r\n";
	output_http_date_header(os);
	os << "Content-Length: " << s.size() << "\r\n"
		"Content-Type: text/plain\r\n"
		"\r\n"
		<< s;
	asyncWrite(&Connection::handleWrite);
	responsed = true;
}

void Connection::returnStreamResponse(std::istream& is, char const* type)
{
	typedef std::istreambuf_iterator<char> isbufit_t;
	std::vector<char> buf;
	buf.reserve(2048);
	buf.assign(isbufit_t(is), isbufit_t());
	std::ostream os(&response);
	os << "HTTP/1.1 200 OK\r\n"
			"Connection: Close\r\n";
	output_http_date_header(os);
	os << "Content-Length: " << buf.size() << "\r\n"
		"Content-Type: " << type << "\r\n"
		"\r\n";
	std::copy(buf.begin(), buf.end(), std::ostreambuf_iterator<char>(os));
	asyncWrite(&Connection::handleWrite);
	responsed = true;
}

/*
@brief boost::asio::async_read_untilのラッパ
*/
void Connection::asyncReadUntil(handler_type hander, char const* s)
{
	as::async_read_until(socket, request, s,
		bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}

/*
@brief boost::asio::async_writeのラッパ
*/
void Connection::asyncWrite(handler_type hander)
{
	as::async_write(socket, response,
		bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}
