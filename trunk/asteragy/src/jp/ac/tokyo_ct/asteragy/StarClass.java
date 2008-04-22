package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class StarClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	StarClass(Aster a, Player p) {
		super(a, p);
	}

	public StarClass(StarClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new StarClass(this);
	}

	public int getNumber() {
		return 2;
	}

	public int[][] getRange() {
		return swapGetRange(defaultRange);
	}

	public boolean setPointAndNext(Point pt) {
		return swapSetPointAndNext(pt);
	}

	public boolean hasNext() {
		return swapHasNext();
	}

	public boolean moveAstern() {
		return swapMoveAstern();
	}

	public void executeSpecialCommand() {
		final Field f = getAster().getField();
		f.getCanvas().paintEffect(new EffectCommandStar(f, this, target1, target2));

		f.swap(target1, target2);
		logAction(target1, target2);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(2);
		}
		return asterImage;
	}
}
