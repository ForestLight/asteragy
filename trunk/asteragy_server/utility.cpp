#include "stdafx.h"
#include <locale>
#include <ostream>
#include <vector>
#include <map>
#include <algorithm>
#include <boost/foreach.hpp>
#include <boost/lambda/lambda.hpp>
#include <boost/tr1/functional.hpp> //ref
#include <boost/algorithm/string/trim.hpp>
#include <boost/algorithm/string/split.hpp>
#include "server.h"

#ifdef USE_BOOST_POSIX_TIME
#include <boost/date_time/posix_time/posix_time.hpp>
namespace ptime = boost::posix_time;
#else
#include <ctime>
#endif

namespace algo = boost::algorithm;
namespace as = boost::asio;

using std::string;
using boost::begin;
using boost::end;
using std::ctype;
using boost::bind;
using boost::iterator_range;
using std::tr1::ref;

typedef ctype<char> ctypec_t;

namespace utility
{
	ctypec_t const& cacheCType = std::use_facet<ctypec_t>(std::locale::classic());
}

/*
@biref HTTP���X�|���X�p��Date: ��ǉ�����B
@param[in,out] os �o�͐�̃X�g���[��
*/
void output_http_date_header(std::ostream& os)
{
	char const* const format = "Date: %a, %d %b %Y %H:%M:%S GMT\r\n";
#ifdef USE_BOOST_POSIX_TIME
	using std::locale;
	locale prev = os.getloc();
	os.imbue(locale(locale::classic(), new ptime::time_facet(format)));
	os << ptime::second_clock::universal_time();
	os.imbue(prev);
#else
	char buf[80];
	std::time_t t = std::time(0);
	std::strftime(buf, sizeof buf, format, std::gmtime(&t));
	os << buf << std::endl;
#endif
}

/*
@brief "hoge=foo&piyo=bar"�`���̕�����𕪉�����B
@param[out] out ������̃f�[�^
@param[in] r ���͕�����
��̗�̏ꍇ�Aout�͎��̂悤�ɂȂ�B
out["hoge"] = "foo"
out["piyo"] = "bar"
*/
void parseArgs(map_t& out, iterator_range<string::const_iterator> const& r)
{
	namespace bll = boost::lambda;
	typedef iterator_range<string::const_iterator> str_cit_range;
	map_t m;
	//'&'�ŋ�؂���v�ɓ����B
	std::vector<str_cit_range> v;
	algo::split(v, r, bll::_1 == '&');
	//�e�X���ŏ���=�ŋ�؂���map�̃L�[�ƒl�ɂ���B
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
@brief HTTP�w�b�_�̕�����𕪉�����B
@param[out] out ������̃f�[�^
@param[in] r ���͕�����
�Ⴆ�Ύ��̂悤�ȕ��������͂Ƃ���ƁA
Hoge: Foo
Piyo: Bar
out�͎��̂悤�ɂȂ�B
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
@brief �悤�����Content-Length = 0�̏o�͂��s���֐��B
@param[in] code �X�e�[�^�X�R�[�h
code���ςȒl�i�L�蓾�Ȃ��l�A�Ή����Ă��Ȃ��l�j�̏ꍇ�A
500 Internal Server Error�Ƃ��Ĉ����B
*/
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

/*
@brief ���X�|���X��Ԃ��B
@param[in] s �R���e���g
*/
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

/*
@brief boost::asio::async_read_until�̃��b�p
*/
void Connection::asyncReadUntil(handler_type hander, char const* s)
{
	as::async_read_until(socket, request, s,
		bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}

/*
@brief boost::asio::async_write�̃��b�p
*/
void Connection::asyncWrite(handler_type hander)
{
	as::async_write(socket, response,
		bind(hander, shared_from_this(),
			as::placeholders::error,
			as::placeholders::bytes_transferred));
}
