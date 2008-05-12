package jp.ac.tokyo_ct.asteragy;

final class SunClass extends AsterClass {
	int asterClassSelect;

	SunClass(SunClass a) {
		super(a);
		//asterClassSelect = a.asterClassSelect;
	}

	AsterClass clone() {
		return new SunClass(this);
	}

	SunClass(Aster a, Player p) {
		super(a, p);
	}

	int getNumber() {
		return 1;
	}
}
