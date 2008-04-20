package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.util.Random;

import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

/**
 * @author Ichinohe ゲーム進行の管理
 */
final class Game implements Runnable {
	/**
	 * ゲームを開始する
	 */
	public void start(Option op) {
		System.out.println("Game.start()");
		option = op;

		Thread init = new Thread(this);
		init.start();
		try {
			init.join();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}

		/*
		 * // canvas = new GameCanvas(this); // Display.setCurrent(canvas);
		 * field = new Field(this); field.setFieldSize(9, 9); field.setAster();
		 * 
		 * player[0] = new KeyInputPlayer(this, "先攻");
		 * 
		 * switch (gameType) { case 1: //player[1] = new AIPlayer(this, "乱数");
		 * break; case 2: httpLogger = new HTTPPlayer(this, "後攻 (N)"); player[1] =
		 * httpLogger; break; default: player[1] = new KeyInputPlayer(this,
		 * "後攻"); }
		 * 
		 * canvas = new CanvasControl(this); // 初期設定(仮) Aster a =
		 * field.getField()[field.getY() - 1][field.getX() / 2];
		 * a.setAsterClass(new SunClass(a, player[0])); a =
		 * field.getField()[0][field.getX() / 2]; a.setAsterClass(new
		 * SunClass(a, player[1]));
		 * 
		 * player[0].addSP(30); player[1].addSP(30);
		 * 
		 * System.out.println("Game.start()"); try { Thread.sleep(300); } catch
		 * (Exception e) { }
		 * 
		 * canvas.setCurrent();
		 */
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
			System.gc();
		}
	}

	private void initialize() {
		System.out.println("initialize start");
		canvas = new CanvasControl(this);
		canvas.paintNowloading(canvas.getScreen().getGraphics());
		canvas.getScreen().flipScreen();
		// canvas.repaint();
		// Display.setCurrent(canvas);

		Aster.COLOR_MAX = option.numOfColors;
		Field.CONNECTION = option.connection;

		field = new Field(this);
		field.setFieldSize(option.fieldXSize, option.fieldYSize);
		field.setAster();

		player[0] = new KeyInputPlayer(this, "先攻");

		switch (option.gameType) {
		case 1:
			player[0] = new AIPlayer(this, "COM (Very Easy)");
			player[1] = new AIPlayer(this, "COM (Very Hard)");
			break;
		case 2:
			httpLogger = new HTTPPlayer(this, "後攻 (N)");
			player[1] = httpLogger;
			break;
		default:
			player[1] = new KeyInputPlayer(this, "後攻");
		}

		// 初期設定(仮)
		Aster a = field.getField()[field.getY() - 1][field.getX() / 2];
		new SunClass(a, player[0]);
		a = field.getField()[0][field.getX() / 2];
		new SunClass(a, player[1]);

		player[0].addAP(option.initialAP[option.AP_Pointer]);
		player[1].addAP(option.initialAP[option.AP_Pointer]);

		canvas.repaint();
		canvas.getScreen().flipScreen();
		System.out.println("initialize end");
	}

	public void run() {
		init = true;
		initialize();
		init = false;
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
			Action a;
			while ((a = player.getAction()) != null) {
				a.run();
			}

			Player goPlayer = field.checkGameOver();
			if (goPlayer != null) {
				Game.sleep(1500);
				String msg = goPlayer.toString().concat("の負け");
				canvas.paintString(msg, true);
				canvas.getScreen().flipScreen();
				Game.sleep(1500);
				canvas.paintString("", false);
				System.out.println(msg);
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
		else {
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

	void setField(Field f) {
		field = f;
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
		try {
			a.printToStream(System.out);
		} catch (IOException e) {
		}
		System.out.println();
		if (httpLogger != null) {
			httpLogger.log(a);
		}
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

	private HTTPPlayer httpLogger = null;

	// private int gameType;
	private Option option;

	public boolean isInit() {
		return init;
	}

	public Option getOption() {
		return option;
	}

	private boolean init;

	static final Random random = new Random(System.currentTimeMillis());

	static Image loadImage(String s) {
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///".concat(s));
			// メディアの使用開始
			m.use();
			// 読み込み
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}

	static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
