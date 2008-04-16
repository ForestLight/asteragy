#ifndef STDAFX_H_
#define STDAFX_H_

#ifdef _MSC_VER
#	if _MSC_VER >= 1020
#		pragma once
#	endif
#
#	if _MSC_VER >= 1400
#		define _CRT_SECURE_NO_WARNINGS
#		define _SCL_SECURE_NO_WARNINGS
#		ifdef NDEBUG
#			define _SECURE_SCL 0
#		endif
#	endif
#
/*
#	elif _MSC_VER == 1400
#		pragma comment(lib, "boost_date_time-vc80-mt.lib")
#		pragma comment(lib, "boost_regex-vc80-mt.lib")
#		pragma comment(lib, "boost_system-vc80-mt.lib")
#		pragma comment(lib, "boost_thread-vc80-mt.lib")
#	endif
*/
#
#	pragma warning(push)
#	pragma warning(disable: 4819 4127 4512 4510 4610 4100 4251)
#
#else
#	define __assume(x) ((void)0)
#endif

#include "targetver.h"

#ifdef __CYGWIN__
#	define _WIN32
#endif

#if defined _WIN32 || defined _WIN64 //_WIN32だけ見ればよいのはわかっているけど……。
#	define NOMINMAX
#	define WIN32_LEAN_AND_MEAN
#	include <winsock2.h>
#	include <windows.h>
#
#	define __USE_W32_SOCKETS // MinGW/Cygwin用
#endif

#include <iostream>
#include <string>
#include <map>
#include <utility>
#include <algorithm>
#include <cassert>

#define BOOST_ALL_DYN_LINK

#include <boost/range.hpp>
#include <boost/bind.hpp> //Asioのプレースホルダを使うためTR1にできない
#include <boost/utility.hpp>
#include <boost/mpl/identity.hpp>
#include <boost/tr1/memory.hpp>

#include <boost/asio.hpp>

#ifdef _MSC_VER
#	pragma warning(pop)
#endif

#endif /*STDAFX_H_*/
