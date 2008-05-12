package jp.ac.tokyo_ct.asteragy;

final class MercuryClass extends AsterClass {

	static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0 } };

	MercuryClass(Aster a, Player p) {
		super(a, p);
	}

	MercuryClass(MercuryClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MercuryClass(this);
	}

	int getNumber() {
		return 3;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point tmp = getPoint();
			Point pt = new Point(tmp.x - (range[0].length / 2), tmp.y
					- (range.length / 2));

			for (int i = 0; i < defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.Y)
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x + j >= field.X)
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
						// その位置のアステルにクラスがあり
						final AsterClass ac = field.field[pt.y + i][pt.x + j]
								.getAsterClass();
						if (ac != null) {
							// そのクラスの所持者が自分であり
							if (ac.getPlayer() == getPlayer()) {
								// 行動可能回数が0ならば選択可能
								if (ac.getActionCount() == 0) {
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
}
