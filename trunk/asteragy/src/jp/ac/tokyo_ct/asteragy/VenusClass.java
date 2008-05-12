package jp.ac.tokyo_ct.asteragy;

final class VenusClass extends AsterClass {
	static int[][] defaultRange = { { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 },
			{ 0, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 0 } };

	VenusClass(Aster a, Player p) {
		super(a, p);
	}

	VenusClass(VenusClass a) {
		super(a);
	}

	AsterClass clone() {
		return new VenusClass(this);
	}

	int getNumber() {
		return 4;
	}

	int[][] getRange() {
		final int[][] defRange = getPlayer() == game.player[1] ? defaultRangeP2
				: defaultRange;
		switch (mode) {
		case 0:
			return swapGetRange(defRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Aster[][] f = field.field;
			final Point thisPoint = getPoint();
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));
			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;
					// レンジ内であり
					if (defRange[i][j] == 1) {
						final Aster a = f[pt.y + i][pt.x + j];
						final AsterClass asterClass = a.getAsterClass();
						// その位置のアステルにクラスがあり
						// そのクラスの所持者が相手であり
						// サンでなければ対象に選択可能
						if (asterClass != null
								&& asterClass.getPlayer() != getPlayer()
								&& a.getNumber() != 1) {
							range[i][j] = 1;
						}
					}
				}
				return range;
			}
		}
		return null;
	}
}
