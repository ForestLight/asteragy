package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class JupiterClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public JupiterClass(Aster a, Player p) {
		super(a, p);
	}

	public JupiterClass(JupiterClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new JupiterClass(this);
	}
	
	public int getNumber() {
		return 7;
	}

	public int[][] getRange() {
		final Aster a = getAster();
		final Field field = a.getField();
		final Point asterPoint = field.asterToPoint(a);
		switch (mode) {
		case 0:
			if (getPlayer() == getPlayer().game.getPlayer2()) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = asterPoint.x - (range[0].length / 2);
			pt.y = asterPoint.y - (range.length / 2);
			
			// プレイヤー2の場合レンジP2を使用
			final int[][] def = getPlayer() == getPlayer().game.getPlayer1()
				? defaultRange
				: defaultRangeP2;

			// 対象選択1個目(敵ユニットのみ)
			if (target1 == null) {
				for (int i = 0; i < def.length; i++) {
					if (!field.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < def[0].length; j++) {
						if (!field.isXInFieldBound(pt.x + j))
							continue;

						// レンジ内であり
						if (def[i][j] == 1) {
							// その位置のアステルにクラスがあり
							final AsterClass asterClass =
								field.at(pt.y + i, pt.x + j).getAsterClass();
							if (asterClass != null) {
								// そのクラスの所持者が自分ではなければ選択可能
								if (asterClass.getPlayer() != getPlayer()) {
									range[i][j] = 1;
								}
							}
						}
					}
				}
			}
			// 対象選択2個目 (アステル)
			else {
				// ターゲット1のレンジ内での位置
				pt.x = target1.x - (asterPoint.x - range[0].length / 2);
				pt.y = target1.y - (asterPoint.y - range.length / 2);

				for (int i = 0; i < def.length; i++) {
					for (int j = 0; j < def[0].length; j++) {
						if (def[i][j] == 1) {
							if (i != pt.y && j != pt.x) {
								range[i][j] = 1;
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
		// スワップの場合もコマンドの場合も同じ
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
		return swapMoveAstern();
	}

	public void executeSpecialCommand() {
		final Field field = getAster().getField();
		
		Effect effect = new EffectCommandJupiter(field, target1);
		getAster().getField().getScreen().paintEffect(effect);

		field.setDeleteFlag(target1);
		field.delete(target1.x, target1.y);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(7);
		}
		return asterImage;
	}

}
