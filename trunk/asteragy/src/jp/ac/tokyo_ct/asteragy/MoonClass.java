package jp.ac.tokyo_ct.asteragy;

final class MoonClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

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
		Point me = getPoint();
		logAction();
		for (int i = 0; i < field.Y; i++) {
			for (int j = 0; j < field.X; j++) {
				// Ž©•ª‚ÌƒTƒ“‚ð‚Ý‚Â‚¯‚é
				final Aster a = field.field[i][j];
				if (a.getNumber() == 1
						&& a.getAsterClass().getPlayer() == getPlayer()) {
					Point pt = new Point(j, i);
					
					Effect effect = new EffectCommandMoon(me, pt);
					game.getCanvas().paintEffect(effect);
					field.swap(pt.x, pt.y, me.x, me.y);
					field.setDeleteFlag(pt);
					field.delete(pt.x, pt.y, game.getCanvas().disappearControl.disappearing);
					logAction();
					return;
				}
			}
		}
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}
}