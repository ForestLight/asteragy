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
		player1 = new KeyInputPlayer("��U");
		player2 = new KeyInputPlayer("��U");
		field = new Field();
		
		for (;;) //���[�v1��Ńv���C���[2�l�����ꂼ��1�^�[�������Ȃ��B 
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
	 * @retval true �^�[��������ɏI������
	 * @retval false �Q�[�����I������
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
	 * ��U�v���C���[
	 */
	private Player player1;
	/**
	 * ��U�v���C���[
	 */
	private Player player2;
	
	private Field field;
}
