#include "stdafx.h"

using boost::asio::ip::tcp;

namespace
{
/*
boost::function0<void> console_ctrl_function;

BOOL WINAPI console_ctrl_handler(DWORD ctrl_type)
{
	switch (ctrl_type)
	{
	case CTRL_C_EVENT:
	case CTRL_BREAK_EVENT:
	case CTRL_CLOSE_EVENT:
	case CTRL_SHUTDOWN_EVENT:
		console_ctrl_function();
		return TRUE;
	default:
		return FALSE;
	}
}
*/
void output_http_date_header(std::ostream& os)
{
	char buf[256];
	std::time_t t = time(0);
	std::strftime(buf, sizeof buf, "Date: %a, %d %b %Y %H:%M:%S GMT\r\n", std::gmtime(&t));
	os << buf;
}

void server_main()
{
	try
	{
		static char const header[] =
			"HTTP/1.1 200 OK\r\n"
			"Connection: Close\r\n"
			"Content-Type: text/plain\r\n";

		boost::asio::io_service io_service;
//		console_ctrl_function = boost::bind(&boost::asio::io_service::stop, &io_service);
//		SetConsoleCtrlHandler(console_ctrl_handler, TRUE);

		tcp::acceptor acceptor(io_service, tcp::endpoint(tcp::v4(), 80));
		acceptor.set_option(boost::asio::ip::tcp::acceptor::reuse_address(true));

		std::string res; //HTTPPlayer‚Ìƒ^[ƒ“—p
		res.reserve(80);

		for (;;)
		{
			tcp::socket socket(io_service);
			acceptor.accept(socket);

			boost::asio::streambuf request;
			boost::asio::read_until(socket, request, "\r\n");
			std::cout << &request << std::endl;

			boost::asio::streambuf response;
			std::ostream os(&response);
			os << header;


			output_http_date_header(os);

			os << "Content-Length: 2\r\n"
				"\r\n"
				"OK\r\n";

			boost::system::error_code ignored_error;
			boost::asio::write(socket, response,
				boost::asio::transfer_all(), ignored_error);
		}
	}
	catch(std::exception const& e)
	{
		std::cerr << e.what() << std::endl;
	}
}

} // end of anonymous namespace

int main()
{
	try
	{
		boost::thread t(server_main);
		t.join();
	}
	catch (std::exception const& e)
	{
		std::cerr << e.what() << std::endl;
		return 1;
	}
	return 0;
}
/*
HTTP/1.1 200 OK
Date: Mon, 04 Feb 2008 16:01:14 GMT
Server: Apache
Accept-Ranges: bytes
Content-Length: 46754
Cache-Control: no-store,no-cache,must-revalidate
Pragma: no-cache
Expires: -1
Connection: close
Content-Type: text/html
*/
