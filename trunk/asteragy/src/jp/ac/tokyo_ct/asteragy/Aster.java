package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

/**
 * @author kurix
 * 
 */
final class Aster {
	static int COLOR_MAX = 5;

	final static int RED = 1;

	final static int BLUE = 2;

	final static int GREEN = 3;

	final static int YELLOW = 4;

	final static int PINK = 5;

	final Field field;

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

	Aster(Field f, int c) {
		color = c;
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

	void init() {
		deleteFlag = false;
		judgeFlag = false;
	}

	void setNum(int i) {
		num = i;
	}

	int getNum() {
		return num;
	}

	private int num;

	int getColor() {
		return color;
	}

	AsterClass getAsterClass() {
		return asterClass;
	}

	void setDeleteFlag(boolean b) {
		deleteFlag = b;
	}

	boolean getDeleteFlag() {
		return deleteFlag;
	}

	void setJudgeFlag(boolean b) {
		judgeFlag = b;
	}

	boolean getJudgeFlag() {
		return judgeFlag;
	}

	void setColor(int c) {
		if (c != 0) {
			color = c;
		}
	}

	/**
	 * 削除フラグが立っていた場合、削除して生成しなおす
	 * 
	 */
	void delete(int c) {
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

	void setAsterClass(AsterClass ac) {
		asterClass = ac;
	}

	final int getNumber() {
		return asterClass != null ? asterClass.getNumber() : 0;
	}

	Point getPoint() {
		return field.asterToPoint(this);
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

	void paint(Graphics g) {
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
