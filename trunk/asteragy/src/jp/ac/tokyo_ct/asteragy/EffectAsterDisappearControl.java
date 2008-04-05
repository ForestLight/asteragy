package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Vector;

import com.nttdocomo.ui.Graphics;

public class EffectAsterDisappearControl extends Effect implements Runnable {

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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
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

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		canvas.getField().repaintField(canvas.getScreen().getGraphics());
		canvas.getScreen().flipScreen();
	}

}
