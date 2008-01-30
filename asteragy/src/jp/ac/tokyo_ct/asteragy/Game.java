package jp.ac.tokyo_ct.asteragy;

/**
 * 
 */

/**
 * @author Ichinohe �Q�[���i�s�̊Ǘ�
 */
final class Game {
	/**
	 * �Q�[�����J�n����
	 */
	public void start() {
		System.out.println("Game.start()");
		// canvas = new GameCanvas(this);
		// Display.setCurrent(canvas);
		field = new Field(this);
		field.setFieldSize(9, 9);
		field.setAster();
		canvas = new CanvasControl(this);
		player[0] = new KeyInputPlayer(this, "��U");
		player[1] = new KeyInputPlayer(this, "��U");
		// canvas = new CanvasControl(this);

		// �����ݒ�(��)
		Aster a = field.getField()[field.getY() - 1][field.getX() / 2];
		a.setAsterClass(new SunClass(a, player[0]));
		a = field.getField()[0][field.getX() / 2];
		a.setAsterClass(new SunClass(a, player[1]));

		player[0].addSP(30);
		player[1].addSP(30);

		System.out.println("Game.start()");
		for (;;) // ���[�v1��Ńv���C���[2�l�����ꂼ��1�^�[�������Ȃ��B
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
	 * @retval true �^�[��������ɏI������
	 * @retval false �Q�[�����I������
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
	 * ��U�v���C���[�̎擾
	 * 
	 * @return ��U�v���C���[
	 */
	Player getPlayer1() {
		return player[0];
	}

	/**
	 * ��U�v���C���[�̎擾
	 * 
	 * @return ��U�v���C���[
	 */
	Player getPlayer2() {
		return player[1];
	}

	Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * �v���C���[�z��̎擾
	 * 
	 * @return �v���C���[
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
			String s = "Game.getPlayerIndex: ����p�͗L���ȃv���C���[�ł͂Ȃ��B";
			System.out.println(s);
			throw new IllegalArgumentException(s);
		}
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
	 * �v���C���[
	 */
	private Player[] player = new Player[2];

	/**
	 * �^�[���i�s���̃v���C���[
	 */
	private Player currentPlayer;

	/**
	 * �t�B�[���h
	 */
	private Field field;

	/**
	 * �`��ɗp����L�����o�X
	 */
	private CanvasControl canvas;

	private static void printMemoryStatus() {
		Runtime r = Runtime.getRuntime();
		System.out.println("Total memory: " + r.totalMemory()
				+ ", Free memory: " + r.freeMemory());
	}

}
