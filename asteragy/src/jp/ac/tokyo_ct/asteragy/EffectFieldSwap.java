package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public class EffectFieldSwap {

	private final Field field;

	private final Point a;

	private final Point b;

	private boolean sa;

	private boolean h;

	public EffectFieldSwap(Field field, Point a, Point b) {
		this.field = field;
		this.a = a;
		this.b = b;
		checkSmall();
	}

	private void checkSmall() {
		h = true;
		if (a.x > b.x || a.y > b.y)
			sa = true;

		else
			sa = false;

		if (a.x == b.x)
			h = false;
	}

	public void start() {
		Graphics g = field.getGame().getCanvas().getGraphics();

		int l = field.getGame().getCanvas().getLeftMargin();
		int t = field.getGame().getCanvas().getTopMargin();

		int x;
		int y;
		if (sa) {
			x = b.x * GameCanvas.measure;
			y = b.y * GameCanvas.measure;
		} else {
			x = a.x * GameCanvas.measure;
			y = a.y * GameCanvas.measure;
		}

		int ax = 0;
		int ay = 0;

		while ((ax < GameCanvas.measure && h)
				|| (ay < GameCanvas.measure && !h)) {
			synchronized (g) {
				g.lock();
				field.getGame().getCanvas().getBackImage().paintAsterBack(g, a);
				field.getGame().getCanvas().getBackImage().paintAsterBack(g, b);

				if (h)
					g.setOrigin(l + x - ax + GameCanvas.measure, t + y - ay);
				else
					g.setOrigin(l + x - ax, t + y - ay + GameCanvas.measure);

				if (sa)
					field.getAster(a).getPaint().paint(g);
				else
					field.getAster(b).getPaint().paint(g);

				g.setOrigin(l + x + ax, t + y + ay);

				if (sa)
					field.getAster(b).getPaint().paint(g);
				else
					field.getAster(a).getPaint().paint(g);
				

				g.unlock(false);
			}
			if (h)
				ax += 2;
			else
				ay += 2;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO Ž©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
				e.printStackTrace();
			}
		}

	}
}
