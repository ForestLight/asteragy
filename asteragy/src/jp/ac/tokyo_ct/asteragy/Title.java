package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * @author Okubo だれかなおして
 * 
 */
public class Title extends Canvas {
	private static Image title;

	private static Image[] menu;

	private int depth = 0;

	private int cursor = 0;

	private int gameType = -1;

	// private static int highScore;

	private AudioPresenter ap;

	public Title() {
		if (title == null) {
			title = loadImage("title.jpg");
		}
		if (menu == null) {
			menu = new Image[5];
			for (int i = 0; i < 5; i++)
				menu[i] = loadImage("menu_" + i + ".gif");
		}
	}

	private Thread startThread;

	/**
	 * タイトル画面の描画
	 * 
	 * @return gameType 0:一機対戦 1:AI対戦 2:ネットワーク対戦
	 */
	public int start() {
		startThread = Thread.currentThread();
		Display.setCurrent(this);
		gameType = -1;

		for (;;) {
			// System.out.println("loop");
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				return gameType;
			}
		}
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

	public void processEvent(int type, int param) {
		if (type == Display.KEY_PRESSED_EVENT) {
			switch (param) {
			case Display.KEY_UP:
				if (cursor > 0)
					cursor--;
				break;
			case Display.KEY_DOWN:
				if (depth + cursor < 2)
					cursor++;
				break;
			case Display.KEY_SELECT:
				if (depth == 0 && cursor == 1) {
					gameType = 0;
					startThread.interrupt();
				} else if (depth == 0 && cursor == 2) {
					IApplication.getCurrentApp().terminate();
				} else if (depth == 1 && cursor == 0) {
					gameType = 1;
					startThread.interrupt();
				} else if (depth == 1 && cursor == 1) {
					gameType = 2;
					startThread.interrupt();
				} else {
					depth++;
				}
				break;
			case Display.KEY_0:
				if (depth > 0)
					depth--;
			default:
				return;
			}
			repaint();
		}
	}

	public void paint(Graphics g) {
		g.lock();

		g.drawImage(title, 0, 0);

		// 汎用性のないコードですいません
		if (depth == 0) {
			g.drawImage(menu[0], getWidth() / 2 - menu[0].getWidth() / 2, 150);
			g.drawImage(menu[1], getWidth() / 2 - menu[1].getWidth() / 2,
					150 + menu[1].getHeight());
			g.drawImage(menu[2], getWidth() / 2 - menu[2].getWidth() / 2, 150
					+ menu[1].getHeight() + menu[2].getHeight());
		} else {
			g.drawImage(menu[3], getWidth() / 2 - menu[3].getWidth() / 2, 150);
			g.drawImage(menu[4], getWidth() / 2 - menu[4].getWidth() / 2,
					150 + menu[4].getHeight());
		}
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(getWidth() / 2 - menu[0].getWidth() / 2, 150
				+ menu[0].getHeight() * cursor, menu[0].getWidth(), menu[0]
				.getHeight());

		g.unlock(true);
	}
}
