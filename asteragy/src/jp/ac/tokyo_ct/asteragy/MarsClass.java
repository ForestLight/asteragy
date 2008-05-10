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
		switch (mode) {
		case 0:
			if (getPlayer() == getPlayer().game.player[1]) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			final int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			Point pt = field.asterToPoint(a);
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;
			final int[][] defRange =
				getPlayer() == game.player[1]
					? defaultRangeP2
					: defaultRange;
			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					// if (pt.x + j < 0 || pt.y + i >= f.getX())
					if (!field.isXInFieldBound(pt.x + j))
						continue;
					if (defaultRangeP2[i][j] == 1) {
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
			if (target1 == null)
				return true;
			target1 = null;
		}
		return false;
	}

	void executeSpecialCommand() {
		field.getCanvas().paintEffect(new EffectCommandMars(field, this, target1));
		logAction(target1);
		field.setDeleteFlag(target1);
		field.delete(target1.x, target1.y, game.getCanvas().disappearControl.disappearing);
	}
}
