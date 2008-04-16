#include "stdafx.h"
#include <string>
#include "game.h"

PlayDataQueue::PlayDataQueue() : owner(-1)
{
}

PlayDataQueue::~PlayDataQueue()
{
}

bool PlayDataQueue::Put(std::string const& s, int player)
{
	if (owner == player)
	{
		buf.push(s);
		return true;
	}
	else
	{
		return false;
	}
}

bool PlayDataQueue::Get(std::string& s, int player)
{
	if (buf.empty())
	{
		return false;
	}
	else if (player == owner)
	{
		return false;
	}
	else
	{
		s.swap(buf.front());
		buf.pop();
		return true;
	}
}

/*
@brief É^Å[ÉìÇ™äÆóπÇµÇΩÇ±Ç∆Çé¶Ç∑ÅB
*/
bool PlayDataQueue::SwitchUser(int lastPlayer)
{
	if (owner == lastPlayer)
	{
		owner = !owner;
		return true;
	}
	else
	{
		return false;
	}
}

Game::Game()
{
}

Game::~Game()
{
}

bool Game::EndTurn(int lastPlayer)
{
	return queue.SwitchUser(lastPlayer);
}

bool Game::PostAction(std::string const& s, int player)
{
	return queue.Put(s, player);
}


bool Game::GetAction(std::string& s, int player)
{
	return queue.Get(s, player);
}
