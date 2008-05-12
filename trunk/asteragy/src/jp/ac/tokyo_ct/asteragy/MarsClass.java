package jp.ac.tokyo_ct.asteragy;

final class MarsClass extends AsterClass {
	static int[][] defaultRange = { { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	MarsClass(Aster a, Player p) {
		super(a, p);
	}

	MarsClass(MarsClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MarsClass(this);
	}

	int getNumber() {
		return 6;
	}

	int[][] getRange() {
		final Aster a = getAster();
		final int[][] defRange =
			getPlayer() == game.player[1]
				? defaultRangeP2
				: defaultRange;
		switch (mode) {
		case 0:
			return swapGetRange(defRange);
		case 1:
			final int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			Point pt = field.asterToPoint(a);
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;
			for (int i = 0; i < defRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;
					if (defRange[i][j] == 1) {
						// レンジ内で自身かサン以外なら選択可
						final Aster aster2 = field.at(pt.y + i, pt.x + j);
						if (aster2.getNumber() != 1 && aster2 != a) {
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
}
