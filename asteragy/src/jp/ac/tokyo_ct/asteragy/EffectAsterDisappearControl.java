package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Vector;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

final class EffectAsterDisappearControl extends Effect {
	private static final int frame = 10;

	private static final Image image = Game.loadImage("disappear");

	Vector disappearing = new Vector(20);

	void start(Graphics g, CanvasControl c) {
		if (disappearing.size() == 0)
			return;
		for (int i = 0; i < frame; ++i) {
			g.lock();
			Enumeration en = disappearing.elements();
			while (en.hasMoreElements()) {
				paintAster(c, g, (Point) en.nextElement(), i);
			}
			g.unlock(false);
			Game.sleep(1000 / CanvasControl.f);
		}
		disappearing.removeAllElements();
	}

	void paintAster(CanvasControl c, Graphics g, Point pt, int time) {
		c.paintAsterBack(g, pt);
		c.field.setOrignAster(g, pt);
		g.drawImage(image, 1, 1, 0, time * (CanvasControl.measure - 1),
				CanvasControl.measure - 1, CanvasControl.measure - 1);
	}
}
