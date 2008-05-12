package jp.ac.tokyo_ct.asteragy;

final class UranusClass extends AsterClass {
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
			return swapGetRange(uranusRange);
		case 1:
			int[][] range = new int[uranusRange.length][uranusRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Aster[][] f = field.field;
			Point pt = getPoint().clone();
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;

			// 1個目
			if (target1 == null) {
				for (int i = 0; i < uranusRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < uranusRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (uranusRange[i][j] == 1) {
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
				for (int i = 0; i < uranusRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < uranusRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (uranusRange[i][j] == 1) {
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
}
