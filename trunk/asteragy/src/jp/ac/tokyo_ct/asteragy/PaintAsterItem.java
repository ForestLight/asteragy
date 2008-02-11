package jp.ac.tokyo_ct.asteragy;

public interface PaintAsterItem extends PaintItem {

	public void setColor(int color);

	public void setClass(AsterClass aster);
	
	public int getWidth();
	
	public int getHeight();
	
	public void setSize(int width, int height);
	
	public void resetSize();

}
