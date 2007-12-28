package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class PlutoClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 0, 1, 0, 1, 0 },
			{ 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, 
			{ 0, 0, 1, 0, 0 }, 
			{ 0, 1, 0, 1, 0 } };

	private static Image asterImage;

	public PlutoClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 11;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return null;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			return false;
		}
		return false;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
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
			return false;
		}
		return false;
	}

	public String getName() {
		return "プルート";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ルインクラスト";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "レンジ内のアステル\n全てを破壊する (サンにも有効)";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 11;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
	}

	public void executeSpecialCommand() {
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for (int i = 0; i < defaultRange.length; i++) {
			for (int j = 0; j < defaultRange[0].length; j++) {
				// レンジ内であり
				if (defaultRange[i][j] == 1) {
					// 自身ではない部分を破壊
					if (i != defaultRange.length / 2
							&& j != defaultRange[0].length / 2) {
						pt.x = me.x - defaultRange.length + j;
						pt.y = me.y - defaultRange[0].length + i;

						// フィールドの外にはみ出してたら処理しない
						if (pt.x < 0
								|| pt.x >= getAster().getField().getField()[0].length)
							continue;
						if (pt.y < 0
								|| pt.y >= getAster().getField().getField().length)
							continue;

						// ターゲットを破壊
						getAster().getField().setDeleteFlag(pt);
						getAster().getField().delete(pt.x, pt.y, 0);
					}
				}
			}
		}
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(11);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}

}