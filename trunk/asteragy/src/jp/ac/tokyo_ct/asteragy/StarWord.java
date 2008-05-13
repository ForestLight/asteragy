package jp.ac.tokyo_ct.asteragy;

//import java.util.Random;

import com.nttdocomo.ui.*;

class StarWord {

	// private final CanvasControl canvas;

	// private static final Random random = new Random();

	// private final Image star = Game.loadImage("star_word.gif");

	private final Point point;

	private final int space;

	private StarWord next;

	// private int[] matrix;

	// private int time;

	StarWord(ImagePixels pixels, int space, Point point) {
		// this.canvas = canvas;
		this.point = point;
		this.space = space;
		// matrix = new int[6];
		// time = 0;
		Point nextpoint = point.clone();
		nextPoint(pixels, nextpoint);
		while (pixels.hasMoreElements()) {
			int i = pixels.nextElement();
			if (i == 0x0) {
				next = new StarWord(pixels, space, nextpoint);
				break;
			} else {
				nextPoint(pixels, nextpoint);
			}
		}
	}

	private void nextPoint(ImagePixels pixels, Point now) {
		if (pixels.isLeftPixels()) {
			now.x = 0;
			now.y += space;
		} else {
			now.x += space;
		}
	}

	void paint(Graphics g) {
		// g.drawImage(star, point.x, point.y);
		// g.drawImage(star, matrix);
		// g.setColor(Graphics.getColorOfRGB(255, 255, 100));
		// g.fillArc(point.x - 2, point.y - 2, space + 4, space + 4, 0, 360);
		g.setColor(Graphics.getColorOfRGB(255, 240, 0));
		g.fillArc(point.x - 1, point.y - 1, space + 2, space + 2, 0, 360);
		// g.setColor(Graphics.getColorOfRGB(155, 155, 0));
		// g.fillArc(point.x, point.y, space, space, 0, 360);

		// time++;

		if (next != null)
			next.paint(g);
	}
}
