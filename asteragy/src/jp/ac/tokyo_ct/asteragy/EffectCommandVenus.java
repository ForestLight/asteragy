package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandVenus extends Effect {

	// private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandVenus(Field field, Point point) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.field = field;
		this.point = point;
		// loadImage();
	}

	/*
	 * private void loadImage() { if (effect != null) return;
	 * 
	 * try { // リソースから読み込み MediaImage m = MediaManager
	 * .getImage("resource:///venus_effect.gif"); // メディアの使用開始 m.use(); // 読み込み
	 * effect = m.getImage(); } catch (Exception e) { } }
	 */

	public void start(Graphics g) {
		if (!isEffect)
			return;
		// TODO 自動生成されたメソッド・スタブ

		Point size = new Point(GameCanvas.measure, GameCanvas.measure);
		Image image = field.getScreen().getScreen(
				field.getAsterLocation(point), size);

		Graphics ig = image.getGraphics();
		int[] pixels = ig.getPixels(0, 0, size.x, size.y, null, 0);

		field.setOrignAster(g, point);
		for (int i = 0; i < 10; i++) {
			slide(pixels, size.x, size.y);
			ig.setPixels(0, 0, size.x, size.y, pixels, 0);
			g.lock();
			g.drawImage(image, 1, 1);
			g.unlock(true);

			Game.sleep(10000 / CanvasControl.f);
		}

		/*
		 * final BackImage back = field.getGame().getCanvas().getBackImage();
		 * 
		 * final int w = GameCanvas.measure / 2; final int h = 8;
		 * 
		 * final int theta = SimpleMath.cycle / 12;
		 * 
		 * for (int i = 0; i < 100; i++) {
		 * 
		 * g.lock();
		 * 
		 * back.paintAsterBack(g, point);
		 * 
		 * if (i % 12 <= 6) field.repaintAster(g, point);
		 * 
		 * field.setOrignAster(g, point, w, w);
		 * 
		 * g.drawImage(effect, w * SimpleMath.cos(theta * (i + 1)) /
		 * SimpleMath.divide, h * SimpleMath.sin(theta * (i + 1)) /
		 * SimpleMath.divide);
		 * 
		 * if (i % 12 >= 7) field.repaintAster(g, point);
		 * 
		 * g.unlock(true);
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } }
		 */

	}

	private void slide(int[] pixels, int width, int height) {
		if (pixels.length != width * height)
			return;
		for (int i = 0; i < height; i += 2) {
			for (int j = width - 1; j > 0; j--) {
				pixels[i * width + j] = pixels[i * width + j - 1];
			}
		}
		for (int i = 1; i < height; i += 2) {
			for (int j = 1; j < width; j++) {
				pixels[i * width + j - 1] = pixels[i * width + j];
			}
		}
	}

}
