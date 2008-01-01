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
	private static final int playerheight = 20;

	private final CanvasControl canvas;

	public GameCanvas(CanvasControl canvas) {
		super();
		this.canvas = canvas;
	}

	public void paint(Graphics g) {
		System.out.println("repaint()");
		// �_�u���o�b�t�@�J�n
		g.lock();
		// �N���A
		g.clearRect(0, 0, 240, 240);
		//�`��
		paintBackGround(g);
		paintFieldSpace(g);
		paintPlayerInfo(g);
		// �_�u���o�b�t�@�I��
		g.unlock(false);
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
		g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
		canvas.getField().paint(g);
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
		// 1P
		g.setOrigin(0, this.getHeight() - playerheight);
		canvas.getPlayer1().paint(g);
		// 2P
		g.setOrigin(0, 0);
		canvas.getPalyer2().paint(g);
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see com.nttdocomo.ui.Canvas#processEvent(int, int)
	 */
	public void processEvent(int type, int param) {
		if (canvas.getEventProcesser() != null)
			canvas.getEventProcesser().processEvent(type, param);
		else
			super.processEvent(type, param);
	}

}
