package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class VenusClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 0 } };

	private static Image asterImage;

	public VenusClass(Aster a, Player p) {
		super(a, p);
	}

	public VenusClass(VenusClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new VenusClass(this);
	}

	public int getNumber() {
		return 4;
	}

	public int[][] getRange() {
		final Game game = getPlayer().game;
		switch (mode) {
		case 0:
			if (getPlayer() == game.getPlayer2()) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Field field = getAster().getField();
			final Point thisPoint = field.asterToPoint(getAster());
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			if (getPlayer() == game.getPlayer2()) {
				for (int i = 0; i < defaultRange.length; i++) {
					if (!field.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (!field.isXInFieldBound(pt.x + j))
							continue;

						// レンジ内であり
						if (defaultRangeP2[i][j] == 1) {
							// その位置のアステルにクラスがあり
							final Aster a = field.getField()[pt.y + i][pt.x + j];
							final AsterClass asterClass = a.getAsterClass();
							if (asterClass != null) {
								// そのクラスの所持者が相手であり
								if (asterClass.getPlayer() != getPlayer()) {
									// サンでなければ対象に選択可能
									if (a.getNumber() != 1)
										range[i][j] = 1;
								}
							}
						}
					}
				}
			} else {
				for (int i = 0; i < defaultRange.length; i++) {
					if (!field.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (!field.isXInFieldBound(pt.x + j))
							continue;

						// レンジ内であり
						if (defaultRange[i][j] == 1) {
							// その位置のアステルにクラスがあり
							final Aster f = field.getField()[pt.y + i][pt.x + j];
							final AsterClass asterClass = f.getAsterClass();
							if (asterClass != null) {
								// そのクラスの所持者が相手であり
								if (asterClass.getPlayer() != getPlayer()) {
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

	public void executeSpecialCommand() {
		// 対象の所持者を変更
		final AsterClass asterClass = getAster().getField().getAster(target1)
				.getAsterClass();

		Effect effect = new EffectCommandVenus(getAster().getField(), target1);
		effect.start();

		asterClass.setPlayer(this.getPlayer());
		// 行動済状態に
		asterClass.setActionCount(0);
		logAction(target1);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(4);
		}
		return asterImage;
	}
}
