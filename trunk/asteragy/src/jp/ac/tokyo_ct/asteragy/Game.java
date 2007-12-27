package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * 
 */

/**
 * @author Ichinohe �Q�[���i�s�̊Ǘ�
 */
class Game {

	/**
	 * �Q�[�����J�n����
	 */
	public void start() {
		System.out.println("Game.start()");
		canvas = new GameCanvas(this);
		Display.setCurrent(canvas);
		player1 = new KeyInputPlayer(this, "��U");
		player2 = new KeyInputPlayer(this, "��U");
		field = new Field(this);
		field.setFieldSize(11, 11);
		field.setAster();
		
		//�b��
		Aster a = field.getField()[6][0];
		a.setAsterClass(new StarClass(a, player1));
		a = field.getField()[6][1];
		a.setAsterClass(new MercuryClass(a, player1));
		a = field.getField()[6][2];
		a.setAsterClass(new VenusClass(a, player1));
		a = field.getField()[6][3];
		a.setAsterClass(new EarthClass(a, player1));
		a = field.getField()[6][4];
		a.setAsterClass(new MarsClass(a, player1));
		a = field.getField()[6][5];
		a.setAsterClass(new JupiterClass(a, player1));
		a = field.getField()[6][6];
		a.setAsterClass(new SaturnClass(a, player1));
		a = field.getField()[6][7];
		a.setAsterClass(new UranusClass(a, player1));
		a = field.getField()[6][8];
		a.setAsterClass(new NeptuneClass(a, player1));
		a = field.getField()[6][9];
		a.setAsterClass(new PlutoClass(a, player1));
		
		System.out.println("Game.start()");
		for (;;) // ���[�v1��Ńv���C���[2�l�����ꂼ��1�^�[�������Ȃ��B
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
	 * ��U�v���C���[�̎擾
	 * 
	 * @return ��U�v���C���[
	 */
	Player getPlayer1() {
		return player1;
	}

	/**
	 * ��U�v���C���[�̎擾
	 * 
	 * @return ��U�v���C���[
	 */
	Player getPlayer2() {
		return player2;
	}

	/**
	 * �t�B�[���h�̎擾
	 * 
	 * @return �t�B�[���h
	 */
	Field getField() {
		return field;
	}

	/**
	 * �`��L�����o�X�̎擾
	 * 
	 * @return ���̃Q�[���̕`����s���L�����o�X
	 */
	GameCanvas getCanvas() {
		return canvas;
	}

	public Game() {
	}

	/**
	 * ��U�v���C���[
	 */
	private Player player1;

	/**
	 * ��U�v���C���[
	 */
	private Player player2;

	/**
	 * �t�B�[���h
	 */
	private Field field;

	/**
	 * �`��ɗp����L�����o�X
	 */
	private GameCanvas canvas;

	private static void printMemoryStatus() {
		Runtime r = Runtime.getRuntime();
		System.out.println("Total memory: " + r.totalMemory()
				+ ", Free memory: " + r.freeMemory());
	}

}
