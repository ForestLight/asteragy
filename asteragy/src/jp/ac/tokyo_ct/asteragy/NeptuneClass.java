package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class NeptuneClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }};

	private static Image asterImage;

	public NeptuneClass(Aster a, Player p) {
		super(a, p);
	}

	public NeptuneClass(NeptuneClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new NeptuneClass(this);
	}

	public int getNumber() {
		return 10;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return defaultRange;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	public boolean hasNext() {

		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			if (target1 == null)
				return true;
			else
				return false;
		}
		return false;
	}

	public boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return true;
		}
		return false;
	}

	public String getCommandName() {
		return "スターライトストリーム";
	}

	public void executeSpecialCommand() {
		// ターゲットと自分をswap
		final Field field = getAster().getField();

		Effect effect = new EffectCommandNeptune(field, target1);
		getAster().getField().getScreen().paintEffect(effect);

		field.swap(target1, field.asterToPoint(getAster()));
		logAction(target1);

	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(10);
		}
		return asterImage;
	}

}
