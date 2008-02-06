package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

public class MoonClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 1, 0 }, { 1, 1, 1 },
			{ 0, 1, 0 } };

	private static Image asterImage;

	public MoonClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 12;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
		}
		return false;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
		}
		return false;
	}

	public boolean moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
		}
		return false;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		final Field f = getAster().getField();
		Point me = f.asterToPoint(getAster());
		for (int i = 0; i < f.getY(); i++) {
			for (int j = 0; j < f.getX(); j++) {
				// 自分のサンをみつける
				final Aster a = f.getField()[i][j];
				if (a.getNumber() == 1
						&& a.getAsterClass().getPlayer() == getPlayer()) {
					Point pt = new Point(j, i);
					f.swap(pt, me);
					f.setDeleteFlag(pt);
					f.delete(pt.x, pt.y);
					logAction();
					return;
				}
			}
		}
	}

	public Image getImage() {
		// TODO 自動生成されたメソッド・スタブ
		if (asterImage == null) {
			asterImage = loadImage(12);
		}
		return asterImage;
	}

}