package jp.ac.tokyo_ct.asteragy;

final class StarClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

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
		field.getCanvas().paintEffect(new EffectCommandStar(field, this, target1, target2));
		logAction(target1, target2);

		field.swap(target1.x, target1.y, target2.x, target2.y);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}
}
