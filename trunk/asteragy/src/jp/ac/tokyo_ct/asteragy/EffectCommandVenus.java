package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandVenus extends Effect {

	private static final Image effect = Game.loadImage("venus_effect.gif");

	private final Field field;

	private final Point point;

	public EffectCommandVenus(Field field, Point point) {
		this.field = field;
		this.point = point;
	}

	public void start(Graphics g) {
		if (!isEffect)
			return;

		final BackImage back = field.getGame().getCanvas().getBackImage();

		final int w = GameCanvas.measure / 2;
		final int h = 8;

		final int theta = SimpleMath.cycle / 12;

		for (int i = 0; i < 100; i++) {

			g.lock();

			back.paintAsterBack(g, point);

			if (i % 12 <= 6)
				field.repaintAster(g, point);

			field.setOrignAster(g, point, w, w);

			g.drawImage(effect, w * SimpleMath.cos(theta * (i + 1))
					/ SimpleMath.divide, h * SimpleMath.sin(theta * (i + 1))
					/ SimpleMath.divide);

			if (i % 12 >= 7)
				field.repaintAster(g, point);

			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

	}

}
