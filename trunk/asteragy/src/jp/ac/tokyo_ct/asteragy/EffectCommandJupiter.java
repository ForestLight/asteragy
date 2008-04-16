package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandJupiter extends Effect {

	private static Image effect;

	// private static Image tranc;

	private final Field field;

	private final Point point;

	private Point center;

	private Point[] circle;

	// private Point lefttop;

	// private Point rightbottom;

	public EffectCommandJupiter(Field field, Point point) {
		this.field = field;
		this.point = point;
		// setLocation();
		loadImage();
		// imageTranclucently();
		setCircle();
	}

	/*
	 * private void setLocation() { lefttop = point.clone(); rightbottom =
	 * point.clone();
	 * 
	 * lefttop.x -= 1; lefttop.y -= 6; rightbottom.x += 2; }
	 */

	private void loadImage() {
		if (effect != null)
			return;

		try {
			// リソースから読み込み
			MediaImage m = MediaManager
					.getImage("resource:///jupiter_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	/*
	 * private void imageTranclucently() { // 背景黒で半透明化 tranc =
	 * Image.createImage(effect.getWidth(), effect.getHeight()); Graphics f =
	 * effect.getGraphics(); Graphics t = tranc.getGraphics(); int[] pixels =
	 * f.getRGBPixels(0, 0, effect.getWidth(), effect .getHeight(), null, 0);
	 * for (int i = 0; i < pixels.length; i++) { pixels[i] = (pixels[i] >>> 1) &
	 * 0x7f7f7f; } t.setRGBPixels(0, 0, effect.getWidth(), effect.getHeight(),
	 * pixels, 0); t.dispose(); f.dispose(); }
	 */

	private void setCircle() {
		center = field.getAsterLocation(point);
		center.x += GameCanvas.measure / 2;
		center.y += GameCanvas.measure / 2;
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

	public void start(Graphics g) {
		if (!isEffect)
			return;

		Point l = field.getAsterLocation(point.add(new Point(-1, -1)));
		Point s = new Point(GameCanvas.measure * 3, GameCanvas.measure * 3);
		Image back = field.getScreen().getScreen(l, s);

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
			matrix[3] = -1 * SimpleMath.sin(theta * i);
			matrix[4] = SimpleMath.cos(theta * i);

			g.drawImage(effect, matrix);

			g.unlock(true);

			try {
				Thread.sleep(600 / CanvasControl.f);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			g.lock();

			g.drawImage(back, l.x, l.y);

			g.unlock(true);
			try {
				Thread.sleep(100 / CanvasControl.f);
			} catch (InterruptedException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			g.lock();

			matrix[2] = center.x + SimpleMath.cos(theta * i) * -e.x * 3 / 5
					+ SimpleMath.sin(theta * i) * -e.y * 3 / 5;
			matrix[5] = center.y - SimpleMath.sin(theta * i) * -e.x * 3 / 5
					+ SimpleMath.cos(theta * i) * -e.y * 3 / 5;
			matrix[0] = SimpleMath.cos(theta * i) * 6 / 5;
			matrix[1] = SimpleMath.sin(theta * i) * 6 / 5;
			matrix[3] = -1 * SimpleMath.sin(theta * i) * 6 / 5;
			matrix[4] = SimpleMath.cos(theta * i) * 6 / 5;

			g.drawImage(effect, matrix);

			g.unlock(true);
			try {
				Thread.sleep(100 / CanvasControl.f);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			g.lock();

			g.drawImage(back, l.x, l.y);

			g.unlock(true);
			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	/*
	 * public void start(Graphics g) { if(!isEffect) return; // TODO
	 * 自動生成されたメソッド・スタブ
	 * 
	 * final int height = 10;
	 * 
	 * for (int i = 0; i < effect.getHeight(); i++) { g.lock();
	 * 
	 * field.repaintAsterRect(g, lefttop, rightbottom); field.setOrignAster(g,
	 * point, (GameCanvas.measure - effect .getWidth()) / 2, GameCanvas.measure /
	 * 2 - effect.getHeight());
	 * 
	 * g.drawImage(effect, 0, -height, 0, 0, effect.getWidth(), i);
	 * 
	 * g.unlock(true);
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } }
	 * 
	 * for (int i = 0; i < height; i++) {
	 * 
	 * g.lock();
	 * 
	 * field.repaintAsterRect(g, lefttop, rightbottom); field.setOrignAster(g,
	 * point, (GameCanvas.measure - effect .getWidth()) / 2, GameCanvas.measure /
	 * 2 - effect.getHeight());
	 * 
	 * g.drawImage(effect, 0, i - height);
	 * 
	 * g.unlock(true);
	 * 
	 * try { Thread.sleep(300 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } }
	 * 
	 * for (int i = 0; i < height; i++) {
	 * 
	 * g.lock();
	 * 
	 * field.repaintAsterRect(g, lefttop, rightbottom); field.setOrignAster(g,
	 * point, (GameCanvas.measure - effect .getWidth()) / 2, GameCanvas.measure /
	 * 2 - effect.getHeight());
	 * 
	 * g.drawImage(effect, 0, i, 0, 0, effect.getWidth(), effect .getHeight() -
	 * i);
	 * 
	 * g.unlock(true);
	 * 
	 * try { Thread.sleep(300 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } }
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f * 20); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); } }
	 */
}
