package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

final class EffectFieldSwap extends Effect {

	private final int[] args;

	EffectFieldSwap(int[] args) {
		this.args = args;
	}

	void start(Graphics g, CanvasControl c) {
		final Field field = c.field;
		final Point a = new Point(args[0], args[1]);
		final Point b = new Point(args[2], args[3]);
		final Aster aa = field.at(a);
		final Aster ab = field.at(b);

		int s = CanvasControl.measure - 1;
		int l = 0;

		for (int i = 0; i < (CanvasControl.measure - 1) * 3 / 2; i++) {

			g.lock();
			c.paintAsterBack(g, a);
			final int t = (CanvasControl.measure - s - 1) / 2;
			final int u = (CanvasControl.measure - l - 1) / 2;
			field.setOrignAster(g, a, t, t);
			aa.setSize(s, s);
			aa.paint(g);
			field.setOrignAster(g, a, u, u);
			ab.setSize(l, l);
			ab.paint(g);

			c.paintAsterBack(g, b);
			field.setOrignAster(g, b, t, t);
			ab.setSize(s, s);
			ab.paint(g);
			field.setOrignAster(g, b, u, u);
			aa.setSize(l, l);
			aa.paint(g);
			g.unlock(true);

			if (s > 0)
				s--;
			if (i > (CanvasControl.measure - 1) / 2)
				l++;

			Game.sleep(100 / CanvasControl.f);

		}

		aa.resetSize();
		ab.resetSize();

		field.repaintAster(g, c, a);
		field.repaintAster(g, c, b);
	}
}
