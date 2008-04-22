package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.util.Random;

import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

/**
 * @author Ichinohe �Q�[���i�s�̊Ǘ�
 */
final class Game {
	/**
	 * �Q�[�����J�n����
	 */
	public void start(Option op) {
		System.out.println("Game.start()");
		option = op;

		initializing = true;
		initialize();
		initializing = false;

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
			System.gc();
		}
	}

	private void initialize() {
		System.out.println("initialize start");
		if (option.gameType == 2) {
			httpLogger = new HTTPPlayer(this, "��U (N)");
			httpLogger.initialize(option);
		}
		canvas = new CanvasControl(this);
		canvas.repaint(); // now loading��\��������

		Aster.COLOR_MAX = option.numOfColors;
		Field.CONNECTION = option.connection;

		field = new Field(this, option.fieldXSize, option.fieldYSize);
		field.setAster();

		player[0] = new KeyInputPlayer(this, "��U");

		switch (option.gameType) {
		case 1:
			player[0] = new AIPlayer(this, "COM (Very Easy)");
			player[1] = new AIPlayer(this, "COM (Very Easy)");
			break;
		case 2:
			player[1] = httpLogger;
			break;
		default:
			player[1] = new KeyInputPlayer(this, "��U");
		}

		// �����ݒ�(��)
		Aster a = field.field[field.Y - 1][field.X / 2];
		new SunClass(a, player[0]);
		a = field.field[0][field.X / 2];
		new SunClass(a, player[1]);

		player[0].addAP(Option.initialAP[option.AP_Pointer]);
		player[1].addAP(Option.initialAP[option.AP_Pointer]);

		if (option.gameType == 2) {
			httpLogger.sendInitField(field);
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
				Game.sleep(1500);
				String msg = goPlayer.toString().concat("�̕���");
				canvas.paintString(msg, true);
				canvas.repaint();
				Game.sleep(1500);
				canvas.paintString("", false);
				System.out.println(msg);
				return false;
			}

			if (a == null) {
				return true;
			}
			// boolean gameover = field.act(a);
		}
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
		try {
			a.outputToStream(System.out);
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
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///".concat(s)
					.concat(".gif"));
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
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
}
