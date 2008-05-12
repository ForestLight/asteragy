package jp.ac.tokyo_ct.asteragy;

final class PlutoClass extends AsterClass {
	static final int[][] defaultRange = { { 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 1 } };

	PlutoClass(Aster a, Player p) {
		super(a, p);
	}

	PlutoClass(PlutoClass a) {
		super(a);
	}

	AsterClass clone() {
		return new PlutoClass(this);
	}

	int getNumber() {
		return 11;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return defaultRange;
		}
		return null;
	}
}
