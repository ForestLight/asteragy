package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

public class MoonClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	public MoonClass(Aster a, Player p) {
		super(a, p);
	}

	public MoonClass(MoonClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new MoonClass(this);
	}

	public int getNumber() {
		return 12;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
		}
		return false;
	}

	public boolean hasNext() {
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
		}
		return false;
	}

	public boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
		}
		return false;
	}

	public void executeSpecialCommand() {
		final Field f = getAster().getField();
		Point me = f.asterToPoint(getAster());
		for (int i = 0; i < f.getY(); i++) {
			for (int j = 0; j < f.getX(); j++) {
				// Ž©•ª‚ÌƒTƒ“‚ð‚Ý‚Â‚¯‚é
				final Aster a = f.getField()[i][j];
				if (a.getNumber() == 1
						&& a.getAsterClass().getPlayer() == getPlayer()) {
					Point pt = new Point(j, i);
					f.swap(pt, me);
					f.setDeleteFlag(pt);
					f.delete(pt.x, pt.y);
					logAction();
					return;
				}
			}
		}
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(12);
		}
		return asterImage;
	}

}