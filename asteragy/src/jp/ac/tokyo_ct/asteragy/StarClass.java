package jp.ac.tokyo_ct.asteragy;

final class StarClass extends AsterClass {
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
}
