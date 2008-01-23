package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EarthClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 1, 1, 1 },
			{ 1, 1, 1 },
			{ 1, 1, 1 } };

	private static Image asterImage;

	public EarthClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 5;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			// SunClassから拝借
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getAster().getField().asterToPoint(
					getAster());
			final Field field = getAster().getField();
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i< defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.getY())
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x+j >= field.getX())
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// その位置のアステルにクラスがあれば選択不能
						final Aster f = field.getField()[pt.y
								+ i][pt.x + j];
						if (f.getAsterClass() != null) {
							range[i][j] = 0;
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
			if(target1 == null)
				return true;
			target1 = null;
		}
		return false;

	}

	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "アース";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "サモンムーン";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "レンジ内にムーンを呼び出す";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 6;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 6;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		/* イージス
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for (int i = 0; i < defaultRange.length; i++) {
			for (int j = 0; j < defaultRange[0].length; j++) {
				// レンジ内であり
				if (defaultRange[i][j] == 1) {
					pt.x = me.x - defaultRange.length + j;
					pt.y = me.y - defaultRange[0].length + i;

					// フィールドの外にはみ出してたら処理しない
					if (pt.x < 0
							|| pt.x >= getAster().getField().getField()[0].length)
						continue;
					if (pt.y < 0
							|| pt.y >= getAster().getField().getField().length)
						continue;

					// クラス持ちであり
					if (getAster().getField().getAster(pt).getAsterClass() != null) {
						// このユニットと同一のプレイヤーが所持しているのなら
						if (getAster().getField().getAster(pt).getAsterClass()
								.getPlayer() == this.getPlayer()) {
							// 対象不可フラグを建てる
							getAster().getField().getAster(pt).getAsterClass()
									.setProtectedFlag(true);
						}
					}
				}
			}
		}
		*/
		final Aster a = getAster().getField().getAster(target1);
		AsterClass ac = new MoonClass(a,getPlayer());
		a.setAsterClass(ac);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(5);
		}
		return asterImage;
	}
}
