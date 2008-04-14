package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class SaturnClass extends AsterClass {
	private static int[][] defaultRange = { { 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1 } };

	private static Image asterImage;

	public SaturnClass(Aster a, Player p) {
		super(a, p);
	}

	public SaturnClass(SaturnClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new SaturnClass(this);
	}

	public int getNumber() {
		return 8;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return null;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			return false;
		}
		return false;
	}

	public boolean hasNext() {
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			return false;
		}
		return false;
	}

	public boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			break;
		}
		return false;
	}

	public void executeSpecialCommand() {
		// 左回り
		//修正@2/25 右回りに
		int i, j;
		int num, flag = 0;
		final Aster a = getAster();
		final Field field = a.getField();
		final Point me = field.asterToPoint(a);
		Point pt = new Point();
		pt.x = me.x - (defaultRange[0].length / 2);
		pt.y = me.y - (defaultRange.length / 2);
		Aster[] queue = new Aster[17];

		final Aster[][] f = field.getField();
		for (i = 0, j = 0; j < 16; j++) {
			// 外周レンジのアステルを右回りにキュー（のようなもの）に入れていく
			if (field.isXInFieldBound(pt.x) && field.isYInFieldBound(pt.y)) {
				queue[i] = f[pt.y][pt.x];
				i++;
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;
		}
		
		Effect effect = new  EffectCommandSaturn(field, this, queue);
		getAster().getField().getScreen().paintEffect(effect);

		num = --i;
				
		for (i = 0, j = 0; j < 16; j++) {
			// 右回りに戻していく
			if (field.isXInFieldBound(pt.x) && field.isYInFieldBound(pt.y)) {
				if (flag == 0) {
					f[pt.y][pt.x] = queue[num];
					flag++;
				}
				else {
					f[pt.y][pt.x] = queue[i];
					i++;
				}
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;

		}

		logAction();
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(8);
		}
		return asterImage;
	}

}
