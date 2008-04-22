package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandVenus extends Effect {

	private final Field field;

	private final Point point;

	public EffectCommandVenus(Field field, Point point) {
		this.field = field;
		this.point = point;
	}
	
	public void start(Graphics g) {
		Point size = new Point(CanvasControl.measure, CanvasControl.measure);
		Image image = field.getCanvas().getScreen(
				field.getAsterLocation(point), size);

		Graphics ig = image.getGraphics();
		int[] pixels = ig.getPixels(0, 0, size.x, size.y, null, 0);
		ig.dispose();

		field.setOrignAster(g, point);
		for (int i = 0; i < 10; i++) {
			slide(pixels, size.x, size.y);
			Graphics ig2 = image.getGraphics();
			ig2.setPixels(0, 0, size.x, size.y, pixels, 0);
			ig2.dispose();
			g.lock();
			g.drawImage(image, 1, 1);
			g.unlock(true);

			Game.sleep(100 / CanvasControl.f);
		}
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
