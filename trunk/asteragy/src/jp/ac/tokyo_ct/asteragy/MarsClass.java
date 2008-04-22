package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class MarsClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	private static Image asterImage;

	public MarsClass(Aster a, Player p) {
		super(a, p);
	}

	public MarsClass(MarsClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new MarsClass(this);
	}

	public int getNumber() {
		return 6;
	}

	public int[][] getRange() {
		final Aster a = getAster();
		switch (mode) {
		case 0:
			if (getPlayer() == getPlayer().game.player[1]) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			Field f = a.getField();
			Point pt = new Point();
			pt.x = f.asterToPoint(a).x - (range[0].length / 2);
			pt.y = f.asterToPoint(a).y - (range.length / 2);

			if (getPlayer() == getPlayer().game.player[1]) {
				for (int i = 0; i < defaultRange.length; i++) {
					if (!f.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						// if (pt.x + j < 0 || pt.y + i >= f.getX())
						if (!f.isXInFieldBound(pt.x + j))
							continue;

						if (defaultRangeP2[i][j] == 1) {
							// レンジ内で自身かサン以外なら選択可
							final Aster aster2 = f.at(pt.y + i, pt.x + j);
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
			} else {
				for (int i = 0; i < defaultRange.length; i++) {
					if (!f.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						// if (pt.x + j < 0 || pt.y + i >= f.getX())
						if (!f.isXInFieldBound(pt.x + j))
							continue;

						if (defaultRange[i][j] == 1) {
							// レンジ内で自身かサン以外なら選択可
							final Aster aster2 = f.field[pt.y + i][pt.x + j];
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
			}
			return range;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	public boolean hasNext() {
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

	public boolean moveAstern() {
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

	public void executeSpecialCommand() {
		final Field f = getAster().getField();
		f.getCanvas().paintEffect(new EffectCommandMars(f, this, target1));
		f.setDeleteFlag(target1);
		f.delete(target1.x, target1.y);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(6);
		}
		return asterImage;
	}
}
