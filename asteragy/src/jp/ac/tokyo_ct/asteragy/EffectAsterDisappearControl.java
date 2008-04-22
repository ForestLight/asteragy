package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Vector;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public final class EffectAsterDisappearControl extends Effect {
	private static final int frame = 10;

	private static final Image image = Game.loadImage("disappear");

	private final Vector disappearing = new Vector(20);

	void Add(Point pt) {
		disappearing.addElement(pt);
	}
	void Clear(){
		disappearing.removeAllElements();
	}

	public void start(Graphics g) {

		if (disappearing.size() == 0)
			return;
		Field f = Main.game.getField();
		for (int i = 0; i < frame; ++i) {
			g.lock();
			Enumeration en = disappearing.elements();
			while (en.hasMoreElements()) {
				paintAster(f, g, (Point) en.nextElement(), i);
			}
			g.unlock(false);
			Game.sleep(1000 / CanvasControl.f);
		}
		disappearing.removeAllElements();
	}

	void paintAster(Field f, Graphics g, Point pt, int time) {
		CanvasControl.paintAsterBack(g, pt);
		f.setOrignAster(g, pt);
		g.drawImage(image, 1, 1, 0, time * (CanvasControl.measure - 1),
				CanvasControl.measure - 1, CanvasControl.measure - 1);
	}
}
