package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class MercuryClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public MercuryClass(Aster a, Player p) {
		super(a, p);
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 3;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point tmp = getAster().getPoint();
			Point pt = new Point();
			pt.x = tmp.x - (range[0].length / 2);
			pt.y = tmp.y - (range.length / 2);

			for (int i = 0; i + pt.y < defaultRange.length; i++) {
				if (pt.y + i < 0
						|| pt.y + i >= getAster().getField().getField().length)
					continue;
				for (int j = 0; j + pt.x < defaultRange[0].length; j++) {
					if (pt.x + j < 0
							|| pt.x + j >= getAster().getField().getField()[0].length)
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
						// その位置のアステルにクラスがあり
						if (getAster().getField().getField()[pt.y + i][pt.x + j]
								.getAsterClass() != null) {
							// そのクラスの所持者が自分であり
							if (getAster().getField().getField()[pt.y + i][pt.x
									+ j].getAsterClass().getPlayer() == getPlayer()) {
								// 行動可能回数が0ならば選択可能
								if (getAster().getField().getField()[pt.y + i][pt.x
										+ j].getAsterClass().getActionCount() == 0) {
									range[i][j] = 1;
								}
							}
						}
					}
				}
			}
			return range;
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
			return true;
		}
		return false;
	}

	public String getName() {
		return "マーキュリー";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "クイックタイム";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "ほりゅう";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 4;
	}

	public void executeSpecialCommand() {
		// 対象の行動可能回数を1回増やす
		getAster().getField().getAster(target1).getAsterClass()
				.incActionCount();
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(3);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}

}
