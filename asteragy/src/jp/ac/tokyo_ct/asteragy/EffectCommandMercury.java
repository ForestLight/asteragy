package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

final class EffectCommandMercury extends Effect {

	// private static Image effect;

	private final Field field;

	private final Point point;

	// private final Aster aster;

	// private final PaintAsterItem paint;

	// private int time;

	EffectCommandMercury(Field field, Point point) {
		this.field = field;
		this.point = point;
		// aster = field.getAster(point);
		// paint = aster.getPaint();
		// loadImage();
	}

	void start(Graphics g) {
		int r = 24;

		Image back = field.getCanvas().getScreen(
				field.getAsterLocation(point.add(new Point(-1, -1))),
				new Point(CanvasControl.measure * 3, CanvasControl.measure * 3));

		field.setOrignAster(g, point, CanvasControl.measure / 2 - 1,
				CanvasControl.measure / 2 - 1);

		g.setColor(Graphics.getColorOfName(Graphics.WHITE));

		int theta = 0;

		while (r > 0) {
			g.lock();

			g.drawImage(back, -back.getWidth() / 2 + 2,
					-back.getHeight() / 2 + 2);

			for (int i = 0; i < 8; i++) {
				g.fillArc(r * SimpleMath.cos(i * SimpleMath.cycle / 8 + theta)
						/ SimpleMath.divide, r
						* SimpleMath.sin(i * SimpleMath.cycle / 8 + theta)
						/ SimpleMath.divide, 3, 3, 0, 360);
			}

			g.unlock(true);

			r--;
			theta += 5;

			Game.sleep(300 / CanvasControl.f);
		}
	}
}
