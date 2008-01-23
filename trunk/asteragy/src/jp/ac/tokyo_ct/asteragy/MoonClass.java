package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

public class MoonClass extends AsterClass {

	private static int[][] defaultRange = { 
		{ 0, 1, 0 },
		{ 1, 1, 1 },
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
			return defaultRange;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			if(target1 == null)
				return true;
			target1 = null;
		}
		return false;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		final Field f = getAster().getField();
		Point pt;
		for(int i = 0; i < f.getY(); i++){
			for(int j = 0; j < f.getX(); j++){
				// 自分のサンをみつける
				if(f.getField()[i][j].getNumber() == 1
						&& f.getField()[i][j].getAsterClass().getPlayer() == getPlayer()){
					f.swap(pt = new Point(j, i), target1);
					f.setDeleteFlag(pt);
					f.delete(pt.x, pt.y, 0);
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