package jp.ac.tokyo_ct.asteragy;

/**
 * 
 */

/**
 * @author Ichinohe ゲーム進行の管理
 */
final class Game {
	/**
	 * ゲームを開始する
	 */
	public void start() {
		System.out.println("Game.start()");
		// canvas = new GameCanvas(this);
		// Display.setCurrent(canvas);
		field = new Field(this);
		field.setFieldSize(9, 9);
		field.setAster();
		canvas = new CanvasControl(this);
		player[0] = new KeyInputPlayer(this, "先攻");
		player[1] = new KeyInputPlayer(this, "後攻");
		// canvas = new CanvasControl(this);

		// 初期設定(仮)
		Aster a = field.getField()[field.getY() - 1][field.getX() / 2];
		a.setAsterClass(new SunClass(a, player[0]));
		a = field.getField()[0][field.getX() / 2];
		a.setAsterClass(new SunClass(a, player[1]));

		player[0].addSP(30);
		player[1].addSP(30);

		System.out.println("Game.start()");
		for (;;) // ループ1回でプレイヤー2人がそれぞれ1ターンをこなす。
		{
			boolean gameover;
			gameover = turn(player[0]);
			if (!gameover)
				break;
			gameover = turn(player[1]);
			if (!gameover)
				break;
		}
		System.gc();
	}

	/**
	 * @param player
	 * @retval true ターンが正常に終了した
	 * @retval false ゲームが終了した
	 */
	private boolean turn(Player player) {
		System.out.println("Game.turn()");
		printMemoryStatus();
		currentPlayer = player;
		canvas.onTurnStart(player);
		field.onTurnStart(player);
		for (;;) {
			Action a = player.getAction();

			Player goPlayer = field.checkGameOver();
			if (goPlayer != null) {
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				System.out.println(goPlayer + "win");
				return false;
			}

			if (a == null) {
				return true;
			}
			// boolean gameover = field.act(a);
		}
	}

	/**
	 * 先攻プレイヤーの取得
	 * 
	 * @return 先攻プレイヤー
	 */
	Player getPlayer1() {
		return player[0];
	}

	/**
	 * 後攻プレイヤーの取得
	 * 
	 * @return 後攻プレイヤー
	 */
	Player getPlayer2() {
		return player[1];
	}

	Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * プレイヤー配列の取得
	 * 
	 * @return プレイヤー
	 */
	Player[] getPlayers() {
		return player;
	}
	
	int getPlayerIndex(Player p) {
		if (p == player[0])
			return 0;
		else if (p == player[1])
			return 1;
		else
		{
			String s = "Game.getPlayerIndex: 引数pは有効なプレイヤーではない。";
			System.out.println(s);
			throw new IllegalArgumentException(s);
		}
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
	CanvasControl getCanvas() {
		return canvas;
	}

	Game() {
	}

	void logAction(Action a) {
		System.out.print("Game.logAction: ");
		a.printToStream(System.out);
		System.out.println();
	}

	/**
	 * プレイヤー
	 */
	private Player[] player = new Player[2];

	/**
	 * ターン進行中のプレイヤー
	 */
	private Player currentPlayer;

	/**
	 * フィールド
	 */
	private Field field;

	/**
	 * 描画に用いるキャンバス
	 */
	private CanvasControl canvas;

	private static void printMemoryStatus() {
		Runtime r = Runtime.getRuntime();
		System.out.println("Total memory: " + r.totalMemory()
				+ ", Free memory: " + r.freeMemory());
	}

}
