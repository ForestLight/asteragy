#include "stdafx.h"
#include <string>
#include <queue>
#include "game.h"

Game::Game() : playerCount()
{
}

Game::~Game()
{
}

bool Game::EndTurn(int lastPlayer)
{
	SendAction("end", lastPlayer);
	return true;
}

bool Game::SendAction(std::string const& s, int player)
{
	if (action[player].empty() || action[player].front() != s)
		action[player].push(s);
	return true;
}

bool Game::GetAction(std::string& s, int player)
{
	if (!action[player].empty())
	{
		s.swap(action[player].front());
		action[player].pop();
	}
	return true;
}

void Game::PostOption(std::string const& s)
{
	option = s;
}

std::string const& Game::GetOption() const
{
	return option;
}

void Game::SendInitField(std::string const& s)
{
	initField = s;
}

std::string const& Game::GetInitField() const
{
	return initField;
}

int Game::JoinPlayer()
{
	return playerCount++;
}

bool Game::Ready()
{
	return playerCount == 2;
}
