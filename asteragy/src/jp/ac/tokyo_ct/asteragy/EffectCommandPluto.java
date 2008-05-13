package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandPluto extends Effect {

	private static final int frame = 6;

	private static final Image effect = Game.loadImage("pluto_effect");

	private final Point aster;

	private Point size;

	private Point location;

	// private boolean end;

	// private boolean circle;

	// private Graphics g;

	EffectCommandPluto(Field field, Point aster) {
		this.aster = aster;
	}

	private void setBounds(CanvasControl c) {
		size = new Point(effect.getWidth(), effect.getHeight());
		location = new Point(c.getLeftMargin(), c.getTopMargin());
		location.x += aster.x * CanvasControl.measure + CanvasControl.measure
				/ 2;
		location.y += aster.y * CanvasControl.measure + CanvasControl.measure
				/ 2;
		location.x -= size.x / 2;
		location.y -= size.y / 2;
		System.out.println("size(" + size.x + "," + size.y + ")");
		System.out.println("location(" + location.x + "," + location.y + ")");
	}

	void start(Graphics g, CanvasControl c) {
		setBounds(c);
		// ”wŒiŽæ“¾
		Image back = c.getScreen(location, size);

		int r = 0;
		int l = 0;

		g.setOrigin(location.x, location.y);
		// g.clipRect(0, 0, size.x, size.y);

		while (l < size.x) {
			g.lock();
			g.drawImage(effect, r, 0, r, 0, frame, size.y);
			g.drawImage(back, l, 0, l, 0, frame, size.y);
			g.unlock(true);

			if (r > 9)
				l += frame;

			if (r <= size.x)
				r += frame;

			Game.sleep(300 / CanvasControl.f);
		}

		// g.clearClip();

		g.drawImage(effect, 0, 0);

		Game.sleep(1000 / CanvasControl.f);

		g.setOrigin(0, 0);

		for (int i = 0; i < 10; i++) {
			g.lock();
			g.setColor(Graphics.getColorOfRGB(255, 255, 255, i * 25));
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.unlock(true);
			Game.sleep(1000 / CanvasControl.f);
		}
	}
}
