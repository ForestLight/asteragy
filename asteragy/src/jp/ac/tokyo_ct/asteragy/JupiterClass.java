package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class JupiterClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public JupiterClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 7;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
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
			final Aster[][] f = field.getField();
			pt.x = asterPoint.x - (range[0].length / 2);
			pt.y = asterPoint.y - (range.length / 2);

			// プレイヤー2の場合レンジP2を使用
			if (getPlayer() == getPlayer().game.getPlayer2()) {
				// 対象選択1個目(敵ユニットのみ)
				if (target1 == null) {
					for (int i = 0; i < defaultRange.length; i++) {
						if (pt.y + i < 0 || pt.y + i >= f.length)
							continue;
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (pt.x + j < 0 || pt.x + j >= f[0].length)
								continue;

							// レンジ内であり
							if (defaultRangeP2[i][j] == 1) {
								// その位置のアステルにクラスがあり
								final AsterClass asterClass = f[pt.y + i][pt.x
										+ j].getAsterClass();
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

					for (int i = 0; i < defaultRange.length; i++) {
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (defaultRangeP2[i][j] == 1) {
								if (i != pt.y && j != pt.x) {
									range[i][j] = 1;
								}
							}
						}
					}
				}
			} else {
				// 対象選択1個目(敵ユニットのみ)
				if (target1 == null) {
					for (int i = 0; i < defaultRange.length; i++) {
						if (pt.y + i < 0 || pt.y + i >= f.length)
							continue;
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (pt.x + j < 0 || pt.x + j >= f[0].length)
								continue;

							// レンジ内であり
							if (defaultRange[i][j] == 1) {
								// その位置のアステルにクラスがあり
								final AsterClass asterClass = f[pt.y + i][pt.x
										+ j].getAsterClass();
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

					for (int i = 0; i < defaultRange.length; i++) {
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (defaultRange[i][j] == 1) {
								if (i != pt.y && j != pt.x) {
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

		// スワップの場合もコマンドの場合も同じ
		return swapSetPointAndNext(pt);
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return swapHasNext();
	}

	public boolean moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		return swapMoveAstern();
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		getAster().getField().swap(target1, target2);

	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(7);
		}
		return asterImage;
	}

}
