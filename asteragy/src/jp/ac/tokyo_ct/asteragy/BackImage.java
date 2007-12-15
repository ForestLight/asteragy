package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class BackImage {

	private static Image backimage;

	/**
	 * �Œ�w�i�쐬
	 * 
	 */
	private static void createBackGround() {
		// �w�i�摜�쐬
		backimage = Image.createImage(240, 240);
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
	private static void paintBackGround(Graphics g) {
		// �Ǎ���C���[�W
		Image back = null;
		try {
			// �w�i�摜���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///back.jpg");
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
	 * �w�i�ǂݍ���
	 * 
	 * @return �w�i
	 */
	private static Image backImage() {
		if (backimage == null)
			createBackGround();
		return backimage;
	}

	/**
	 * �`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	public static void paint(Graphics g) {
		g.drawImage(backImage(), 0, 0);
	}

}
