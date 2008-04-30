package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

abstract class Effect {

	static boolean isEffect = true;

	abstract void start(Graphics g, CanvasControl c);

	static void setEffect() {
		isEffect = !isEffect;
	}

	static void setEffect(boolean b) {
		isEffect = b;
	}
}
