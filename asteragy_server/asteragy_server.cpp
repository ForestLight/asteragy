#include "stdafx.h"
#include "server.h"
#include <memory>
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>

#ifdef _WIN32
#include <windows.h>
#else
#include <signal.h>
#include <unistd.h>
#endif

namespace as = boost::asio;
using as::ip::tcp;

namespace
{
	std::auto_ptr<as::io_service> pios;

#ifdef _WIN32
	BOOL WINAPI console_ctrl_handler(DWORD ctrl_type)
	{
		switch (ctrl_type)
		{
//		case CTRL_C_EVENT:
//		case CTRL_BREAK_EVENT:
//		case CTRL_CLOSE_EVENT:
		case CTRL_SHUTDOWN_EVENT:
			if (as::io_service* p = pios.get())
			{
				p->stop();
				return TRUE;
			}
		}
		return FALSE;
	}
#else
	void sig_handler(int)
	{
		if (!pios.get())
		{
			pios->stop();
			pios.reset(0);
		}
		_exit(0);
	}
#endif
	void server_main()
	{
		pios.reset(new as::io_service);
#ifdef _WIN32
		SetConsoleCtrlHandler(console_ctrl_handler, TRUE);
#else
		sigset_t wait_mask;
		sigemptyset(&wait_mask);
		sigaddset(&wait_mask, SIGINT);
		sigaddset(&wait_mask, SIGQUIT);
		sigaddset(&wait_mask, SIGTERM);
		struct sigaction sa = {0};
		sa.sa_handler = sig_handler;
		sa.sa_mask = wait_mask;

		sigaction(SIGINT, &sa, 0);
		sigaction(SIGQUIT, &sa, 0);
		sigaction(SIGTERM, &sa, 0);
#endif
		Server s(*pios);

		pios->run();
		abs(0);
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
