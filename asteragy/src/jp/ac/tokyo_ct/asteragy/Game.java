package jp.ac.tokyo_ct.asteragy;

/**
 * 
 */

/**
 * @author Ichinohe
 * ゲーム進行の管理
 */
class Game {
	
	/**
	 * ゲームを開始する
	 */
	public void start()
	{
		player1 = new KeyInputPlayer("先攻");
		player2 = new KeyInputPlayer("後攻");
		field = new Field();
		canvas = new GameCanvas();
		
		for (;;) //ループ1回でプレイヤー2人がそれぞれ1ターンをこなす。 
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
		for (;;)
		{
			Action a = player1.getAction();
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
	 * インスタンスの取得
	 * @return インスタンス
	 */
	public static Game getInstance() {
		return instance;
	}
	
	/**
	 * 先攻プレイヤーの取得
	 * @return	 先攻プレイヤー
	 */
	Player getPlayer1() {
		return player1;
	}
	
	/**
	 * 後攻プレイヤーの取得
	 * @return 後攻プレイヤー
	 */
	Player getPlayer2() {
		return player2;
	}
	
	/**
	 * フィールドの取得
	 * @return フィールド
	 */
	Field getField() {
		return field;
	}
	
	/**
	 * 描画キャンバスの取得
	 * @return このゲームの描画を行うキャンバス
	 */
	GameCanvas getCanvas() {
		return canvas;
	}
	
	/**
	 * シングルトンのため、private
	 */
	private Game() {
	}
	
	/**
	 * シングルトンインスタンス
	 */
	private static Game instance = new Game();
	
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
}
