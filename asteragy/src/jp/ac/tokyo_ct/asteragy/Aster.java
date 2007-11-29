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

	private final Field field;

	private int color;

	private AsterClass asterClass = null;

	private static Random r = new Random(System.currentTimeMillis());

	private boolean deleteFlag;

	Aster(Field f) {
		color = r.nextInt(COLOR_MAX) + 1;
		deleteFlag = false;
		field = f;
	}

/*	public void swap(int x1, int y1, int x2, int y2) {
		field.swap(x1, y1, x2, y2);
	}*/

	public int getColor() {
		return color;
	}

	public AsterClass getAsterClass() {
		return asterClass;
	}

	public void setDeleteFlag(boolean b) {
		deleteFlag = b;
	}

	public boolean getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * 削除フラグが立っていた場合、削除して生成しなおす
	 * 
	 */
	public void delete(int c) {
		if (deleteFlag) {
			color = r.nextInt(COLOR_MAX) + 1;
			asterClass = null;
			deleteFlag = false;
			// ここでプレイヤーに対するSP配分処理もやるんだろうか
		}

		if (c != 0) {
			color = c;
		}
	}

	public void setAsterClass(AsterClass ac) {
		asterClass = ac;
		ac.setAster(this);
	}

	public int getNumber() {
		return asterClass != null ? asterClass.getNumber() : 0;
	}
	
	public Field getField() {
		return field;
	}
}
