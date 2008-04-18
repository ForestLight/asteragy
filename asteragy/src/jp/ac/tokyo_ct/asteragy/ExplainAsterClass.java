package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Okubo
 *
 * Title.javaに統合した方がいい気がしないでもない
 *
 */
public final class ExplainAsterClass extends Canvas {

	public int number;

	private static Image asterClassImage;

	private Image[] pageImage = new Image[12];

	private int[][] range;

	ExplainAsterClass() {
		Graphics g;

		for (number = 0; number < 12; number++) {
			pageImage[number] = Image.createImage(getWidth(), getHeight());
			g = pageImage[number].getGraphics();
			asterClassImage = AsterClass.loadImage(number + 1);
			range = AsterClass.getDefaultRange(number + 1);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(asterClassImage, 20, 20);
			g.setColor(Graphics.getColorOfName(Graphics.WHITE));
			g.drawString(AsterClass.classNameB[number], 20, 60);
			g.drawString("コマンド: " + AsterClass.commandName[number], 20, 80);
			g.drawString(AsterClass.commandExplain[number], 20, 100);
			g.drawString("クラスコスト: " + AsterClass.classCost[number], 20, 120);
			g.drawString("コマンドコスト: " + AsterClass.commandCost[number], 20, 140);
			g.drawString("行動回数： " + AsterClass.actionNum[number],20,160);

			g.drawString("0: もどる", 20, 237);

			for (int i = 0; i < range.length; i++) {
				for (int j = 0; j < range[0].length; j++) {
					if (range[i][j] == 1) {
						g.fillRect(180 + 5 * j, 180 + 5 * i, 5, 5);
						g.setColor(Graphics.getColorOfName(Graphics.BLACK));
						g.drawRect(180 + 5 * j, 180 + 5 * i, 4, 4);
						g.setColor(Graphics.getColorOfName(Graphics.WHITE));
					}
				}
			}
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.fillRect(180+5*(range[0].length/2) + 1, 180+5*(range.length/2) + 1, 3, 3);
		}
		number = 0;
	}

	public void paint(Graphics g) {
		g.lock();

		g.drawImage(pageImage[number], 0, 0);

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
