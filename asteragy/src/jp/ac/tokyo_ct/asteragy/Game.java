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
		//canvas = new GameCanvas(this);
		//Display.setCurrent(canvas);
		canvas = new CanvasControl(this);
		player1 = new KeyInputPlayer(this, "先攻");
		player2 = new KeyInputPlayer(this, "後攻");
		field = new Field(this);
		field.setFieldSize(11, 11);
		field.setAster();
		
		//てすとぷれー用
		
//		Aster a = field.getField()[6][0];
//		a.setAsterClass(new StarClass(a, player1));
//		a = field.getField()[6][1];
//		a.setAsterClass(new MercuryClass(a, player1));
//		a = field.getField()[6][2];
//		a.setAsterClass(new VenusClass(a, player1));
//		a = field.getField()[6][3];
//		a.setAsterClass(new EarthClass(a, player1));
//		a = field.getField()[6][4];
//		a.setAsterClass(new MarsClass(a, player1));
//		a = field.getField()[6][5];
//		a.setAsterClass(new JupiterClass(a, player1));
//		a = field.getField()[6][6];
//		a.setAsterClass(new SaturnClass(a, player1));
//		a = field.getField()[6][7];
//		a.setAsterClass(new UranusClass(a, player1));
//		a = field.getField()[6][8];
//		a.setAsterClass(new NeptuneClass(a, player1));
//		a = field.getField()[6][9];
//		a.setAsterClass(new PlutoClass(a, player1));
//			
//		a = field.getField()[4][0];
//		a.setAsterClass(new StarClass(a, player2));
//		a = field.getField()[4][1];
//		a.setAsterClass(new MercuryClass(a, player2));
//		a = field.getField()[4][10];
//		a.setAsterClass(new VenusClass(a, player2));
//		a = field.getField()[4][3];
//		a.setAsterClass(new EarthClass(a, player2));
//		a = field.getField()[4][4];
//		a.setAsterClass(new MarsClass(a, player2));
//		a = field.getField()[4][5];
//		a.setAsterClass(new JupiterClass(a, player2));
//		a = field.getField()[4][6];
//		a.setAsterClass(new SaturnClass(a, player2));
//		a = field.getField()[4][7];
//		a.setAsterClass(new UranusClass(a, player2));
//		a = field.getField()[4][8];
//		a.setAsterClass(new NeptuneClass(a, player2));
//		a = field.getField()[4][9];
//		a.setAsterClass(new PlutoClass(a, player2));
//		
//		a = field.getField()[7][5];
//		a.setAsterClass(new SunClass(a,player1));
//		a = field.getField()[3][5];
//		a.setAsterClass(new SunClass(a,player2));
		
		
		//初期設定(仮) 
		Aster a = field.getField()[field.getY()-1][field.getX()/2];
		a.setAsterClass(new SunClass(a,player1));
		a = field.getField()[0][field.getX()/2];
		a.setAsterClass(new SunClass(a,player2));

		player1.addSP(30);
		player2.addSP(30);
				
		
		System.out.println("Game.start()");
		for (;;) // ループ1回でプレイヤー2人がそれぞれ1ターンをこなす。
		{
			boolean gameover;
			gameover = turn(player1);
			if (!gameover)
				break;
			gameover = turn(player2);
			if (!gameover)
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
		currentPlayer = player;
		canvas.onTurnStart(player);
		field.onTurnStart(player);
		for (;;) {
			Action a = player.getAction();
			
			Player goPlayer = field.checkGameOver();
			if (goPlayer != null) {
				try{
					Thread.sleep(1500);
				}catch(Exception e){
				}
				if(goPlayer == getPlayer1()) System.out.println("Player2 win");
				else System.out.println("Player1 win");
				return false;
			}
			
			if (a == null) {
				return true;
			}
		//	boolean gameover = field.act(a);
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
	
	Player getCurrentPlayer(){
		return currentPlayer;
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
