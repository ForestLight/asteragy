package jp.ac.tokyo_ct.asteragy;

final class PlutoClass extends AsterClass {
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
}
