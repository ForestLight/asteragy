package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class VenusClass extends AsterClass {
	private static int[][] defaultRange = {
			{ 0, 0, 1, 0, 0},
			{ 0, 1, 1, 1, 0}, 
			{ 0, 1, 1, 1, 0}, 
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0}
			};
	private static int[][] defaultRangeP2 = {
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 1, 1, 1, 0},
		{0, 1, 1, 1, 0},
		{0, 0, 1, 0, 0}		
	};

	private static Image asterImage;

	public VenusClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 4;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			if(getPlayer() == getPlayer().game.getPlayer2()){
				return swapGetRange(defaultRangeP2);
			}else{
			return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getAster().getField().asterToPoint(
					getAster());
			final Field field = getAster().getField();
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);
			
			if(getPlayer() == getPlayer().game.getPlayer2()){
				for (int i = 0; i< defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= field.getY())
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x+j >= field.getX())
							continue;

						// レンジ内であり
						if (defaultRangeP2[i][j] == 1) {
							// その位置のアステルにクラスがあり
							final Aster f = getAster().getField().getField()[pt.y
									+ i][pt.x + j];
							if (f.getAsterClass() != null) {
								// そのクラスの所持者が相手であり
								if (f.getAsterClass().getPlayer() != getPlayer()) {
									// サンでなければ対象に選択可能
									if (f.getNumber() != 1)
										range[i][j] = 1;
								}
							}
						}
					}
				}				
			}else{
				for (int i = 0; i< defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= field.getY())
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x+j >= field.getX())
							continue;
	
						// レンジ内であり
						if (defaultRange[i][j] == 1) {
							// その位置のアステルにクラスがあり
							final Aster f = getAster().getField().getField()[pt.y
									+ i][pt.x + j];
							if (f.getAsterClass() != null) {
								// そのクラスの所持者が相手であり
								if (f.getAsterClass().getPlayer() != getPlayer()) {
									// サンでなければ対象に選択可能
									if (f.getNumber() != 1)
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

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
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
			return true;
		}
		return false;
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "テンプテーション";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵ユニット一体を自分のユニットにする";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 7;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 7;
	}

	public String getName() {
		return "ヴィーナス";
	}

	public void executeSpecialCommand() {
		// 対象の所持者を変更
		getAster().getField().getAster(target1).getAsterClass().setPlayer(
				this.getPlayer());
		// 行動済状態に
		getAster().getField().getAster(target1).getAsterClass().setActionCount(
				0);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(4);
		}
		return asterImage;
	}
}
