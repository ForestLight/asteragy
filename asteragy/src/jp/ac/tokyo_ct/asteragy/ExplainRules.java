package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class ExplainRules extends Canvas {

	public int page;

	public Image image;

	public Image[] pageImage = new Image[3];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// ページの作成
		Graphics g;
		for (int i = 0; i < 3; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}
		g = pageImage[0].getGraphics();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("ルール説明（仮）です", 20, 25);
		g.drawImage(image, 20, 50);
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.lock();

		g.drawImage(pageImage[page], 0, 0);

		g.unlock(true);
	}

}
