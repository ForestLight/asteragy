package jp.ac.tokyo_ct.asteragy;

final class EarthClass extends AsterClass {
	EarthClass(Aster a, Player p) {
		super(a, p);
	}

	EarthClass(EarthClass a) {
		super(a);
	}

	AsterClass clone() {
		return new EarthClass(this);
	}

	int getNumber() {
		return 5;
	}
}
