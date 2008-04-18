package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandNeptune extends Effect {

	//private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandNeptune(Field field, Point point) {
		this.field = field;
		this.point = point;
		// loadImage();
	}

	/*
	 * private void loadImage() {
	 * 
	 * if (effect != null) return; try { // リソースから読み込み MediaImage m =
	 * MediaManager .getImage("resource:///neptune_effect.gif"); // メディアの使用開始
	 * m.use(); // 読み込み effect = m.getImage(); } catch (Exception e) { } }
	 */

	public void start(Graphics g) {
		if (!isEffect)
			return;

		g.setOrigin(0, 0);

		Point from = new Point(field.getGame().getCanvas().getLeftMargin()
				+ point.x * GameCanvas.measure + GameCanvas.measure / 2, 0);
		Point to = new Point(field.getGame().getCanvas().getLeftMargin()
				+ point.x * GameCanvas.measure + GameCanvas.measure / 2, field
				.getGame().getCanvas().getTopMargin()
				+ point.y * GameCanvas.measure + GameCanvas.measure / 2);
		Thunder thunder = new Thunder(from, to);
		thunder.paint(g);
		/*
		 * for (int i = 0; i < 30; i++) { g.lock();
		 * 
		 * g.drawImage(effect, 0, -1 * effect.getHeight(), 0, 0, effect
		 * .getWidth(), effect.getHeight() / 10 * i);
		 * 
		 * g.unlock(true);
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) {
		 * e.printStackTrace(); } }
		 */

		Game.sleep(30000 / CanvasControl.f);

		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.fillRect(0, 0, field.getGame().getCanvas().getWidth(), field
				.getGame().getCanvas().getHeight());

		Game.sleep(300 / CanvasControl.f);

		field.getGame().getCanvas().repaint();
		field.getScreen().flipScreen();
	}
}
