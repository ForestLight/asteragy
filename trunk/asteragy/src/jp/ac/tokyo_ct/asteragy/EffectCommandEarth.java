package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandEarth extends Effect {

	private static final Image effect = Game.loadImage("earth_effect");

	private final Point point;

	EffectCommandEarth(Point point) {
		this.point = point;
	}

	void start(Graphics g, CanvasControl c) {
		for (int i = 0; i < 10; i++) {
			g.lock();
			c.field.repaintAster(g, c, point);
			c.field.setOrignAster(g, point);
			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);
			g.unlock(true);
			Game.sleep(1000 / CanvasControl.f);
		}
		for (int i = 10; i != 0; i--) {
			g.lock();
			c.paintAsterBack(g, point);
			c.field.setOrignAster(g, point);
			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);
			g.unlock(true);
			Game.sleep(1000 / CanvasControl.f);
		}
	}

}
