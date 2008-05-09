package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandStar extends Effect {

	private static final Image effect = Game.loadImage("star_effect");

	private final Point point;

	private final Point to;

	// 0:¶ 2:‰E 1:ã 3:‰º
	private final int direction;

	EffectCommandStar(Field field, AsterClass cls, Point a, Point b) {
		if (cls.getPoint().equals(a)) {
			point = a;
			to = b;
		} else {
			point = b;
			to = a;
		}
		if (point.x > to.x)
			direction = 0;
		else if (point.x < to.x)
			direction = 2;
		else if (point.y > to.y)
			direction = 1;
		else
			direction = 3;
	}

	final int theta = SimpleMath.cycle / 30;

	void start(Graphics g, CanvasControl c) {
		int[] matrix = new int[6];

		for (int i = 0; i < 15; i++) {

			matrix[0] = SimpleMath.cos(theta * i);
			matrix[1] = -SimpleMath.sin(theta * i);
			matrix[2] = SimpleMath.cos(theta * i) * (-effect.getWidth() / 2)
					- SimpleMath.sin(theta * i) * (-effect.getHeight() / 2)
					+ (effect.getWidth() / 2 + 1) * SimpleMath.divide;
			matrix[3] = SimpleMath.sin(theta * i);
			matrix[4] = SimpleMath.cos(theta * i);
			matrix[5] = SimpleMath.sin(theta * i) * (-effect.getWidth() / 2)
					+ SimpleMath.cos(theta * i) * (-effect.getHeight() / 2)
					+ (effect.getWidth() / 2 + 1) * SimpleMath.divide;

			g.lock();
			c.paintAsterBack(g, to);
			c.field.setOrignAster(g, to);

			g.drawImage(effect, matrix);

			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		Aster paint = c.field.at(point);

		int ax = 0;
		int ay = 0;

		for (int i = 0; i < CanvasControl.measure; i++) {
			c.paintAsterBack(g, to);
			c.paintAsterBack(g, point);
			g.lock();

			c.field.setOrignAster(g, point, ax, ay);
			paint.paint(g);

			g.unlock(true);

			if (direction % 2 == 0) {
				ax += (direction - 1);
			} else {
				ay += (direction - 2);
			}

			Game.sleep(50 / CanvasControl.f);
		}
	}
}
