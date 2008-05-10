package jp.ac.tokyo_ct.asteragy;

final class UranusClass extends AsterClass {
	static int[][] defaultRange = { { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 1, 0, 1, 1, 1, 0, 1 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	UranusClass(Aster a, Player p) {
		super(a, p);
	}

	UranusClass(UranusClass a) {
		super(a);
	}

	AsterClass clone() {
		return new UranusClass(this);
	}

	int getNumber() {
		return 9;
	}

	int[][] getRange() {
		final Aster a = getAster();
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Aster[][] f = field.field;
			Point pt = getPoint().clone();
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;

			// 1個目
			if (target1 == null) {
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (defaultRange[i][j] == 1) {
							// レンジ内で自身以外なら選択可
							if (f[pt.y + i][pt.x + j] != a) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}
			// 2個目
			else {
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (defaultRange[i][j] == 1) {
							// レンジ内で自身と1個目の場所以外なら選択可
							if (f[pt.y + i][pt.x + j] != a
									&& field.asterToPoint(a) != target1) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}
			return range;
		}
		return null;
	}

	boolean setPointAndNext(Point pt) {
		// スワップの場合もコマンドの場合も同じ
		return swapSetPointAndNext(pt);
	}

	boolean hasNext() {
		return swapHasNext();
	}

	boolean moveAstern() {
		return swapMoveAstern();
	}

	void executeSpecialCommand() {
		field.getCanvas().paintEffect(new EffectCommandUranus(target1, target2));
		logAction(target1, target2);

		field.swap(target1.x, target1.y, target2.x, target2.y);
	}
}
