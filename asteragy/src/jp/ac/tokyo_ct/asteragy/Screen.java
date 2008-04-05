package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Screen implements PaintItem {

	private final GameCanvas canvas;

	private Image display;

	public Screen(GameCanvas canvas) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.canvas = canvas;
		display = Image.createImage(canvas.getWidth(), canvas.getHeight());
	}

	public void flipScreen() {
		canvas.repaint();
	}

	public void paintEffect(Effect effect) {
		Graphics g = canvas.getGraphics();
		effect.start(g);
	}

	public Graphics getGraphics() {
		return display.getGraphics();
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.drawImage(display, 0, 0);
	}

	public Image getScreen(int x, int y, int width, int height) {
		Image screen = Image.createImage(width, height);
		screen.getGraphics().drawImage(display, 0, 0, x, y, width, height);
		return screen;
	}

}
