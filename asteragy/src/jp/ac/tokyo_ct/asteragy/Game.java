package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.util.Random;

/**
 * 
 */

/**
 * @author Ichinohe �Q�[���i�s�̊Ǘ�
 */
final class Game implements Runnable {
	/**
	 * �Q�[�����J�n����
	 */
	public void start(Option op) {
		System.out.println("Game.start()");
		option = op;

		Thread init = new Thread(this);
		init.start();
		try {
			init.join();
		} catch (InterruptedException e) {
			// TODO �����������ꂽ catch �u���b�N
			// e.printStackTrace();
		}

		/*
		 * // canvas = new GameCanvas(this); // Display.setCurrent(canvas);
		 * field = new Field(this); field.setFieldSize(9, 9); field.setAster();
		 * 
		 * player[0] = new KeyInputPlayer(this, "��U");
		 * 
		 * switch (gameType) { case 1: //player[1] = new AIPlayer(this, "����");
		 * break; case 2: httpLogger = new HTTPPlayer(this, "��U (N)"); player[1] =
		 * httpLogger; break; default: player[1] = new KeyInputPlayer(this,
		 * "��U"); }
		 * 
		 * canvas = new CanvasControl(this); // �����ݒ�(��) Aster a =
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
		canvas.repaint();
		canvas.getScreen().flipScreen();
		for (;;) // ���[�v1��Ńv���C���[2�l�����ꂼ��1�^�[�������Ȃ��B
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

		player[0] = new KeyInputPlayer(this, "��U");

		switch (option.gameType) {
		case 1:
			player[1] = new AIPlayer(this, "����");
			break;
		case 2:
			httpLogger = new HTTPPlayer(this, "��U (N)");
			player[1] = httpLogger;
			break;
		default:
			player[1] = new KeyInputPlayer(this, "��U");
		}

		// �����ݒ�(��)
		Aster a = field.getField()[field.getY() - 1][field.getX() / 2];
		new SunClass(a, player[0]);
		a = field.getField()[0][field.getX() / 2];
		new SunClass(a, player[1]);

		player[0].addAP(option.initialAP[option.AP_Pointer]);
		player[1].addAP(option.initialAP[option.AP_Pointer]);

		System.out.println("initialize end");
	}

	public void run() {
		init = true;
		initialize();
		init = false;
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
			Action a;
			while ((a = player.getAction()) != null) {
				a.run();
			}

			Player goPlayer = field.checkGameOver();
			if (goPlayer != null) {
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				canvas.paintString(goPlayer + "�̕���", true);
				canvas.getScreen().flipScreen();
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				canvas.paintString("", false);
				System.out.println(goPlayer + "�̕���");
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
		else {
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

	void setField(Field f) {
		field = f;
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
}
