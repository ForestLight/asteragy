package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18�~18
	 */
	public static final int measure = 18;

	/**
	 * �v���C���[���̈捂��
	 */
	public static final int playerheight = 20;

	private final CanvasControl canvas;

	public GameCanvas(CanvasControl canvas) {
		super();
		this.canvas = canvas;
	}

	public void paint(Graphics g) {
		System.out.println("repaint()");
		// �_�u���o�b�t�@�J�n
		g.lock();

		// Now Loading...
		if (canvas.isInit()) {
			canvas.paintNowloading(g);
			g.unlock(false);
			return;
		}

		// �`��
		paintBackGround(g);
		paintFieldSpace(g);
		paintPlayerInfo(g);

		paintOverGraphics(g);
		// �_�u���o�b�t�@�I��
		g.unlock(false);
	}

	private void paintOverGraphics(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		canvas.paintOver(g);
	}

	/**
	 * �w�i�`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintBackGround(Graphics g) {
		System.out.println("paintBackGround");
		g.setOrigin(0, 0);
		canvas.getBackImage().paint(g);
	}

	/**
	 * �t�B�[���h�̈�̕`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintFieldSpace(Graphics g) {
		System.out.println("paintFieldSpace");

		canvas.getField().paint(g);

		g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
		canvas.getCursor().paint(g);

		canvas.getCommand().paint(g);
	}

	/**
	 * �v���C���[���`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintPlayerInfo(Graphics g) {
		System.out.println("paintPlayerInfo");
		PaintItem[] players = canvas.getPlayers();
		for (int i = 0; i < players.length; i++) {
			players[i].paint(g);
		}
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see com.nttdocomo.ui.Canvas#processEvent(int, int)
	 */
	public void processEvent(int type, int param) {
		final EventProcesser eventProcesser = canvas.getEventProcesser();
		if (type == Display.KEY_PRESSED_EVENT && param == Display.KEY_9)
			Effect.setEffect();
		if (eventProcesser != null)
			eventProcesser.processEvent(type, param);
		else
			super.processEvent(type, param);
	}

}
