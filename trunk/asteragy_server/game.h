#ifndef GAME_H
#define GAME_H

#include <string>
#include <queue>

#if defined _MSC_VER && _MSC_VER >= 1020
#	pragma once
#endif

class PlayDataQueue
{
public:
	PlayDataQueue();
	~PlayDataQueue();
	bool Put(std::string const& s, int player);
	bool Get(std::string& s, int player);
	bool SwitchUser(int lastPlayer);
	int GetOwner() const {return owner;}
private:
	std::queue<std::string> buf;
	int owner; //その時点でbufへPutしているプレイヤー
	//（!buf.empty()のとき、中身は全て同一プレイヤーownerのPutしたものである）

	PlayDataQueue(PlayDataQueue const&);
	PlayDataQueue& operator =(PlayDataQueue const&);
};

class Game
{
public:
	Game();
	~Game();

	bool PostAction(std::string const& s, int player);
	bool GetAction(std::string& s, int player);
	bool EndTurn(int lastPlayer);
private:
	PlayDataQueue queue;

	Game(Game const&);
	Game& operator =(Game const&);
};

#endif //GAME_H
