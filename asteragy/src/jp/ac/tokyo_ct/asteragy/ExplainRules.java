package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class ExplainRules extends Canvas {

	public int page;

	public Image image;

	public Image[] pageImage = new Image[3];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// �y�[�W�̍쐬
		Graphics g;
		for (int i = 0; i < 3; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}
		g = pageImage[0].getGraphics();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("���[�������i���j�ł�", 20, 25);
		g.drawImage(image, 20, 50);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.lock();

		g.drawImage(pageImage[page], 0, 0);

		g.unlock(true);
	}

}
