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
		final Game game = getPlayer().game;
		switch (mode) {
		case 0:
			if (getPlayer() == game.player[1]) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Aster[][] f = field.field;
			final Point thisPoint = getPoint();
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));
			final int[][] defRange = getPlayer() == game.player[1] ? defaultRangeP2
					: defaultRange;
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

	boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	boolean hasNext() {
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

	boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return true;
		}
		return false;
	}

	void executeSpecialCommand() {
		// 対象の所持者を変更
		final AsterClass ac = field.at(target1).getAsterClass();
		field.getCanvas().paintEffect(new EffectCommandVenus(target1));
		ac.setPlayer(this.getPlayer());
		logAction(target1);
		// 行動済状態に
		ac.setActionCount(0);
	}
}
