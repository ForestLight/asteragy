package jp.ac.tokyo_ct.asteragy;
import java.util.Random;
/**
 * @author kurix
 *
 */
public class Aster {
	public final static int COLOR_MAX = 4;
	public final static int RED = 1;
	public final static int BLUE = 2;
	public final static int GREEN = 3;
	public final static int YELLOW = 4;
		
	private Field field;
	private int color;
	private int asterClass;
	private static Random r = new Random(System.currentTimeMillis());
	private boolean deleteFlag;
	
	Aster(){
		color = r.nextInt(COLOR_MAX)+1;
		asterClass = 0;
		deleteFlag = false;		
	}
	
	public void swap(Field f,int x1,int y1,int x2,int y2){
		f.swap(x1,y1,x2,y2);
	}	
	public int getColor(){
		return color;
	}
	public int getAsterClass(){
		return asterClass;
	}
	public void setDeleteFlag(boolean b){
		deleteFlag = b;
	}
	public boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	public void delete(){
		if(deleteFlag){
			color = r.nextInt(COLOR_MAX)+1;
			asterClass = 0;
			deleteFlag = false;	
			//ここでプレイヤーに対するSP配分処理もやるんだろうか
		}
	}
}
