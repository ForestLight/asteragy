package jp.ac.tokyo_ct.asteragy;

final class MoonClass extends AsterClass {

	static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
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
}