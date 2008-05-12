package jp.ac.tokyo_ct.asteragy;

final class MoonClass extends AsterClass {
	MoonClass(Aster a, Player p) {
		super(a, p);
	}

	MoonClass(MoonClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MoonClass(this);
	}

	int getNumber() {
		return 12;
	}
}