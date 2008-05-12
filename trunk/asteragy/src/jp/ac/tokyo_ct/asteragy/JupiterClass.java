package jp.ac.tokyo_ct.asteragy;

final class JupiterClass extends AsterClass {
	JupiterClass(Aster a, Player p) {
		super(a, p);
	}

	JupiterClass(JupiterClass a) {
		super(a);
	}

	AsterClass clone() {
		return new JupiterClass(this);
	}
	
	int getNumber() {
		return 7;
	}
}
