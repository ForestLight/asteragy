package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class InitializeAppli extends Canvas {

	public void paint(Graphics g) {
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

	void start() {
		Display.setCurrent(this);
		repaint();
		init();
	}

	void init() {
		//System.out.println("init");
		Game.loader = new ImageLoader();
		Game.loader.load();
	}

}
