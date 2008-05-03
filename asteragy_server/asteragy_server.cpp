#include "stdafx.h"
#include "server.h"
#include <memory>
#include <cstdlib> //srand
#include <ctime> //time
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/date_time/posix_time/posix_time_types.hpp>

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
	void NextTimer();

	void RemoveGameEntry(const boost::system::error_code&)
	{
		RemoveGame();
		NextTimer();
	}

	void NextTimer()
	{
		if (pios.get())
		{
			as::deadline_timer t(*pios, boost::posix_time::seconds(6 * 60));
			t.async_wait(RemoveGameEntry);
		}
	}

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
		NextTimer();

		pios->run();
		abs(0);
	}
} // end of anonymous namespace

int main()
{
	try
	{
		std::srand(static_cast<unsigned>(std::time(0)));
		contentRoot = std::getenv("ASTERAGY_SERVER_CONTENT_ROOT");
		server_main();
	}
	catch (std::exception const& e)
	{
		std::cerr << e.what() << std::endl;
		return 1;
	}
	return 0;
}
