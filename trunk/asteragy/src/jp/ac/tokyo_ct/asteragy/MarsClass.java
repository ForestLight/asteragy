package jp.ac.tokyo_ct.asteragy;

final class MarsClass extends AsterClass {
	MarsClass(Aster a, Player p) {
		super(a, p);
	}

	MarsClass(MarsClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MarsClass(this);
	}

	int getNumber() {
		return 6;
	}
}
