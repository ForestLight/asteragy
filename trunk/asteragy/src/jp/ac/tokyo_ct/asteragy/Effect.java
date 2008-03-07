package jp.ac.tokyo_ct.asteragy;

public abstract class Effect {

	protected static boolean isEffect = false;
	
	public abstract void start();
	
	public static void setEffect(){
		isEffect = !isEffect;
	}
}
