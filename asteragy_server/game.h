#ifndef GAME_H
#define GAME_H

#include <string>
#include <queue>
#include <ctime>
#include <boost/noncopyable.hpp>

#if defined _MSC_VER && _MSC_VER >= 1020
#	pragma once
#endif

class Game : boost::noncopyable
{
public:
	Game();
	~Game();

	bool SendAction(std::string const& s, int player);
	bool GetAction(std::string& s, int player);
	bool EndTurn(int lastPlayer);
	void PostOption(std::string const& s);
	std::string const& GetOption() const;
	void SendInitField(std::string const& s);
	std::string const& GetInitField() const;
	int JoinPlayer();
	bool Ready();

	int GetPlayerCount() const
	{
		return playerCount;
	}

	time_t LastAccessTime() const
	{
		return lastAccessTime;
	}
private:
	std::string option;
	std::string initField;
	std::queue<std::string> action[2];
	mutable time_t lastAccessTime;
	int playerCount;

	Game(Game const&);
	Game& operator =(Game const&);
};

#endif //GAME_H
