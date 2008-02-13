#ifndef STDAFX_H_
#define STDAFX_H_
 
#ifdef _MSC_VER
#	pragma once
#
#	if _MSC_VER >= 1400
#		define _CRT_SECURE_NO_WARNINGS
#		define _SCL_SECURE_NO_WARNINGS
#		define _SECURE_SCL 0
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
#
#	define __assume(x) ((void)0)
#endif

#include "targetver.h"

#if defined _WIN32 || defined _WIN64 //_WIN32だけ見ればよいのはわかっているけど……。
#	define NOMINMAX
#	define WIN32_LEAN_AND_MEAN
#	include <winsock2.h>
#	include <windows.h>
#endif

#include <iostream>
#include <string>
#include <locale>
#include <vector>
#include <map>
#include <iterator>
#include <utility>
#include <ctime>
#include <cassert>


#define BOOST_ALL_DYN_LINK

#include <boost/bind.hpp>
#include <boost/function.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/range.hpp>
#include <boost/ref.hpp>
#include <boost/thread.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include <boost/lambda/lambda.hpp>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/find.hpp>
#include <boost/algorithm/string/trim.hpp>
#include <boost/foreach.hpp>
#include <boost/utility.hpp>
#include <boost/mpl/identity.hpp>

#include <boost/range_ex/algorithm.hpp>

#define __USE_W32_SOCKETS // MinGW/Cygwin用
#include <boost/asio.hpp>

#include <wdpp/scoped.hpp>

#ifdef _MSC_VER
#	pragma warning(pop)
#endif

#endif /*STDAFX_H_*/
