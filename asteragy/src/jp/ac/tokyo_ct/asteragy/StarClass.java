package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class StarClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	StarClass(Aster a, Player p) {
		super(a, p);
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
		getAster().getField().swap(target1, target2);
		logAction(target1, target2);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(2);
		}
		return asterImage;
	}
}
