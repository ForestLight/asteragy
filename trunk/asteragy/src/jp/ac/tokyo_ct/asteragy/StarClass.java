package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class StarClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	StarClass(Aster a, Player p) {
		super(a, p);
	}

	StarClass(StarClass a) {
		super(a);
	}

	AsterClass clone() {
		return new StarClass(this);
	}

	int getNumber() {
		return 2;
	}

	int[][] getRange() {
		return swapGetRange(defaultRange);
	}

	boolean setPointAndNext(Point pt) {
		return swapSetPointAndNext(pt);
	}

	boolean hasNext() {
		return swapHasNext();
	}

	boolean moveAstern() {
		return swapMoveAstern();
	}

	void executeSpecialCommand() {
		final Field f = getAster().field;
		f.getCanvas().paintEffect(new EffectCommandStar(f, this, target1, target2));

		f.swap(target1, target2);
		logAction(target1, target2);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(2);
		}
		return asterImage;
	}
}
