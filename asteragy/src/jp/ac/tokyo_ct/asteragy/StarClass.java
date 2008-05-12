package jp.ac.tokyo_ct.asteragy;

final class StarClass extends AsterClass {
	static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
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
}
