package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandSaturn extends Effect {

	private static final Image effect = Game.loadImage("saturn_effect");

	private final Field field;

	private final Point aster;

	private final Aster[] queue;

	private final Point[] location;

	EffectCommandSaturn(Field field, AsterClass cls, Aster[] queue) {
		this.field = field;
		this.aster = cls.getPoint();
		this.queue = queue;

		location = new Point[queue.length];
		for (int i = 0; i < location.length; i++) {
			location[i] = field.asterToPoint(queue[i]);
		}
	}

	void start(Graphics g, CanvasControl c) {
		final int width2 = effect.getWidth() / 2;
		final int height2 = effect.getHeight() / 2;
		final int width2m = -width2;
		final int height2m = -height2;

		int[] matrix = new int[6];

		int theta = SimpleMath.cycle / 8;

		int r = ((CanvasControl.measure - 1) * 3 / 2) * SimpleMath.cos(theta);

		int x = width2 + r / SimpleMath.divide;
		int y = height2 / 2;
		int width = width2 + r / SimpleMath.divide;
		int height = height2 * SimpleMath.sin(theta)
				/ SimpleMath.divide;

		field.setOrignAster(g, aster, CanvasControl.measure / 2,
				CanvasControl.measure / 2);

		for (int i = 0; i < 9; i++) {
			matrix[2] = r * SimpleMath.cos(theta * i) / SimpleMath.divide;
			matrix[5] = -r * SimpleMath.sin(theta * i) / SimpleMath.divide;
			matrix[0] = SimpleMath.cos(theta * i);
			matrix[1] = SimpleMath.sin(theta * i);
			matrix[3] = -SimpleMath.sin(theta * i);
			matrix[4] = SimpleMath.cos(theta * i);

			g.lock();
			g.drawImage(effect, matrix, x, y, width, height);
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		field.repaintAsterRect(g, new Point(aster.x - 1, aster.y - 1),
				new Point(aster.x + 1, aster.y + 1));

		for (int i = 0; i < CanvasControl.measure - 1; i++) {

			g.lock();

			c.paintAsterBack(g, location[0]);
			for (int j = 0; j < location.length; j++) {
				if (location[j] == null)
					break;

				if (location[j + 1] != null) {
					c.paintAsterBack(g, location[j + 1]);

					field.setOrignAster(g, location[j], i
							* (location[j + 1].x - location[j].x), i
							* (location[j + 1].y - location[j].y));
				} else {
					field.setOrignAster(g, location[j], i
							* (location[0].x - location[j].x), i
							* (location[0].y - location[j].y));
				}

				queue[j].paint(g);

			}

			field.setOrignAster(g, aster, CanvasControl.measure / 2,
					CanvasControl.measure / 2);
			g.drawImage(effect, width2m, height2m);

			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

	}
}
