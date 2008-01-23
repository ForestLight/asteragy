package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Image;

public class SunClass extends AsterClass {
	private static int[][] defaultRange ={
		{0,0,1,0,0},
		{0,1,1,1,0},
		{1,1,1,1,1},
		{0,1,1,1,0},
		{0,0,1,0,0}
	};
	
	private static Image asterImage;
	private int asterClassSelect;

	public SunClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
	
	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getAster().getField().asterToPoint(
					getAster());
			final Field field = getAster().getField();
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i< defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.getY())
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x+j >= field.getX())
						continue;

					// レンジ内であり
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// その位置のアステルにクラスがあり
						final Aster f = field.getField()[pt.y
								+ i][pt.x + j];
						if (f.getAsterClass() != null) {
							// そのクラスの所持者が相手である場合選択不可能
							if (f.getAsterClass().getPlayer() != getPlayer()) {
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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return swapMoveAstern();
		}
		return false;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		final Field field = getAster().getField();
		final Aster a = field.getAster(target1);
		AsterClass ac = new StarClass(a,getPlayer());
		
		switch(asterClassSelect){
		case 0:
			ac = new StarClass(a,getPlayer());
			break;
		case 1:
			ac = new MercuryClass(a,getPlayer());
			break;
		case 2:
			ac = new VenusClass(a,getPlayer());
			break;
		case 3:
			ac = new EarthClass(a,getPlayer());
			break;
		case 4:
			ac = new MarsClass(a,getPlayer());
			break;
		case 5:
			ac = new JupiterClass(a,getPlayer());
			break;
		case 6:
			ac = new SaturnClass(a,getPlayer());
			break;
		case 7:
			ac = new UranusClass(a,getPlayer());
			break;
		case 8:
			ac = new NeptuneClass(a,getPlayer());
			break;
		case 9:
			ac = new PlutoClass(a,getPlayer());
			break;
	
		}
		//選択したクラスのユニットを行動不可能状態で召還
		a.setAsterClass(ac);
		a.getAsterClass().setActionCount(0);
		getPlayer().addSP(-AsterClassData.classCost[asterClassSelect+1]);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(1);
		}
		return asterImage;
	}

}
