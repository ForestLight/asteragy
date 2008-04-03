package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Vector;

public class EffectAsterDisappearControl extends Effect implements Runnable {

	private static final int capacity = 20;

	final private CanvasControl canvas;

	private Vector disappearing;

	public EffectAsterDisappearControl(CanvasControl canvas) {
		this.canvas = canvas;
		this.disappearing = new Vector(capacity);
	}

	public void Add(EffectAsterDisappearing aster) {
		disappearing.addElement(aster);
	}

	public void start() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (!isEffect) {
			Enumeration i = disappearing.elements();
			while (i.hasMoreElements()) {
				((EffectAsterDisappearing) i.nextElement()).endEffect();
			}
			return;
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		while (disappearing.size() > 0) {
			Enumeration i = disappearing.elements();
			while (i.hasMoreElements()) {
				EffectAsterDisappearing aster = (EffectAsterDisappearing) i
						.nextElement();
				if (aster.increaseTime()) {
					aster.endEffect();
					disappearing.removeElement(aster);
					continue;
				}
				aster.repaint();
			}

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}
	}

}
