package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public final class EffectCommandMars extends Effect {

	private static final Image effect = Game.loadImage("mars_effect");

	private final Field field;

	private final Point point;

	private final Point aster;

	private Point lefttop;

	private Point rightbottom;

	private int time;

	public EffectCommandMars(Field field, AsterClass cls, Point point) {
		this.field = field;
		this.point = point;
		this.aster = field.asterToPoint(cls.getAster());
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

	public void start(Graphics g) {
		for (time = 0; time < 17; time++) {

			g.lock();
			field.repaintAsterRect(g, lefttop, rightbottom);
			field.setOrignAster(g, aster);

			g.drawImage(effect, time * (point.x - aster.x), time
					* (point.y - aster.y), 0, (CanvasControl.measure - 1) * time,
					CanvasControl.measure - 1, CanvasControl.measure - 1);

			g.unlock(true);

			Game.sleep(300 / CanvasControl.f);


		}

		time--;

		g.lock();
		CanvasControl.paintAsterBack(g, point);
		field.setOrignAster(g, point);
		g.drawImage(effect, 0, 0, 0, (CanvasControl.measure - 1) * time,
				CanvasControl.measure - 1, CanvasControl.measure - 1);
		g.unlock(false);

		Game.sleep(10000 / CanvasControl.f);

		field.repaintAster(g, point);

	}

	public void paint(Graphics g) {
		g.drawImage(effect, 0, 0, 0, (CanvasControl.measure - 1) * time,
				CanvasControl.measure - 1, CanvasControl.measure - 1);
	}

}
