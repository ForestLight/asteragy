package jp.ac.tokyo_ct.asteragy;

/**
 * @author kurix
 * 
 */
public final class Aster {
	static int COLOR_MAX = 5;

	public final static int RED = 1;

	public final static int BLUE = 2;

	public final static int GREEN = 3;

	public final static int YELLOW = 4;

	public final static int PINK = 5;

	private final Field field;

	private int color;

	private AsterClass asterClass = null;

	private PaintAsterItem paint;

	private boolean deleteFlag;

	private boolean judgeFlag;

	Aster(Field f) {
		color = Game.random.nextInt(COLOR_MAX) + 1;
		deleteFlag = false;
		judgeFlag = false;
		field = f;
		paint = new AsterPaint();
	}

	Aster clone() {
		// Aster a = new Aster(field);
		Aster a = this;
		a.color = color;
		if (asterClass != null) {
			a.asterClass = asterClass.clone();
			a.asterClass.setAster(this);
		} else
			a.asterClass = null;
		a.paint = paint;
		a.deleteFlag = deleteFlag;
		a.judgeFlag = judgeFlag;
		// a.paint = new AsterPaint();
		// a.deleteFlag = false;
		// a.judgeFlag = false;
		return a;
	}

	public void init() {
		deleteFlag = false;
		judgeFlag = false;

		paint = new AsterPaint();
		paint.setColor(color);
		paint.setClass(asterClass);
	}

	public void setNum(int i) {
		num = i;
	}

	public int getNum() {
		return num;
	}

	public int num;

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

	public void setJudgeFlag(boolean b) {
		judgeFlag = b;
	}

	public boolean getJudgeFlag() {
		return judgeFlag;
	}

	public void setColor(int c) {
		if (c != 0) {
			color = c;
		}
	}

	/**
	 * 削除フラグが立っていた場合、削除して生成しなおす
	 * 
	 */
	public void delete(int c) {
		if (deleteFlag) {
			color = Game.random.nextInt(COLOR_MAX) + 1;
			asterClass = null;
			deleteFlag = false;
			judgeFlag = false;

			paint.setClass(null);
			// 初期の生成時に実行しないようなことをしてほしい。
			// とりあえず。
			if (!field.isFieldInit())
				disappearingAster();
		}

		if (c != 0) {
			color = c;
		}
	}

	public void setAsterClass(AsterClass ac) {
		// System.out.println("Aster.setAsterClass");
		asterClass = ac;
		paint.setClass(ac);
	}

	public final int getNumber() {
		return asterClass != null ? asterClass.getNumber() : 0;
	}

	public final Field getField() {
		return field;
	}

	public Point getPoint() {
		return getField().asterToPoint(this);
	}

	synchronized public PaintAsterItem getPaint() {
		paint.setColor(color);
		return paint;
	}

	public void setPaint(PaintAsterItem paint) {
		// System.out.println("setPaint:" + this.toString());
		this.paint = paint;
	}

	private void disappearingAster() {
		// 消失エフェクト処理
		EffectAsterDisappearing disappear = new EffectAsterDisappearing(this);
		paint = disappear;
		// disappear.start();
	}

}
