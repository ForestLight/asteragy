package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandNeptune extends Effect {

	//private static Image effect;

	private final Field field;

	private final Point point;

	EffectCommandNeptune(Field field, Point point) {
		this.field = field;
		this.point = point;
		// loadImage();
	}

	void start(Graphics g) {
		g.setOrigin(0, 0);

		final CanvasControl canvas = field.game.getCanvas();
		Point from = new Point(canvas.getLeftMargin()
				+ point.x * CanvasControl.measure + CanvasControl.measure / 2, 0);
		Point to = new Point(canvas.getLeftMargin()
				+ point.x * CanvasControl.measure + CanvasControl.measure / 2,
				canvas.getTopMargin()
				+ point.y * CanvasControl.measure + CanvasControl.measure / 2);
		Thunder thunder = new Thunder(from, to);
		thunder.paint(g);

		Game.sleep(30000 / CanvasControl.f);

		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		Game.sleep(300 / CanvasControl.f);
	}
}
