package jp.ac.tokyo_ct.asteragy;

final class SaturnClass extends AsterClass {
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
}
