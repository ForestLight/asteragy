package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class StarClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 0, 0 } };

	private static Image asterImage;

	StarClass(Aster a, Player p) {
		super(a, p);
	}

	public int getNumber() {
		// TODO ꂽ\bhEX^u
		return 2;
	}

	public int[][] getRange() {
		// TODO ꂽ\bhEX^u
		return swapGetRange(defaultRange, target1);
	}

	public boolean setPointAndNext(Point pt) {
		// TODO ꂽ\bhEX^u
		return swapSetPointAndNext(pt, target1, target2);
	}

	public boolean hasNext() {
		// TODO ꂽ\bhEX^u
		return swapHasNext(target1, target2);
	}

	public void moveAstern() {
		// TODO ꂽ\bhEX^u
		swapMoveAstern(target1, target2);
	}

	public String getCommandName() {
		// TODO ꂽ\bhEX^u
		return null;
	}

	public String getName() {
		return "X^[";
	}

	public String getExplain() {
		// TODO ꂽ\bhEX^u
		return null;
	}

	public int getCost() {
		// TODO ꂽ\bhEX^u
		return 2;
	}

	public int getCommandCost() {
		// TODO ꂽ\bhEX^u
		return 0;
	}

	public void executeSpecialCommand() {

	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(2);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}

}
