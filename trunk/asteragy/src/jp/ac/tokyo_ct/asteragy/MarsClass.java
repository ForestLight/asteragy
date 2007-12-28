package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class MarsClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public MarsClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 6;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = getAster().getField().asterToPoint(getAster()).x
					- (range[0].length / 2);
			pt.y = getAster().getField().asterToPoint(getAster()).y
					- (range.length / 2);

			for (int i = 0; i + pt.y < defaultRange.length; i++) {
				if (pt.y + i < 0)
					continue;
				for (int j = 0; j + pt.x < defaultRange[0].length; j++) {
					if (pt.x + j < 0)
						continue;

					if (defaultRange[i][j] == 1) {
						// レンジ内で自身かサン以外なら選択可
						if (getAster().getField().getField()[pt.y + i][pt.x + j]
								.getNumber() != 1
								&& getAster().getField().getField()[pt.y + i][pt.x
										+ j] != getAster()) {
							range[i][j] = 1;
						}
						// 自身かサンなら移動のみ可
						else {
							range[i][j] = 0;
						}
					} else {
						range[i][j] = 0;
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
			if(target1 == null)
				return true;
			target1 = null;
		}
		return false;
	}

	public String getName() {
		return "マーズ";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "フレアスター";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "アステル1個を破壊する (サンには無効)";
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
		getAster().getField().setDeleteFlag(target1);
		getAster().getField().delete(target1.x, target1.y, 0);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(6);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}
}
