package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class SaturnClass extends AsterClass {
	private static int[][] defaultRange = { { 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1 } };

	private static Image asterImage;

	public SaturnClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
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
			break;
		}
		return false;
	}

	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "サターン";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ローテーション";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "検討中";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 9;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		// 左回り
		int i, j;
		Point pt = new Point();
		pt.x = getAster().getField().asterToPoint(getAster()).x
				- (defaultRange[0].length / 2);
		pt.y = getAster().getField().asterToPoint(getAster()).y
				- (defaultRange.length / 2);
		Aster[] queue = new Aster[17];

		for (i = 0, j = 0; j < 16; j++) {
			// 外周レンジのアステルを右回りにキューに入れていく
			if (pt.x >= 0 && pt.x < getAster().getField().getX() && pt.y >= 0
					&& pt.y < getAster().getField().getY()) {
				queue[i] = getAster().getField().getField()[pt.y][pt.x];
				i++;
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;

		}
		// pt.x =
		// getAster().getField().asterToPoint(getAster()).x-(defaultRange[0].length/2);
		// pt.y =
		// getAster().getField().asterToPoint(getAster()).y-(defaultRange.length/2);

		for (i = 1, j = 0; ; j++) {
			// キューの1番目のアステルから右回りに戻していく
			if (pt.x >= 0 && pt.x < getAster().getField().getX() && pt.y >= 0
					&& pt.y < getAster().getField().getY()) {
				getAster().getField().getField()[pt.y][pt.x] = queue[i];
				i++;
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;

			// キューが空になったら0番目のアステルを戻してループを抜ける
			if (queue[i] == null) {
				getAster().getField().getField()[pt.y][pt.x] = queue[0];
				break;
			}
		}
		/* 左回りか右回りをプレイヤーに選択させるのはどこになるのか */
	}

	public int getActionNum() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public Image getImage() {
		// TODO 自動生成されたメソッド・スタブ
		if (asterImage == null) {
			asterImage = loadImage(8);
		}
		return asterImage;
	}

}
