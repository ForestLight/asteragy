package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public class EffectFieldSwap implements Effect {

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

	public void start() {


		Graphics g = canvas.getGraphics();

		PaintAsterItem aa = field.getAster(b).getPaint();
		PaintAsterItem ab = field.getAster(a).getPaint();

		int lax = canvas.getLeftMargin() + GameCanvas.measure * a.x;
		int lay = canvas.getTopMargin() + GameCanvas.measure * a.y;
		int lbx = canvas.getLeftMargin() + GameCanvas.measure * b.x;
		int lby = canvas.getTopMargin() + GameCanvas.measure * b.y;

		int s = GameCanvas.measure - 1;
		int l = 0;

		for (int i = 0; i < (GameCanvas.measure - 1) * 3 / 2; i++) {

			canvas.getBackImage().paintAsterBack(g, a);
			g.setOrigin(lax + (GameCanvas.measure - s - 1) / 2, lay
					+ (GameCanvas.measure - s - 1) / 2);
			aa.setSize(s, s);
			aa.paint(g);
			g.setOrigin(lax + (GameCanvas.measure - l - 1) / 2, lay
					+ (GameCanvas.measure - l - 1) / 2);
			ab.setSize(l, l);
			ab.paint(g);

			canvas.getBackImage().paintAsterBack(g, b);
			g.setOrigin(lbx + (GameCanvas.measure - s - 1) / 2, lby
					+ (GameCanvas.measure - s - 1) / 2);
			ab.setSize(s, s);
			ab.paint(g);
			g.setOrigin(lbx + (GameCanvas.measure - l - 1) / 2, lby
					+ (GameCanvas.measure - l - 1) / 2);
			aa.setSize(l, l);
			aa.paint(g);

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

		field.repaintAster(a);
		field.repaintAster(b);


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
