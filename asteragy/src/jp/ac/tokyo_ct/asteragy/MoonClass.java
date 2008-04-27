package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

final class MoonClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	MoonClass(Aster a, Player p) {
		super(a, p);
	}

	MoonClass(MoonClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MoonClass(this);
	}

	int getNumber() {
		return 12;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
		}
		return null;
	}

	boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
		}
		return false;
	}

	boolean hasNext() {
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
		}
		return false;
	}

	boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
		}
		return false;
	}

	void executeSpecialCommand() {
		final Field f = getAster().field;
		Point me = f.asterToPoint(getAster());
		for (int i = 0; i < f.Y; i++) {
			for (int j = 0; j < f.X; j++) {
				// Ž©•ª‚ÌƒTƒ“‚ð‚Ý‚Â‚¯‚é
				final Aster a = f.field[i][j];
				if (a.getNumber() == 1
						&& a.getAsterClass().getPlayer() == getPlayer()) {
					Point pt = new Point(j, i);
					
					Effect effect = new EffectCommandMoon(f, me, pt);
					f.getCanvas().paintEffect(effect);
					f.swap(pt, me);
					f.setDeleteFlag(pt);
					f.delete(pt.x, pt.y);
					logAction();
					return;
				}
			}
		}
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(12);
		}
		return asterImage;
	}

}