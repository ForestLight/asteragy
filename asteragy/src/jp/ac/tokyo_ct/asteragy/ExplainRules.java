package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class ExplainRules extends Canvas {

	public int page;

	private Image image;

	private Image back;

	private Image[] pageImage = new Image[6];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// ページの作成（作成中）
		//
		Graphics g;
		for (int i = 0; i < 6; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}

		g = pageImage[0].getGraphics();
		back = loadImage("back.jpg");
		g.drawImage(back, 0, 0);
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
		g.drawImage(back, 0, 0);
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
		g.drawImage(back, 0, 0);
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

		g = pageImage[3].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("４．クラスとユニット", 15, 25);
		g.drawString("サンのコマンドでアステルにクラスを", 15, 70);
		g.drawString("与えることで、自分のユニットとして", 15, 85);
		g.drawString("操作することができます。", 15, 100);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 20, 115, 17, 17, 0, 0, 17, 17);
		image = AsterClass.loadImage(3);
		g.drawScaledImage(image, 37, 115, 17, 17, 17, 0, 17, 17);
		image = AsterClass.loadImage(5);
		g.drawScaledImage(image, 54, 115, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(8);
		g.drawScaledImage(image, 71, 115, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(9);
		g.drawScaledImage(image, 88, 115, 17, 17, 68, 0, 17, 17);
		g.drawString("ユニットはスワップとコマンド(特殊", 15, 160);
		g.drawString("能力)の２つを使えます。", 15, 175);
		g.drawString("コマンドの種類とレンジ(射程範囲)は", 15, 190);
		g.drawString("与えたクラスによって異なります。", 15, 205);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("　　　　　スワップ　コマンド(特殊", 15, 160);
		g.drawString("能力)", 15, 175);
		g.drawString("　　　　　　　　レンジ(射程範囲)", 15, 190);

		g = pageImage[4].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("５．ユニットの能力(スワップ)", 15, 25);
		g.drawString("ユニットの基本的な行動です。", 15, 70);
		g.drawString("レンジ内の隣り合った２つのアステル", 15, 85);
		g.drawString("を入れ替えます。", 15, 100);
		g.drawString("そのユニット自身も選択できます。", 15, 115);
		g.drawString("→", 106, 162);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 40, 131, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 21, 150, 17, 17, 68, 0, 17, 17);
		g.drawScaledImage(image, 59, 150, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 40, 169, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 40, 151, 17, 17, 51, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(20, 149, 19, 19);
		g.drawRect(58, 149, 19, 19);
		g.drawRect(39, 168, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(39, 130, 19, 19);
		g.drawRect(39, 149, 19, 19);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 160, 150, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 141, 150, 17, 17, 68, 0, 17, 17);
		g.drawScaledImage(image, 179, 150, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 160, 169, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 160, 132, 17, 17, 51, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(140, 149, 19, 19);
		g.drawRect(178, 149, 19, 19);
		g.drawRect(159, 168, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(159, 130, 19, 19);
		g.drawRect(159, 149, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("斜めやレンジ外のアステルの入れ替え", 15, 210);
		g.drawString("はできません。", 15, 225);

		g = pageImage[5].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("６．ユニットの能力(コマンド)", 15, 25);
		g.drawString("クラスにより異なる特殊な行動です。", 15, 70);
		g.drawString("APを消費して使用します。", 15, 85);
		g.drawString("→", 106, 140);
		image = AsterClass.loadImage(6);
		g.setFlipMode(Graphics.FLIP_VERTICAL);
		g.drawScaledImage(image, 40, 101, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(0);
		g.setFlipMode(Graphics.FLIP_NONE);
		g.drawScaledImage(image, 21, 120, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 40, 120, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 59, 120, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 21, 139, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 59, 139, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(4);
		g.drawScaledImage(image, 40, 139, 17, 17, 68, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(20, 119, 57, 19);
		g.drawRect(20, 138, 57, 19);
		g.drawRect(39, 119, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(39, 100, 19, 19);
		g.drawRect(39, 138, 19, 19);
		image = AsterClass.loadImage(6);
		g.drawScaledImage(image, 160, 101, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 141, 120, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 160, 120, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 179, 120, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 141, 139, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 179, 139, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(4);
		g.drawScaledImage(image, 160, 139, 17, 17, 68, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(140, 119, 57, 19);
		g.drawRect(140, 138, 57, 19);
		g.drawRect(159, 119, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(159, 100, 19, 19);
		g.drawRect(159, 138, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("例: ヴィーナスのコマンド", 15, 180);
		g.drawString("“テンプテーション”", 80, 195);
		g.drawString("「" + AsterClass.commandExplain[3] + "」", 40, 215);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("AP", 15, 85);
		g.drawLine(50, 217, 188, 217);
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
