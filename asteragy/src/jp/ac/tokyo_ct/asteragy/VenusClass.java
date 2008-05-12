package jp.ac.tokyo_ct.asteragy;

final class VenusClass extends AsterClass {
	VenusClass(Aster a, Player p) {
		super(a, p);
	}

	VenusClass(VenusClass a) {
		super(a);
	}

	AsterClass clone() {
		return new VenusClass(this);
	}

	int getNumber() {
		return 4;
	}
}
