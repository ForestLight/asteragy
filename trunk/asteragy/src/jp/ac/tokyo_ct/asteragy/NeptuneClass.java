package jp.ac.tokyo_ct.asteragy;

final class NeptuneClass extends AsterClass {
	NeptuneClass(Aster a, Player p) {
		super(a, p);
	}

	NeptuneClass(NeptuneClass a) {
		super(a);
	}

	AsterClass clone() {
		return new NeptuneClass(this);
	}

	int getNumber() {
		return 10;
	}
}
