package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public class EffectFieldSwap extends Effect {

	private final Field field;

	private final CanvasControl canvas;

	private final Point a;

	private final Point b;

	// private boolean sa;

	// private boolean h;

	public EffectFieldSwap(Field field, Point a, Point b) {
		this.field = field;
		this.canvas = field.getGame().getCanvas();
		this.a = a;
		this.b = b;
		// checkSmall();
	}

	public void start(Graphics g) {
		if (!isEffect)
			return;

		PaintAsterItem aa = field.getAster(b).getPaint();
		PaintAsterItem ab = field.getAster(a).getPaint();

		int s = GameCanvas.measure - 1;
		int l = 0;

		for (int i = 0; i < (GameCanvas.measure - 1) * 3 / 2; i++) {

			g.lock();
			canvas.getBackImage().paintAsterBack(g, a);
			field.setOrignAster(g, a, (GameCanvas.measure - s - 1) / 2,
					(GameCanvas.measure - s - 1) / 2);
			aa.setSize(s, s);
			aa.paint(g);
			field.setOrignAster(g, a, (GameCanvas.measure - l - 1) / 2,
					(GameCanvas.measure - l - 1) / 2);
			ab.setSize(l, l);
			ab.paint(g);

			canvas.getBackImage().paintAsterBack(g, b);
			field.setOrignAster(g, b, (GameCanvas.measure - s - 1) / 2,
					(GameCanvas.measure - s - 1) / 2);
			ab.setSize(s, s);
			ab.paint(g);
			field.setOrignAster(g, b, (GameCanvas.measure - l - 1) / 2,
					(GameCanvas.measure - l - 1) / 2);
			aa.setSize(l, l);
			aa.paint(g);
			g.unlock(true);

			if (s > 0)
				s--;
			if (i > (GameCanvas.measure - 1) / 2)
				l++;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		aa.resetSize();
		ab.resetSize();

		field.repaintAster(g, a);
		field.repaintAster(g, b);
		canvas.getScreen().flipScreen();

	}

	/*
	 * private void checkSmall() { h = true; if (a.x > b.x || a.y > b.y) sa =
	 * true;
	 * 
	 * else sa = false;
	 * 
	 * if (a.x == b.x) h = false; }
	 * 
	 * public void start() { Graphics g =
	 * field.getGame().getCanvas().getGraphics();
	 * 
	 * int l = field.getGame().getCanvas().getLeftMargin(); int t =
	 * field.getGame().getCanvas().getTopMargin();
	 * 
	 * int x; int y; if (sa) { x = b.x * GameCanvas.measure; y = b.y *
	 * GameCanvas.measure; } else { x = a.x * GameCanvas.measure; y = a.y *
	 * GameCanvas.measure; }
	 * 
	 * int ax = 0; int ay = 0;
	 * 
	 * while ((ax < GameCanvas.measure && h) || (ay < GameCanvas.measure && !h)) {
	 * synchronized (g) { g.lock();
	 * field.getGame().getCanvas().getBackImage().paintAsterBack(g, a);
	 * field.getGame().getCanvas().getBackImage().paintAsterBack(g, b);
	 * 
	 * if (h) g.setOrigin(l + x - ax + GameCanvas.measure, t + y - ay); else
	 * g.setOrigin(l + x - ax, t + y - ay + GameCanvas.measure);
	 * 
	 * if (sa) field.getAster(a).getPaint().paint(g); else
	 * field.getAster(b).getPaint().paint(g);
	 * 
	 * g.setOrigin(l + x + ax, t + y + ay);
	 * 
	 * if (sa) field.getAster(b).getPaint().paint(g); else
	 * field.getAster(a).getPaint().paint(g);
	 * 
	 * 
	 * g.unlock(false); } if (h) ax += 2; else ay += 2;
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } } }
	 */
}
