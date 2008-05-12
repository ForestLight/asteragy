package jp.ac.tokyo_ct.asteragy;

final class UranusClass extends AsterClass {
	UranusClass(Aster a, Player p) {
		super(a, p);
	}

	UranusClass(UranusClass a) {
		super(a);
	}

	AsterClass clone() {
		return new UranusClass(this);
	}

	int getNumber() {
		return 9;
	}
}
