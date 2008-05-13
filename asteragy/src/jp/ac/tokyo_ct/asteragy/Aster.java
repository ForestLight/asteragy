package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

/**
 * @author kurix
 * 
 */
final class Aster {
	final Field field;

	private int color;

	private AsterClass asterClass = null;

	private boolean deleteFlag = false;

	private boolean judgeFlag = false;

	int drawHeight = CanvasControl.measure - 1;

	int drawWidth = CanvasControl.measure - 1;

	Aster(Field f) {
		color = Game.rand(AsterClass.COLOR_MAX) + 1;
		field = f;
	}

	Aster(Field f, int c) {
		color = c;
		field = f;
	}

	Aster clone(Field f) {
		Aster a = new Aster(f);
		a.color = color;
		if (asterClass != null) {
			a.asterClass = asterClass.clone();
			a.asterClass.setAster(this);
		} else {
			a.asterClass = null;
		}
		// a.deleteFlag = deleteFlag;
		// a.judgeFlag = judgeFlag;
		a.deleteFlag = false;
		a.judgeFlag = false;
		return a;
	}

	final void init() {
		deleteFlag = false;
		// judgeFlag = false;
	}

	final void setNum(int i) {
		num = i;
	}

	final int getNum() {
		return num;
	}

	private int num;

	final int getColor() {
		return color;
	}

	final AsterClass getAsterClass() {
		return asterClass;
	}

	final void setDeleteFlag(boolean b) {
		deleteFlag = b;
	}

	final boolean getDeleteFlag() {
		return deleteFlag;
	}

	final void setJudgeFlag(boolean b) {
		judgeFlag = b;
	}

	final boolean getJudgeFlag() {
		return judgeFlag;
	}

	final void setColor(int c) {
		if (c != 0) {
			color = c;
		}
	}

	/**
	 * 削除フラグが立っていた場合、削除して生成しなおす
	 * 
	 */
	final void delete(int c) {
		if (deleteFlag) {
			Game.println("delete " + field.asterToPoint(this));
			color = Game.rand(AsterClass.COLOR_MAX) + 1;
			asterClass = null;
			deleteFlag = false;
			// judgeFlag = false;
		}

		if (c != 0) {
			color = c;
		}
	}

	final void setAsterClass(AsterClass ac) {
		asterClass = ac;
	}

	final int getNumber() {
		return asterClass != null ? asterClass.getNumber() : 0;
	}

	final Point getPoint() {
		return field.asterToPoint(this);
	}

	final void setSize(int width, int height) {
		drawWidth = width;
		drawHeight = height;
	}

	final void resetSize() {
		setSize(CanvasControl.measure - 1, CanvasControl.measure - 1);
	}

	private final Image getImage() {
		return AsterClass.getImage(asterClass);
	}

	final void paint(Graphics g) {
		final int m = CanvasControl.measure - 1;

		// プレイヤー2のユニットは反転
		if (asterClass != null
				&& asterClass.getPlayer() == field.game.player[1]) {
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
