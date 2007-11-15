/**
 * 
 */

/**
 * @author Ichinohe
 *
 */
class Game {
	public void start()
	{
		player1 = new KeyInputPlayer("先攻");
		player2 = new KeyInputPlayer("後攻");
		field = new Field();
		
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
	 * 先攻プレイヤー
	 */
	private Player player1;
	/**
	 * 後攻プレイヤー
	 */
	private Player player2;
	
	private Field field;
}
