package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.util.Random;

import com.nttdocomo.io.ConnectionException;
import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

/**
 * @author Ichinohe ゲーム進行の管理
 */
final class Game {
	/**
	 * ゲームを開始する
	 */
	public void start(Option op) {
		System.out.println("Game.start()");
		option = op;

		initializing = true;
		boolean initSuccess = initialize();
		initializing = false;
		
		if (!initSuccess)
			return;

		System.out.println("Game.start()");
		for (;;) // ループ1回でプレイヤー2人がそれぞれ1ターンをこなす。
		{
			boolean gameover;
			gameover = turn(player[0]);
			if (!gameover)
				break;
			if(titleBack) break;
			gameover = turn(player[1]);
			if (!gameover)
				break;
			if(titleBack) break;
			System.gc();
		}
	}

	private boolean initialize() {
		try {
			System.out.println("initialize start");
			boolean isLocalFirst = false;
			if (option.gameType == 2) {
				httpLogger = new HTTPPlayer(this, "ネットワーク");
				isLocalFirst = httpLogger.initialize(option);
			}
			canvas = new CanvasControl(this);
			canvas.repaint(); // now loadingを表示させる
	
			Aster.COLOR_MAX = option.numOfColors;
			Field.CONNECTION = option.connection;
	
			field = new Field(this, option.fieldXSize, option.fieldYSize);
			field.setAster();
	
	
			switch (option.gameType) {
			case 1:
				player[0] = new AIPlayer(this, "COM (Very Easy)");
				player[1] = new AIPlayer(this, "COM (Very Easy)");
				break;
			case 2:
				if (isLocalFirst) {
					player[0] = new KeyInputPlayer(this, "先攻");
					player[1] = httpLogger;
					httpLogger.waitForAnotherPlayer();
				} else {
					player[0] = httpLogger;
					player[1] = new KeyInputPlayer(this, "後攻");
					httpLogger.getField(field);
				}
				break;
			default:
				player[0] = new KeyInputPlayer(this, "先攻");
				player[1] = new KeyInputPlayer(this, "後攻");
			}
	
			// 初期設定(仮)
			Aster a = field.field[field.Y - 1][field.X / 2];
			new SunClass(a, player[0]);
			a = field.field[0][field.X / 2];
			new SunClass(a, player[1]);
	
			player[0].addAP(Option.initialAP[option.AP_Pointer]);
			player[1].addAP(Option.initialAP[option.AP_Pointer]);
	
			if (option.gameType == 2) {
				httpLogger.sendInitField(field);
			}
			return true;
		} catch (IOException e) {
			Dialog d = new Dialog(Dialog.BUTTON_OK, "");
			d.setText("申し訳ありません。接続ができませんでした。");
			d.show();
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			if (e instanceof ConnectionException) {
				ConnectionException ce = (ConnectionException)e;
				System.out.println("ConnectionException status: " + ce.getStatus());
			}
			return false;
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
		currentPlayer = player;
		canvas.onTurnStart(player);
		field.onTurnStart(player);
		boolean ret = false;
		exit: for (;;) {
			Action a;
			while ((a = player.getAction()) != null) {
				a.run();
			}

			Player goPlayer = field.checkGameOver();
			if (goPlayer != null) {
				/*
				 * Game.sleep(1500);
				 */
				if(goPlayer.equals(this.player[0])){
					canvas.gameOver(this.player[0]);
				}else{
					canvas.gameOver(this.player[1]);
				}

				String msg = goPlayer.toString().concat("の負け");
				/*
				 * canvas.paintString(msg, true);
				 * canvas.getScreen().flipScreen(); Game.sleep(1500);
				 * canvas.paintString("", false);
				 */
				System.out.println(msg);

				ret = false;
				break exit;
			}

			if (a == null) {
				ret = true;
				break exit;
			}
			// boolean gameover = field.act(a);
		}
		if (httpLogger != null) {
			httpLogger.endTurn(getPlayerIndex(player));
		}
		return ret;
	}

	Player getCurrentPlayer() {
		return currentPlayer;
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
		System.out.println(a.toString());
		if (httpLogger != null) {
			httpLogger.log(a);
		}
	}

	/**
	 * プレイヤー
	 */
	final Player[] player = new Player[2];

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

	public Option getOption() {
		return option;
	}

	boolean initializing;

	static final Random random = new Random(System.currentTimeMillis());

	static Image loadImage(String s) {
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///".concat(s)
					.concat(".gif"));
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
	
	public void titleBack(){
		titleBack=true;
	}
	private boolean titleBack;
}
