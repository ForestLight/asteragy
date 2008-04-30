package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EffectCommandUranus extends Effect {

	// private final Field field;

	private final Point target1;

	private final Point target2;

	private Point direct1;

	private Point direct2;

	EffectCommandUranus(Point target1, Point target2) {
		this.target1 = target1;
		this.target2 = target2;
		setDirect();
		// loadImage();
	}

	private void setDirect() {
		direct1 = new Point();
		direct2 = new Point();
		direct1.x = target1.x - target2.x;
		direct1.y = target1.y - target2.y;
		// if (direct1.x != 0)
		// direct1.x /= Math.abs(direct1.x);
		// if (direct1.y != 0)
		// direct1.y /= Math.abs(direct1.y);
		direct2.x = -direct1.x;
		direct2.y = -direct1.y;
	}

	void start(Graphics g, CanvasControl c) {
		Point size = new Point(CanvasControl.measure - 1,
				CanvasControl.measure - 1);
		Image t1 = c.getScreen(c.field.getAsterLocation(target1), size);
		Image t2 = c.getScreen(c.field.getAsterLocation(target2), size);

		for (int i = 0; i < 10; i++) {
			filt(t1, direct1);
			filt(t2, direct2);
			// ave(t1);
			// ave(t2);
			g.lock();
			c.field.setOrignAster(g, target1);
			g.drawImage(t1, 0, 0);
			c.field.setOrignAster(g, target2);
			g.drawImage(t2, 0, 0);
			g.unlock(true);

			Game.sleep(400 / CanvasControl.f);

		}
	}

	private void filt(Image image, Point dir) {
		Graphics g = image.getGraphics();
		final int height = image.getHeight();
		final int width = image.getWidth();
		int[] pixsel = new int[width * height];
		int[] con = g.getRGBPixels(0, 0, width, height, null, 0);
		int[] check = new int[] { width * (dir.y - 1) + dir.x - 1,
				width * (dir.y - 1) + dir.x, width * (dir.y - 1) + dir.x + 1,
				width * dir.y + dir.x - 1, width * dir.y + dir.x,
				width * dir.y + dir.x + 1, width * (dir.y + 1) + dir.x - 1,
				width * (dir.y) + dir.x, width * (dir.y + 1) + dir.x + 1 };
		for (int i = 0; i < pixsel.length; i++) {
			int c = 0;
			int[] rgb = new int[3];
			for (int j = 0; j < 3; j++) {
				rgb[j] = 0;
			}
			for (int j = 0; j < check.length; j++) {
				if (i + check[j] < 0 || i + check[j] >= con.length
						|| i % width + (j % 3 - 1) + dir.x < 0
						|| i % width + (j % 3 - 1) + dir.x >= width) {
					continue;
				}
				rgb[0] += (con[i + check[j]] >>> 16) & 0xff;
				rgb[1] += (con[i + check[j]] >>> 8) & 0xff;
				rgb[2] += con[i + check[j]] & 0xff;
				c++;
			}
			for (int j = 0; j < 3; j++) {
				if (c != 0)
					rgb[j] /= c;
			}
			pixsel[i] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		}
		g.setRGBPixels(0, 0, width, height, pixsel, 0);
		g.dispose();
	}
}
