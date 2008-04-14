package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class ExplainRules extends Canvas {

	public int page;

	public Image image;

	public Image[] pageImage = new Image[3];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// ページの作成（作成中）
		//
		Graphics g;
		for (int i = 0; i < 3; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}

		g = pageImage[0].getGraphics();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("１．あすてらじぃ☆とは", 15, 25);
		g.drawString("フィールド上のアステルを動かして", 15, 70);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("　　　　　　　アステル", 15, 70);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("戦うボードゲームです。", 15, 85);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("【アステル】", 15, 120);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("フィールドの全マスに配置されている", 15, 135);
		g.drawString("物体で、それぞれに色があります。", 15, 150);
		g.drawImage(image, 20, 160);
		

		g = pageImage[1].getGraphics();
		image = AsterClass.loadImage(1);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("２．ゲームの流れと目的", 15, 25);
		g.drawString("プレイヤーは先行から交互にターンを", 15, 70);
		g.drawString("進めます。ターン中には、ユニットを", 15, 85);
		g.drawString("操作できます。", 15, 100);
		g.drawString("相手のサンを消滅させれば勝利です。", 15, 115);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("　　　　　　　　　　　　ユニット", 15, 85);
		g.drawString("　　　サン", 15, 115);
		g.drawString("【ユニット】", 15, 150);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("プレイヤーが操作することのできる", 15, 165);
		g.drawString("アステルです。サンは互いに最初から", 15, 180);
		g.drawString("所有しているユニットです。", 15, 195);
		g.drawScaledImage(image, 20, 210, 17, 17, 0, 0, 17, 17);
		g.setFlipMode(Graphics.FLIP_VERTICAL);
		g.drawScaledImage(image, 45, 210, 17, 17, 34, 0, 17, 17);
		g.drawString("向きは所有者を表わします。", 70, 220);
		
		g = pageImage[2].getGraphics();
		image = AsterClass.loadImage(0);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("３．アステル", 15, 25);
		g.drawString("同じ色のアステルは４つ以上くっつく", 15, 70);
		g.drawString("ことで消滅します。", 15, 85);
		g.drawString("消滅したあとには、どこからともなく", 15, 100);
		g.drawString("ランダムにアステルが補充されます。", 15, 115);
		g.drawScaledImage(image, 20, 130, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 20, 147, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 37, 147, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 37, 164, 17, 17, 17, 0, 17, 17);
		g.drawString("→", 66, 160);
		image = loadImage("disappear.gif");
		g.drawScaledImage(image, 90, 130, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 90, 147, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 107, 147, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 107, 164, 17, 17, 0, 153, 17, 17);
		g.drawString("→", 136, 160);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 160, 130, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 160, 147, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 177, 147, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 177, 164, 17, 17, 68, 0, 17, 17);
		g.drawString("また、消滅させたアステルの数だけ、", 15, 210);
		g.drawString("AP(アスターパワー)がたまります。", 15, 225);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("AP(アスターパワー)", 15, 225);
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.lock();

		g.drawImage(pageImage[page], 0, 0);

		g.unlock(true);
	}

	private static Image loadImage(String s) {
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///" + s);
			// メディアの使用開始
			m.use();
			// 読み込み
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}

}
