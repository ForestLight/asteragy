package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandNeptune extends Effect {

	// private static Image effect;

	private final Point point;

	EffectCommandNeptune(Point point) {
		this.point = point;
		// loadImage();
	}

	void start(Graphics g, CanvasControl c) {
		g.setOrigin(0, 0);

		Point from = new Point(c.getLeftMargin() + point.x
				* CanvasControl.measure + CanvasControl.measure / 2, 0);
		Point to = new Point(c.getLeftMargin() + point.x
				* CanvasControl.measure + CanvasControl.measure / 2, c
				.getTopMargin()
				+ point.y * CanvasControl.measure + CanvasControl.measure / 2);
		Thunder thunder = new Thunder(from, to);
		thunder.paint(g);

		Game.sleep(30000 / CanvasControl.f);

		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.fillRect(0, 0, c.getWidth(), c.getHeight());

		Game.sleep(300 / CanvasControl.f);
	}
}
