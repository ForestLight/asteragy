package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Okubo
 * 
 * Title.javaに統合した方がいい気がしないでもない
 * 
 */
final class ExplainAsterClass extends Canvas implements Runnable {

	private static final int frame = 5;

	int number = 0;

	private int temp;

	private Thread thread;
	
	private Title title;

	private static Image asterClassImage;

	private static Image[] pageImage;
	
	private static Image[] pageImageInit(Frame f) {
		Image[] pageImage = new Image[12];
		for (int i = 0; i < 12; i++) {
			pageImage[i] = Image.createImage(f.getWidth(), f.getHeight());
			Graphics g = pageImage[i].getGraphics();
			asterClassImage = AsterClass.asterImage[i + 1];
			int[][] range = AsterClass.getDefaultRange(i + 1, false);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			g.fillRect(0, 0, f.getWidth(), f.getHeight());
			g.drawImage(asterClassImage, 20, 20);
			g.setColor(Graphics.getColorOfName(Graphics.WHITE));
			g.drawString(AsterClass.classNameF[i], 20, 60);
			g.drawString("コマンド: ".concat(AsterClass.commandName[i]), 20,
					90);
			g.drawString(AsterClass.commandExplain[i], 20, 110);
			if (i != 0 && i != 11) {
				g.drawString("クラスコスト: ".concat(String
					.valueOf(AsterClass.classCost[i])), 20, 130);
			}
			else {
				g.drawString("クラスコスト: -", 20, 130);
			}
			g.drawString("コマンドコスト: ".concat(String
					.valueOf(AsterClass.commandCost[i])), 20, 150);
			g.drawString("行動回数： ".concat(String
					.valueOf(AsterClass.actionNum[i])), 20, 170);
			//g.drawString("ｱﾌﾟﾘｷｰ/ｸﾘｱｷｰ/[2]/[0]: もどる", 20, 237);

			for (int j = 0; j < range.length; j++) {
				for (int k = 0; k < range[0].length; k++) {
					if (range[j][k] == 1) {
						g.fillRect(180 + 5 * k, 180 + 5 * j, 5, 5);
						g.setColor(Graphics.getColorOfName(Graphics.BLACK));
						g.drawRect(180 + 5 * k, 180 + 5 * j, 4, 4);
						g.setColor(Graphics.getColorOfName(Graphics.WHITE));
					}
				}
			}
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.fillRect(180 + 5 * (range[0].length / 2) + 1,
					180 + 5 * (range.length / 2) + 1, 3, 3);
		}
		return pageImage;
	}

	ExplainAsterClass(Title t) {
		title = t;
		if (pageImage == null) {
			pageImage = pageImageInit(t);
		}
	}

	public void paint(Graphics g) {
		if (thread != null) {
			if (thread.isAlive()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (number == temp) {
			g.lock();
			g.drawImage(pageImage[number], 0, 0);
			g.unlock(true);
			return;
		}
		thread = new Thread(this);
		thread.start();
	}
	public void run() {
		final Graphics g = title.getGraphics();

		final int width = getWidth();
		int dx = (temp - number) / Math.abs(temp - number);
		final int s = width / frame;

		if (temp == 0 && number == 11)
			dx = -dx;
		if (temp == 11 && number == 0)
			dx = -dx;

		for (int i = 0; i < frame + 1; i++) {

			g.lock();

			g.drawImage(pageImage[temp], i * dx * s, 0);
			g.drawImage(pageImage[number], i * dx * s - dx * width, 0);

			g.unlock(true);

			Game.sleep(300 / CanvasControl.f);
		}

		g.dispose();
		temp = number;
	}
}
