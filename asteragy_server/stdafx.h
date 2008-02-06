#ifndef STDAFX_H_
#define STDAFX_H_
 

#include "targetver.h"

#include <winsock2.h>

#include <iostream>
#include <string>
#include <ctime>

#define BOOST_ALL_DYN_LINK

#ifdef _MSC_VER
#	pragma once
#
#	define _CRT_SECURE_NO_WARNINGS
#	define _SCL_SECURE_NO_WARNINGS
#
#	pragma warning(push)
#	pragma warning(disable: 4819 4127 4512 4510 4610)
#endif

#include <boost/bind.hpp>
#include <boost/function.hpp>
#include <boost/range.hpp>
#include <boost/thread.hpp>

#define __USE_W32_SOCKETS // MinGW/Cygwin—p
#include <boost/asio.hpp>


#ifdef _MSC_VER
#	pragma warning(pop)
#endif

#endif /*STDAFX_H_*/
