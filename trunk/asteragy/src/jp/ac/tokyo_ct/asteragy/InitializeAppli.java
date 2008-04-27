package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class InitializeAppli extends Canvas {

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		final String nowloading = "nowloading";
		g.lock();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(nowloading, (getWidth() - Font.getDefaultFont()
				.stringWidth(nowloading)) / 2, (getHeight() + Font
				.getDefaultFont().getHeight()) / 2);
		g.unlock(true);
	}

	public void start() {
		Display.setCurrent(this);
		repaint();
		init();
	}

	private void init() {
		// TODO 自動生成されたメソッド・スタブ
		//System.out.println("init");
		Game.loader = new ImageLoader();
		Game.loader.load();
		Main.game = new Game();
		Main.title = new Title();
	}

}
