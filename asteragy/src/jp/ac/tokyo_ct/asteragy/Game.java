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
		field.setFieldSize(10, 10);
		field.setAster();

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
	 * �C���X�^���X�̎擾
	 * 
	 * @return �C���X�^���X
	 */
	public static Game getInstance() {
		return instance;
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

	/**
	 * �V���O���g���̂��߁Aprivate
	 */
	private Game() {
	}

	/**
	 * �V���O���g���C���X�^���X
	 */
	private static Game instance = new Game();

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
}
