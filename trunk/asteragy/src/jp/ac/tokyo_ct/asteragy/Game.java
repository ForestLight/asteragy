package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.util.*;

import com.nttdocomo.io.ConnectionException;
import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.Image;

/**
 * @author Ichinohe �Q�[���i�s�̊Ǘ�
 */
final class Game {
	/**
	 * �Q�[�����J�n����
	 */
	void start(Option op) {
		Game.println("Game.start()");
		option = op;

		initializing = true;
		boolean initSuccess = initialize();
		initializing = false;

		if (!initSuccess)
			return;

		Game.println("Game.start()");
		for (;;) // ���[�v1��Ńv���C���[2�l�����ꂼ��1�^�[�������Ȃ��B
		{
			boolean gameover;
			gameover = turn(player[0]);
			if (!gameover)
				break;
			if (titleBack)
				break;
			gameover = turn(player[1]);
			if (!gameover)
				break;
			if (titleBack)
				break;
			System.gc();
		}
	}

	private boolean initialize() {
		try {
			Game.println("initialize start");
			boolean isLocalFirst = false;
			if (option.gameType == 2) {
				httpLogger = new HTTPPlayer(this, "�l�b�g�̌�����");
				isLocalFirst = httpLogger.initialize(option);
			}

			field = new Field(this, option.fieldXSize, option.fieldYSize,
					option.connection);
			AsterClass.colorMax = option.numOfColors;
			field.setAster();

			canvas = new CanvasControl(this);
			canvas.repaint(); // now loading��\��������

			switch (option.gameType) {
			case 1:
				player[0] = new KeyInputPlayer(this, "��U");
				player[1] = new AIPlayer(this, "COM");
				break;
			case 2:
				if (isLocalFirst) {
					player[0] = new KeyInputPlayer(this, "��U");
					player[1] = httpLogger;
					httpLogger.waitForAnotherPlayer();
				} else {
					player[0] = httpLogger;
					player[1] = new KeyInputPlayer(this, "��U");
					httpLogger.getField(field);
				}
				break;
			default:
				player[0] = new KeyInputPlayer(this, "��U");
				player[1] = new KeyInputPlayer(this, "��U");
			}

			// �����ݒ�(��)
			Aster a = field.field[field.Y - 1][field.X / 2];
			new AsterClass(a, player[0], 1);
			a = field.field[0][field.X / 2];
			new AsterClass(a, player[1], 1);

			player[0].addAP(option.asterPower);
			player[1].addAP(option.asterPower);
			Game.println("initialize");

			if (option.gameType == 2) {
				httpLogger.sendInitField(field);
			}
			return true;
		} catch (IOException e) {
			Dialog d = new Dialog(Dialog.BUTTON_OK, "");
			d.setText("�\���󂠂�܂���B�ڑ����ł��܂���ł����B");
			d.show();
			Game.println(e.toString());
			Game.println(e.getMessage());
			if (e instanceof ConnectionException) {
				ConnectionException ce = (ConnectionException) e;
				Game.println("ConnectionException status: "
						+ ce.getStatus());
			}
			return false;
		}
	}

	/**
	 * @param player
	 * @retval true �^�[��������ɏI������
	 * @retval false �Q�[�����I������
	 */
	private boolean turn(Player player) {
		Game.println("Game.turn()");
		printMemoryStatus();
		currentPlayer = player;
		canvas.onTurnStart(player);
		field.onTurnStart(player);
		boolean ret = false;
		exit: for (;;) {
			Action a;
			Player goPlayer = null;
			while ((a = player.getAction()) != null) {
				a.run();
				goPlayer = field.checkGameOver();
				if (goPlayer != null)
					break;
			}
			goPlayer = field.checkGameOver(); // AI�p�ɂ����ɂ��B���̂����O��
			if (goPlayer != null) {
				/*
				 * Game.sleep(1500);
				 */
				if (goPlayer.equals(this.player[0])) {
					canvas.gameOver(this.player[1]);
				} else {
					canvas.gameOver(this.player[0]);
				}

				String msg = goPlayer.toString().concat("�̕���");
				/*
				 * canvas.paintString(msg, true);
				 * canvas.getScreen().flipScreen(); Game.sleep(1500);
				 * canvas.paintString("", false);
				 */
				Game.println(msg);

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
			String s = "Game.getPlayerIndex: ����p�̓v���C���[�łȂ�";
			Game.println(s);
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
		Game.print("Game.logAction: ");
		Game.println(a.toString());
		if (httpLogger != null) {
			httpLogger.log(a);
		}
	}

	void logDeleteInfo(Field f, Vector deletedList) {
		if (httpLogger != null) {
			Game.print("Game.logDeleteInfo");
			if (deletedList.size() == 0) {
				httpLogger.logDeletedList(null, null);
			} else {
				httpLogger.logDeletedList(f, deletedList);
			}
		}
	}

	/**
	 * �v���C���[
	 */
	final Player[] player = new Player[2];

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
		Game.println("Total memory: " + r.totalMemory()
				+ ", Free memory: " + r.freeMemory());
	}

	private HTTPPlayer httpLogger = null;

	// private int gameType;
	private Option option;

	Option getOption() {
		return option;
	}

	boolean initializing;

	//static private final Random random = new Random(System.currentTimeMillis());
	
	private static int seed = 0x92D68CA2 + new Long(System.currentTimeMillis()).hashCode();
	
	static int rand(int m) {
		// return random.nextInt(m);
		seed ^= (seed << 13);
		seed ^= (seed >>> 17);
		seed ^= (seed << 5);
		return (seed >>> 1) % m;
	}

	static ImageLoader loader;

	static Image loadImage(String s) {
		return (Image) loader.getImages().get(s.concat(".gif"));
	}

	static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Game.println("InterruptedException");
		}
	}
	
	static void println(Object o) {
		System.out.println(o);
	}
	
	static void println(int i) {
		System.out.println(i);
	}
	
	static void print(Object o) {
		System.out.print(o);
	}

	void titleBack() {
		titleBack = true;
	}

	private boolean titleBack;
}
