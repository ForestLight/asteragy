package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandEarth extends Effect {

	private static final Image effect = Game.loadImage("earth_effect");

	private final Field field;

	private final Point point;

	public EffectCommandEarth(Field field, Point point) {
		this.field = field;
		this.point = point;
	}

	public void start(Graphics g) {
		for (int i = 0; i < 10; i++) {
			g.lock();
			field.repaintAster(g, point);
			field.setOrignAster(g, point);
			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);
			g.unlock(true);
			Game.sleep(1000 / CanvasControl.f);
		}
		for (int i = 10; i != 0; i--) {
			g.lock();
			CanvasControl.paintAsterBack(g, point);
			field.setOrignAster(g, point);
			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);
			g.unlock(true);
			Game.sleep(1000 / CanvasControl.f);
		}
	}

}
