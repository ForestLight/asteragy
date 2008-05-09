package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class EarthClass extends AsterClass {
	private static int[][] defaultRange = { { 1, 1, 1 }, { 1, 1, 1 },
			{ 1, 1, 1 } };

	private static Image asterImage;

	EarthClass(Aster a, Player p) {
		super(a, p);
	}

	EarthClass(EarthClass a) {
		super(a);
	}

	AsterClass clone() {
		return new EarthClass(this);
	}

	int getNumber() {
		return 5;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			// SunClassから拝借
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getPoint();
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));

			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// その位置のアステルにクラスがあれば選択不能
						final Aster f = field.at(pt.y + i, pt.x + j);
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
		Effect effect = new EffectCommandEarth(target1);
		field.getCanvas().paintEffect(effect);
		logAction(target1);
		final Aster a = field.at(target1);
		new MoonClass(a, getPlayer());
		a.getAsterClass().setActionCount(0);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(5);
		}
		return asterImage;
	}
}
