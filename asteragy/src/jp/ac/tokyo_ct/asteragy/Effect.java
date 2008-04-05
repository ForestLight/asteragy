package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public abstract class Effect {

	protected static boolean isEffect = true;
	
	public abstract void start(Graphics g);
	
	public static void setEffect(){
		isEffect = !isEffect;
	}
	public static void setEffect(boolean b){
		isEffect = b;
	}
}
