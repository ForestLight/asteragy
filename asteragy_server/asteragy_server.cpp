#include "stdafx.h"
#include "server.h"

namespace as = boost::asio;
using as::ip::tcp;

namespace
{
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

void server_main()
{
	try
	{
		as::io_service io_service;
		console_ctrl_function = boost::bind(&as::io_service::stop, &io_service);
		SetConsoleCtrlHandler(console_ctrl_handler, TRUE);

		Server s(io_service);

		io_service.run();
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
		server_main();
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
