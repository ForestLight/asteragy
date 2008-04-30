package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandMoon extends Effect {

	// private static Image effect;

	private final Point aster;

	private final Point point;

	EffectCommandMoon(Point me, Point pt) {
		aster = me;
		point = pt;
	}

	void start(Graphics g, CanvasControl c) {
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		for (int i = 17; i != 0; i--) {
			g.lock();
			c.field.setOrignAster(g, aster);
			g.setClip(0, 0, CanvasControl.measure, CanvasControl.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			c.field.setOrignAster(g, point);
			g.setClip(0, 0, CanvasControl.measure, CanvasControl.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		for (int i = 0; i >= -17; i--) {
			g.lock();
			c.field.setOrignAster(g, aster);
			g.setClip(0, 0, CanvasControl.measure, CanvasControl.measure);
			g.setColor(Graphics.getColorOfName(Graphics.WHITE));
			g.fillArc(0, 0, 17, 17, 0, 360);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}
	}

}
