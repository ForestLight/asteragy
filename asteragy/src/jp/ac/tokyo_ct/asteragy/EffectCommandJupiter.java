package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandJupiter extends Effect {

	private static final Image effect = Game.loadImage("jupiter_effect");

	// private static Image tranc;

	private final Point point;

	private Point center;

	private Point[] circle;

	// private Point lefttop;

	// private Point rightbottom;

	EffectCommandJupiter(Point point) {
		this.point = point;
		// setLocation();
		// imageTranclucently();
	}

	void start(Graphics g, CanvasControl c) {
		{
			center = c.field.getAsterLocation(point);
			center.x += CanvasControl.measure / 2;
			center.y += CanvasControl.measure / 2;
			final int r = effect.getWidth() * 2;
			final int theta = SimpleMath.cycle / 8;
			circle = new Point[8];
			for (int i = 0; i < circle.length; i++) {
				circle[i] = center.clone();
				circle[i].x *= SimpleMath.divide;
				circle[i].y *= SimpleMath.divide;
				circle[i].x += r * SimpleMath.cos(i * theta);
				circle[i].y += r * SimpleMath.sin(i * theta);
			}
		}
		Point l = c.field.getAsterLocation(point.add(new Point(-1, -1)));
		Point s = new Point(CanvasControl.measure * 3, CanvasControl.measure * 3);
		Image back = c.field.getCanvas().getScreen(l, s);

		int[] matrix = new int[6];
		final int theta = -SimpleMath.cycle / 8;
		final Point e = new Point(effect.getWidth(), effect.getHeight());

		center.x *= SimpleMath.divide;
		center.y *= SimpleMath.divide;

		// field.setOrignAster(g, point, GameCanvas.measure / 2,
		// GameCanvas.measure / 2);
		for (int i = 0; i < circle.length; i++) {

			g.lock();

			g.drawImage(back, l.x, l.y);

			matrix[2] = circle[i].x + SimpleMath.cos(theta * i) * -e.x / 2
					+ SimpleMath.sin(theta * i) * -e.y / 2;
			matrix[5] = circle[i].y - SimpleMath.sin(theta * i) * -e.x / 2
					+ SimpleMath.cos(theta * i) * -e.y / 2;
			matrix[0] = SimpleMath.cos(theta * i);
			matrix[1] = SimpleMath.sin(theta * i);
			matrix[3] = -SimpleMath.sin(theta * i);
			matrix[4] = SimpleMath.cos(theta * i);

			g.drawImage(effect, matrix);

			g.unlock(true);

			Game.sleep(600 / CanvasControl.f);

			g.lock();

			g.drawImage(back, l.x, l.y);

			g.unlock(true);
			Game.sleep(100 / CanvasControl.f);
			g.lock();

			matrix[2] = center.x + SimpleMath.cos(theta * i) * -e.x * 3 / 5
					+ SimpleMath.sin(theta * i) * -e.y * 3 / 5;
			matrix[5] = center.y - SimpleMath.sin(theta * i) * -e.x * 3 / 5
					+ SimpleMath.cos(theta * i) * -e.y * 3 / 5;
			matrix[0] = SimpleMath.cos(theta * i) * 6 / 5;
			matrix[1] = SimpleMath.sin(theta * i) * 6 / 5;
			matrix[3] = -SimpleMath.sin(theta * i) * 6 / 5;
			matrix[4] = SimpleMath.cos(theta * i) * 6 / 5;

			g.drawImage(effect, matrix);

			g.unlock(true);
			Game.sleep(100 / CanvasControl.f);

			g.lock();

			g.drawImage(back, l.x, l.y);

			g.unlock(true);
			Game.sleep(100 / CanvasControl.f);
		}
	}
}
