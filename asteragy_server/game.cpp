#include "stdafx.h"
#include <string>
#include <queue>
#include <ctime>
#include "game.h"

using std::time;

Game::Game() : playerCount(), lastAccessTime(time(0))
{
}

Game::~Game()
{
}

bool Game::EndTurn(int lastPlayer)
{
	lastAccessTime = time(0);
	SendAction("end", lastPlayer);
	return true;
}

bool Game::SendAction(std::string const& s, int player)
{
	lastAccessTime = time(0);
	if (action[player].empty() || action[player].front() != s)
		action[player].push(s);
	return true;
}

bool Game::GetAction(std::string& s, int player)
{
	lastAccessTime = time(0);
	if (!action[player].empty())
	{
		s.swap(action[player].front());
		action[player].pop();
	}
	return true;
}

void Game::PostOption(std::string const& s)
{
	lastAccessTime = time(0);
	option = s;
}

std::string const& Game::GetOption() const
{
	lastAccessTime = time(0);
	return option;
}

void Game::SendInitField(std::string const& s)
{
	lastAccessTime = time(0);
	initField = s;
}

std::string const& Game::GetInitField() const
{
	lastAccessTime = time(0);
	return initField;
}

int Game::JoinPlayer()
{
	lastAccessTime = time(0);
	return playerCount++;
}

bool Game::Ready()
{
	lastAccessTime = time(0);
	return playerCount == 2;
}
