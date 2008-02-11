package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class BackImage implements PaintItem {

	private Image backimage;

	private final CanvasControl canvas;

	public BackImage(CanvasControl canvas) {
		this.canvas = canvas;
		createBackGround();
	}

	/**
	 * �Œ�w�i�쐬
	 * 
	 */
	private void createBackGround() {
		// �w�i�摜�쐬
		backimage = Image.createImage(canvas.getWidth(), canvas.getHeight());
		// �O���t�B�N�X�쐬
		Graphics g = backimage.getGraphics();
		// �w�i�`��
		paintBackGround(g);
		// �O���t�B�N�X�p��
		g.dispose();
	}

	/**
	 * �w�i�`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintBackGround(Graphics g) {
		// �Ǎ���C���[�W
		Image back = null;
		try {
			// �w�i�摜���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///back.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			back = m.getImage();
		} catch (Exception e) {
		}
		// �`��
		if (back != null)
			g.drawImage(back, 0, 0);
	}

	/**
	 * �`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	public void paint(Graphics g) {
		System.out.println("paintBackImage");
		g.drawImage(backimage, 0, 0);
	}

	public void paintFieldBack(Graphics g) {
		final int leftMargin = canvas.getLeftMargin();
		final int topMargin = canvas.getTopMargin();
		g.setOrigin(0, 0);
		g.drawImage(backimage, leftMargin, topMargin, leftMargin, topMargin,
				canvas.fieldWidth() + 1, canvas.fieldHeight() + 1);
	}

	public void paintAsterBack(Graphics g, Point point) {
		final int m = GameCanvas.measure;
		g.setOrigin(0, 0);
		int x = canvas.getLeftMargin() + point.x * m;
		int y = canvas.getTopMargin() + point.y * m;
		g.drawImage(backimage, x, y, x, y, m + 1, m + 1);
	}

	public void paintPlayerBakc(Graphics g, int player) {
		int y = 0;
		if (player == 1) {
			y = canvas.getHeight() - GameCanvas.playerheight;
		}
		g.drawImage(backimage, 0, y, 0, y, canvas.getWidth(),
				GameCanvas.playerheight);
	}

}
