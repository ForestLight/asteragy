package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandSaturn extends Effect {

	private static Image effect = Game.loadImage("saturn_effect.gif");

	private final Field field;

	private final Point aster;

	private final Aster[] queue;

	private final Point[] location;

	public EffectCommandSaturn(Field field, AsterClass cls, Aster[] queue) {
		this.field = field;
		this.aster = field.asterToPoint(cls.getAster());
		this.queue = queue;

		location = new Point[queue.length];
		for (int i = 0; i < location.length; i++) {
			location[i] = field.asterToPoint(queue[i]);
		}

		/*
		 * for (int i = 1, j = 0; j < 16; j++) { if (queue[i] == null) break; if
		 * (location[i] == null) location[i] = location[i - 1].clone();
		 * 
		 * if (j < 4) location[i].x++; else if (j < 8) location[i].y++; else if
		 * (j < 12) location[i].x--; else location[i].y--;
		 * 
		 * if (field.isXInFieldBound(location[i].x) &&
		 * field.isYInFieldBound(location[i].y)) { i++; } }
		 */
	}

	public void start(Graphics g) {
		if (!isEffect)
			return;

		int[] matrix = new int[6];

		int theta = SimpleMath.cycle / 8;

		int r = ((GameCanvas.measure - 1) * 3 / 2) * SimpleMath.cos(theta);

		int x = effect.getWidth() / 2 + r / SimpleMath.divide;
		int y = effect.getHeight() / 2;
		int width = effect.getWidth() / 2 + r / SimpleMath.divide;
		int height = (effect.getHeight() / 2) * SimpleMath.sin(theta)
				/ SimpleMath.divide;

		field.setOrignAster(g, aster, GameCanvas.measure / 2,
				GameCanvas.measure / 2);

		for (int i = 0; i < 9; i++) {
			matrix[2] = r * SimpleMath.cos(theta * i) / SimpleMath.divide;
			matrix[5] = -1 * r * SimpleMath.sin(theta * i) / SimpleMath.divide;
			matrix[0] = SimpleMath.cos(theta * i);
			matrix[1] = SimpleMath.sin(theta * i);
			matrix[3] = -1 * SimpleMath.sin(theta * i);
			matrix[4] = SimpleMath.cos(theta * i);

			g.lock();
			g.drawImage(effect, matrix, x, y, width, height);
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		field.repaintAsterRect(g, new Point(aster.x - 1, aster.y - 1),
				new Point(aster.x + 1, aster.y + 1));

		for (int i = 0; i < GameCanvas.measure - 1; i++) {

			g.lock();

			field.getGame().getCanvas().getBackImage().paintAsterBack(g,
					location[0]);
			for (int j = 0; j < location.length; j++) {
				if (location[j] == null)
					break;

				if (location[j + 1] != null) {
					field.getGame().getCanvas().getBackImage().paintAsterBack(
							g, location[j + 1]);

					field.setOrignAster(g, location[j], i
							* (location[j + 1].x - location[j].x), i
							* (location[j + 1].y - location[j].y));
				} else {
					field.setOrignAster(g, location[j], i
							* (location[0].x - location[j].x), i
							* (location[0].y - location[j].y));
				}

				queue[j].getPaint().paint(g);

			}

			field.setOrignAster(g, aster, GameCanvas.measure / 2,
					GameCanvas.measure / 2);
			g
					.drawImage(effect, -effect.getWidth() / 2, -effect
							.getHeight() / 2);

			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

	}
}
