package jp.ac.tokyo_ct.asteragy;

import java.util.Random;
import com.nttdocomo.ui.*;

/**
 * @author kurix
 * 
 */
public class Aster {
	public final static int COLOR_MAX = 5;

	public final static int RED = 1;

	public final static int BLUE = 2;

	public final static int GREEN = 3;

	public final static int YELLOW = 4;
	
	public final static int PINK = 5;

	private final Field field;

	private int color;

	private AsterClass asterClass = null;
	
	private PaintAsterItem paint;

	private static Random r = new Random(System.currentTimeMillis());

	private boolean deleteFlag;

	Aster(Field f) {
		color = r.nextInt(COLOR_MAX) + 1;
		deleteFlag = false;
		field = f;
		paint = new AsterPaint();
	}

	/*
	 * public void swap(int x1, int y1, int x2, int y2) { field.swap(x1, y1, x2,
	 * y2); }
	 */

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
	 * �폜�t���O�������Ă����ꍇ�A�폜���Đ������Ȃ���
	 * 
	 */
	public void delete(int c) {
		if (deleteFlag) {
			color = r.nextInt(COLOR_MAX) + 1;
			asterClass = null;
			deleteFlag = false;
			// �����Ńv���C���[�ɑ΂���SP�z�����������񂾂낤��
		}

		if (c != 0) {
			color = c;
		}
	}

	public void setAsterClass(AsterClass ac) {
		System.out.println("Aster.setAsterClass");
		asterClass = ac;
		ac.setAster(this);
		paint.setClass(ac);
	}

	public int getNumber() {
		return asterClass != null ? asterClass.getNumber() : 0;
	}

	public Field getField() {
		return field;
	}

	Point getPoint() {
		return getField().asterToPoint(this);
	}
	
	public PaintItem getPaint(){
		paint.setColor(color);
		return paint;
	}
	
	public void setPaint(PaintAsterItem paint){
		this.paint = paint;
	}

}
