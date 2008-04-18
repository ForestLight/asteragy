package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Vector;

import com.nttdocomo.ui.Graphics;

public final class EffectAsterDisappearControl extends Effect implements Runnable {

	private static final int capacity = 20;

	final private CanvasControl canvas;

	private Vector disappearing;

	private Graphics g;

	public EffectAsterDisappearControl(CanvasControl canvas) {
		this.canvas = canvas;
		this.disappearing = new Vector(capacity);
	}

	public void Add(EffectAsterDisappearing aster) {
		disappearing.addElement(aster);
	}

	public void start(Graphics g) {
		this.g = g;
		if (!isEffect) {
			Enumeration i = disappearing.elements();
			Graphics scr = canvas.getScreen().getGraphics();
			while (i.hasMoreElements()) {
				((EffectAsterDisappearing) i.nextElement()).endEffect(scr);
			}
			return;
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (disappearing.size() > 0) {
			Enumeration i = disappearing.elements();
			while (i.hasMoreElements()) {
				synchronized (g) {
					g.lock();
					EffectAsterDisappearing aster = (EffectAsterDisappearing) i
							.nextElement();
					if (aster.increaseTime()) {
						aster.endEffect(g);
						disappearing.removeElement(aster);
						continue;
					}
					aster.repaint(g);
					g.unlock(false);
				}
			}

			Game.sleep(1000 / CanvasControl.f);
		}
		canvas.getField().repaintField(canvas.getScreen().getGraphics());
		canvas.getScreen().flipScreen();
	}

}
