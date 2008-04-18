package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandMoon extends Effect {

	// private static Image effect;

	private final Field field;

	private final Point aster;

	private final Point point;

	public EffectCommandMoon(Field f, Point me, Point pt) {
		field = f;
		aster = me;
		point = pt;
		// loadImage();
	}

	/*
	 * private void loadImage() { if (effect != null) return;
	 * 
	 * try { // リソースから読み込み MediaImage m =
	 * MediaManager.getImage("resource:///moon_effect.gif"); // メディアの使用開始
	 * m.use(); // 読み込み effect = m.getImage(); } catch (Exception e) { } }
	 */

	public void start(Graphics g) {
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 17; i >= 0; i--) {
			g.lock();
			field.setOrignAster(g, aster);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			field.setOrignAster(g, point);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		for (int i = 0; i >= -17; i--) {
			g.lock();
			field.setOrignAster(g, aster);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(255, 255, 255));
			g.fillArc(0, 0, 17, 17, 0, 360);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}
	}

}
