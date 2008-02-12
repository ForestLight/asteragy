#include "stdafx.h"
#include "server.h"

namespace ptime = boost::posix_time;
namespace algo = boost::algorithm;
namespace as = boost::asio;

using std::string;
using boost::begin;
using boost::end;
using std::ctype;

namespace utility
{
	ctype<char> const& cacheCType = std::use_facet<ctype<char> >(std::locale::classic());
	boost::function<bool (char)> isSpace(boost::bind(&ctype<char>::is, boost::ref(cacheCType), ctype<char>::space, _1));
}

void output_http_date_header(std::ostream& os)
{
	std::locale prev = os.getloc();
	wdpp::scoped l(boost::bind(&std::ios_base::imbue, boost::ref(os), prev));
	os.imbue(std::locale(os.getloc(), new ptime::time_facet("Date: %a, %d %b %Y %H:%M:%S GMT\r\n")));
	os << ptime::second_clock::universal_time();
}

void parseArgs(std::map<string, string>& m,
	boost::iterator_range<string::const_iterator> const& r)
{
	using boost::iterator_range;
	namespace bll = boost::lambda;
	typedef std::map<string, string> args_type;
	typedef iterator_range<string::const_iterator> str_cit_range;
	//'&'で区切ってvに入れる。
	std::vector<str_cit_range> v;
	algo::split(v, r, bll::_1 == '&');
	//各々を最初の=で区切ってmapのキーと値にする。
	BOOST_FOREACH(str_cit_range const& e, v)
	{
		string::const_iterator it = boost::find(e, '=');
		if (it != end(e))
		{
			m.insert(args_type::value_type(
				string(begin(e), it), string(it + 1, end(e))));
		}
		else
		{
			m.insert(args_type::value_type(
				string(begin(e), end(e)), string()));
		}
	}
}

void perseHeader(std::map<string, string>& header, std::istream& is)
{
	for (;;)
	{
		string s;
		getline(is, s);
		string::iterator it = std::find(s.begin(), s.end(), ':');
		if (it == end(s) || it == begin(s))
		{
			break;
		}
		string fieldName(begin(s), it);
		string fieldValue(it + 1, end(s));
		algo::trim_if(fieldValue, utility::isSpace);
		
		std::map<string, string>::iterator pos = header.lower_bound(fieldName);
		if (pos != header.end() && !(fieldName < pos->first))
		{
			pos->second.reserve(pos->second.size() + 2 + fieldValue.size());
			(pos->second += ", ") += fieldValue;
		}
		else
		{
			header.insert(pos, std::pair<string, string>(fieldName, fieldValue));
		}
	}
}

//ようするにContent-Length = 0の出力を行う関数。
//codeが変な値の場合、500 Internal Server Errorとして扱う。
void Connection::returnEmptyResponse(int code)
{
	std::ostream os(&response);
	os << "HTTP/1.1 ";
	switch (code)
	{
	case 200:
		os << "200 OK";
		break;
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
}

void Connection::returnResponse(string const& s)
{
	std::ostream os(&response);
	os << "HTTP/1.1 200 OK\r\n"
			"Connection: Close\r\n";
	output_http_date_header(os);
	os << "Content-Length: "<< s.size() << "\r\n"
		"Content-Type: text/plain\r\n"
		"\r\n"
		<< s;
	asyncWrite(&Connection::handleWrite);
}

void Connection::asyncReadUntil(handler_type hander, char const* s)
{
	as::async_read_until(socket, request, s,
		boost::bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}

void Connection::asyncWrite(handler_type hander)
{
	as::async_write(socket, response,
		boost::bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}
