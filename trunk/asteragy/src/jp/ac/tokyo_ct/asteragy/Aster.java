package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

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

	private boolean deleteFlag = false;

	private boolean judgeFlag = false;

	private int drawHeight = CanvasControl.measure - 1;

	private int drawWidth = CanvasControl.measure - 1;

	Aster(Field f) {
		color = Game.random.nextInt(COLOR_MAX) + 1;
		field = f;
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
		a.deleteFlag = deleteFlag;
		a.judgeFlag = judgeFlag;
		// a.deleteFlag = false;
		// a.judgeFlag = false;
		return a;
	}

	public void init() {
		deleteFlag = false;
		judgeFlag = false;
	}

	public void setNum(int i) {
		num = i;
	}

	public int getNum() {
		return num;
	}

	public int num;

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
		}

		if (c != 0) {
			color = c;
		}
	}

	public void setAsterClass(AsterClass ac) {
		asterClass = ac;
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

	void setSize(int width, int height) {
		drawWidth = width;
		drawHeight = height;
	}

	void resetSize() {
		setSize(CanvasControl.measure - 1, CanvasControl.measure - 1);
	}

	private Image getImage() {
		return asterClass != null ? asterClass.getImage() : asterImage;
	}

	static final Image asterImage = Game.loadImage("aster_0");

	public void paint(Graphics g) {
		final int m = CanvasControl.measure - 1;

		// プレイヤー2のユニットは反転
		if (asterClass != null && asterClass.getPlayer() == Main.game.player[1]) {
			g.setFlipMode(Graphics.FLIP_VERTICAL);
		} else {
			g.setFlipMode(Graphics.FLIP_NONE);
		}

		g.drawScaledImage(getImage(), 1, 1, drawWidth, drawHeight, m
				* (color - 1), 0, m, m);
		// 行動済みユニットを識別
		if (asterClass != null && asterClass.getActionCount() == 0) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 100));
			g.fillRect(1, 1, drawWidth, drawHeight);
		}
	}

}
