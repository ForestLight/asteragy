package jp.ac.tokyo_ct.asteragy;

final class MercuryClass extends AsterClass {

	MercuryClass(Aster a, Player p) {
		super(a, p);
	}

	MercuryClass(MercuryClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MercuryClass(this);
	}

	int getNumber() {
		return 3;
	}
}
