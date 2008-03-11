package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

public class SunClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 0 } };

	private static Image asterImage;

	private int asterClassSelect;

	public SunClass(SunClass a) {
		super(a);
		//asterClassSelect = a.asterClassSelect;
	}

	public AsterClass clone() {
		return new SunClass(this);
	}

	public SunClass(Aster a, Player p) {
		super(a, p);
	}

	public int getNumber() {
		return 1;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Field field = getAster().getField();
			final Point thisPoint = field.asterToPoint(getAster());
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
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

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			if (target1 == null) {
				target1 = pt;
			} else {
				asterClassSelect = pt.x;
			}
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
			return swapMoveAstern();
		}
		return false;
	}

	public void executeSpecialCommand() {
		final Field field = getAster().getField();
		final Aster a = field.getAster(target1);
		AsterClass ac = new StarClass(a, getPlayer());
		logAction(new int[] {target1.x, target1.y, asterClassSelect});
		switch (asterClassSelect) {
		case 0:
			ac = new StarClass(a, getPlayer());
			break;
		case 1:
			ac = new MercuryClass(a, getPlayer());
			break;
		case 2:
			ac = new VenusClass(a, getPlayer());
			break;
		case 3:
			ac = new EarthClass(a, getPlayer());
			break;
		case 4:
			ac = new MarsClass(a, getPlayer());
			break;
		case 5:
			ac = new JupiterClass(a, getPlayer());
			break;
		case 6:
			ac = new SaturnClass(a, getPlayer());
			break;
		case 7:
			ac = new UranusClass(a, getPlayer());
			break;
		case 8:
			ac = new NeptuneClass(a, getPlayer());
			break;
		case 9:
			ac = new PlutoClass(a, getPlayer());
			break;

		}
		// 選択したクラスのユニットを行動不可能状態で召還
		ac.setActionCount(0);
		//getPlayer().addAP(-AsterClass.classCost[asterClassSelect + 1]);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(1);
		}
		return asterImage;
	}

}
