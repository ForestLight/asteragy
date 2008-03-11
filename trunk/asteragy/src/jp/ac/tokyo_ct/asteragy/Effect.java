package jp.ac.tokyo_ct.asteragy;

public abstract class Effect {

	protected static boolean isEffect = true;
	
	public abstract void start();
	
	public static void setEffect(){
		isEffect = !isEffect;
	}
	public static void setEffect(boolean b){
		isEffect = b;
	}
}
