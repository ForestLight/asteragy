package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * 
 */

/**
 * @author Ichinohe ゲーム進行の管理
 */
class Game {

	/**
	 * ゲームを開始する
	 */
	public void start() {
		System.out.println("Game.start()");
		canvas = new GameCanvas(this);
		Display.setCurrent(canvas);
		player1 = new KeyInputPlayer(this, "先攻");
		player2 = new KeyInputPlayer(this, "後攻");
		field = new Field(this);
		field.setFieldSize(10, 10);
		field.setAster();
		
		//暫定
		Aster a = field.getField()[0][5];
		System.out.println("Game.start()");
		AsterClass ac = new JupiterClass(a, player1);
//		a.setAsterClass();
		//a.setAsterClass(new NeptuneClass(a, player1));

		for (;;) // ループ1回でプレイヤー2人がそれぞれ1ターンをこなす。
		{
			boolean gameover;
			gameover = turn(player1);
			if (gameover)
				break;
			gameover = turn(player2);
			if (gameover)
				break;
		}
	}

	/**
	 * @param player
	 * @retval true ターンが正常に終了した
	 * @retval false ゲームが終了した
	 */
	private boolean turn(Player player) {
		System.out.println("Game.turn()");
		printMemoryStatus();
		canvas.onTurnStart(player);
		for (;;) {
			Action a = player.getAction();
			if (a == null) {
				return true;
			}
			boolean gameover = field.act(a);
			if (gameover) {
				return false;
			}
		}
	}

	/**
	 * 先攻プレイヤーの取得
	 * 
	 * @return 先攻プレイヤー
	 */
	Player getPlayer1() {
		return player1;
	}

	/**
	 * 後攻プレイヤーの取得
	 * 
	 * @return 後攻プレイヤー
	 */
	Player getPlayer2() {
		return player2;
	}

	/**
	 * フィールドの取得
	 * 
	 * @return フィールド
	 */
	Field getField() {
		return field;
	}

	/**
	 * 描画キャンバスの取得
	 * 
	 * @return このゲームの描画を行うキャンバス
	 */
	GameCanvas getCanvas() {
		return canvas;
	}

	public Game() {
	}

	/**
	 * 先攻プレイヤー
	 */
	private Player player1;

	/**
	 * 後攻プレイヤー
	 */
	private Player player2;

	/**
	 * フィールド
	 */
	private Field field;

	/**
	 * 描画に用いるキャンバス
	 */
	private GameCanvas canvas;

	private static void printMemoryStatus() {
		Runtime r = Runtime.getRuntime();
		System.out.println("Total memory: " + r.totalMemory()
				+ ", Free memory: " + r.freeMemory());
	}

}
