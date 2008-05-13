package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

final class EffectCommandMars extends Effect {

	private static final Image effect = Game.loadImage("mars_effect");

	private final Point point;

	private final Point aster;

	private Point lefttop;

	private Point rightbottom;

	private int time;

	EffectCommandMars(Field field, AsterClass cls, Point point) {
		this.point = point;
		this.aster = cls.getPoint();
		setRect();
	}

	private void setRect() {
		int lx = aster.x;
		int ty = aster.x;
		int rx = aster.y;
		int by = aster.y;
		if (point.x < aster.x) {
			lx = point.x;
			rx = aster.x;
		} else {
			lx = aster.x;
			rx = point.x;
		}

		if (point.y < aster.y) {
			ty = point.y;
			by = aster.y;
		} else {
			ty = aster.y;
			by = point.y;
		}

		lefttop = new Point(lx, ty);
		rightbottom = new Point(rx, by);
	}

	void start(Graphics g, CanvasControl c) {
		for (time = 0; time < 17; time++) {
			g.lock();
			c.field.repaintAsterRect(g, lefttop, rightbottom);
			c.field.setOrignAster(g, aster);

			g.drawImage(effect, time * (point.x - aster.x), time
					* (point.y - aster.y), 0, (CanvasControl.measure - 1)
					* time, CanvasControl.measure - 1,
					CanvasControl.measure - 1);

			g.unlock(true);

			Game.sleep(300 / CanvasControl.f);
		}

		time--;

		g.lock();
		c.paintAsterBack(g, point);
		c.field.setOrignAster(g, point);
		g.drawImage(effect, 0, 0, 0, (CanvasControl.measure - 1) * time,
				CanvasControl.measure - 1, CanvasControl.measure - 1);
		g.unlock(false);

		Game.sleep(10000 / CanvasControl.f);

		c.field.repaintAster(g, c, point);
	}
}
