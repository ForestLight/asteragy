package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class ExplainRules extends Canvas {

	public int page;

	public Image image;

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.lock();

		switch (page) {
		case 0:
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Graphics.getColorOfName(Graphics.WHITE));
			g.drawString("���[�������i���j�ł�", 20, 25);
		case 1:
			
		case 2:
			
		}

		g.unlock(true);
	}

}
