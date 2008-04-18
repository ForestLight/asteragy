package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public final class EffectFieldSwap extends Effect {

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
			final int t = (GameCanvas.measure - s - 1) / 2;
			final int u = (GameCanvas.measure - l - 1) / 2;
			field.setOrignAster(g, a, t, t);
			aa.setSize(s, s);
			aa.paint(g);
			field.setOrignAster(g, a, u, u);
			ab.setSize(l, l);
			ab.paint(g);

			canvas.getBackImage().paintAsterBack(g, b);
			field.setOrignAster(g, b, t, t);
			ab.setSize(s, s);
			ab.paint(g);
			field.setOrignAster(g, b, u, u);
			aa.setSize(l, l);
			aa.paint(g);
			g.unlock(true);

			if (s > 0)
				s--;
			if (i > (GameCanvas.measure - 1) / 2)
				l++;

			Game.sleep(200 / CanvasControl.f);

		}

		aa.resetSize();
		ab.resetSize();

		field.repaintAster(g, a);
		field.repaintAster(g, b);
		canvas.getScreen().flipScreen();
	}
}
