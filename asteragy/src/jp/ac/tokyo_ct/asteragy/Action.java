package jp.ac.tokyo_ct.asteragy;

import java.io.OutputStream;
import java.io.PrintStream;

final class Action {

	private Player player;

	/**
	 * Actionの生成
	 * 
	 * @param player
	 *            このActionを起こしたプレイヤー
	 */
	public Action(Player player) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	Aster aster;
	int commandType;
	int[] args;
	
	public void printToStream(OutputStream os) {
		PrintStream ps = new PrintStream(os);
		ps.print(Main.game.getPlayerIndex(player));
		ps.print(' ');
		ps.print(commandType);
		for (int i = 0; i < args.length; i++) {
			ps.print(' ');
			ps.print(args[i]);
		}
	}
}
