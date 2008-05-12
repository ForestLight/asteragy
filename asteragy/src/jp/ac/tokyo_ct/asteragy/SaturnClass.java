package jp.ac.tokyo_ct.asteragy;

final class SaturnClass extends AsterClass {
	static final int[][] defaultRange = { { 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1 } };

	SaturnClass(Aster a, Player p) {
		super(a, p);
	}

	SaturnClass(SaturnClass a) {
		super(a);
	}

	AsterClass clone() {
		return new SaturnClass(this);
	}

	int getNumber() {
		return 8;
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
