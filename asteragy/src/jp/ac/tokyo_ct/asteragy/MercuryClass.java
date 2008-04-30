package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class MercuryClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

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
			final Point tmp = getAster().getPoint();
			final Field field = getAster().field;
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
		final Field f = getAster().field;
		f.getCanvas().paintEffect(new EffectCommandMercury(f, target1));
		logAction(target1);
		// 対象の行動可能回数を1回増やす
		f.at(target1).getAsterClass().incActionCount();
		logAction(target1);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(3);
		}
		return asterImage;
	}
}
