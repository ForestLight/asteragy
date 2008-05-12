package jp.ac.tokyo_ct.asteragy;

final class SunClass extends AsterClass {
	int asterClassSelect;

	SunClass(SunClass a) {
		super(a);
		//asterClassSelect = a.asterClassSelect;
	}

	AsterClass clone() {
		return new SunClass(this);
	}

	SunClass(Aster a, Player p) {
		super(a, p);
	}

	int getNumber() {
		return 1;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(sunRange);
		case 1:
			int[][] range = new int[sunRange.length][sunRange[0].length];
			final Point thisPoint = getPoint();
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i < sunRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < sunRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (sunRange[i][j] == 1) {
						range[i][j] = 1;
						// その位置のアステルにクラスがあり
						final Aster a = field.at(pt.y + i, pt.x + j);
						final AsterClass c = a.getAsterClass();
						if (c != null) {
							// そのクラスの所持者が相手である場合選択不可能
							if (c.getPlayer() != getPlayer()) {
								range[i][j] = 0;
							}
							// サンである場合選択不可能
							if (c.getNumber() == 1) {
								range[i][j] = 0;
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
