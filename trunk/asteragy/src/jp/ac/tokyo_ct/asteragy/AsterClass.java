/**
 *
 */
package jp.ac.tokyo_ct.asteragy;

import java.util.Vector;

import com.nttdocomo.ui.*;

/**
 * @author Yusuke
 * 
 */
class AsterClass {
	// asterだけはあえてコピーしていない。
	protected AsterClass(AsterClass a) {
		game = a.game;
		player = a.player;
		number = a.number;
		actionCount = a.actionCount;
		mode = a.mode;
		field = a.field;
		// aster = a.getAster();
		if (a.target1 == null)
			target1 = null;
		else
			target1 = a.target1.clone();
		if (a.target2 == null)
			target2 = null;
		else
			target2 = a.target2.clone();
		isProtected = a.isProtected;
	}

	AsterClass clone() {
		return new AsterClass(this);
	}

	AsterClass(Aster a, Player p, int classNumber) {
		aster = a;
		player = p;
		number = classNumber;
		actionCount = getActionNum();
		field = a.field;
		game = field.game;
		a.setAsterClass(this);
	}

	int asterClassSelect; //サン専用

	/**
	 * クラスの対応する番号を返す
	 * 
	 * @return クラス固有の番号
	 */
	int getNumber() {
		return number;
	}
	
	private int number;

	final Aster getAster() {
		return aster;
	}

	private Aster aster;

	final Game game;

	Field field;

	final Player getPlayer() {
		return player;
	}

	final void setPlayer(Player p) {
		player = p;
	}

	private Player player;

	final void setCommand(int cmd) {
		mode = cmd;
	}

	final int getCommand() {
		return mode;
	}

	final void setAster(Aster a) {
		aster = a;
	}

	final Point getPoint() {
		return field.asterToPoint(aster);
	}

	protected int mode = 0;

	protected Point target1 = null;

	protected Point target2 = null;

	/**
	 * 現在の選択範囲を返す
	 * 
	 * @return 現在の選択範囲
	 */
	final int[][] getRange() {
		final int[][] def = getDefaultRange(getNumber(), player != game.player[0]);
		if (mode == 0) {
			return swapGetRange(def);
		}
		final Point thisPoint = getPoint();
		switch (getNumber()) {
		case 1: { //サン
			final int[][] range = new int[sunRange.length][sunRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));

			for (int i = 0; i < sunRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < sunRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (sunRange[i][j] == 1) {
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
		case 2: { //スター
			return swapGetRange(def);
		}
		case 3: {//マーキュリー
			final int[][] range = new int[mercuryRange.length][mercuryRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2), thisPoint.y
					- (range.length / 2));
			for (int i = 0; i < mercuryRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.Y)
					continue;
				for (int j = 0; j < mercuryRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x + j >= field.X)
						continue;
					// レンジ内であり
					if (mercuryRange[i][j] == 1) {
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
		case 4: { //ビーナス
			int[][] range = new int[venusRange.length][venusRange[0].length];
			final Aster[][] f = field.field;
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));
			for (int i = 0; i < venusRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < venusRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;
					// レンジ内であり
					if (def[i][j] == 1) {
						final Aster a = f[pt.y + i][pt.x + j];
						final AsterClass asterClass = a.getAsterClass();
						// その位置のアステルにクラスがあり
						// そのクラスの所持者が相手であり
						// サンでなければ対象に選択可能
						if (asterClass != null
								&& asterClass.getPlayer() != getPlayer()
								&& a.getNumber() != 1) {
							range[i][j] = 1;
						}
					}
				}
				return range;
			}
		}
		case 5: { //アース
			// SunClassから拝借
			final int[][] range = new int[def.length][def[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));
			for (int i = 0; i < def.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < def[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (def[i][j] == 1) {
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
		case 6: { //マーズ
			//final Aster a = getAster();
			final int[][] range = new int[marsRange.length][marsRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = thisPoint;//.clone();
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;
			for (int i = 0; i < def.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < def[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;
					if (def[i][j] == 1) {
						// レンジ内で自身かサン以外なら選択可
						final Aster aster2 = field.at(pt.y + i, pt.x + j);
						if (aster2.getNumber() != 1 && aster2 != aster) {
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
			return range;
		}
		case 7: { //ジュピター
			int[][] range = new int[jupiterRange.length][jupiterRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));
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
				pt.x = target1.x - (thisPoint.x - range[0].length / 2);
				pt.y = target1.y - (thisPoint.y - range.length / 2);

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
		case 8: //サターン
			return null;
		case 9: { //ウラヌス
			int[][] range = new int[uranusRange.length][uranusRange[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Aster[][] f = field.field;
			Point pt = thisPoint; //.clone();
			pt.x -= range[0].length / 2;
			pt.y -= range.length / 2;

			// 1個目
			if (target1 == null) {
				for (int i = 0; i < uranusRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < uranusRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (uranusRange[i][j] == 1) {
							// レンジ内で自身以外なら選択可
							if (f[pt.y + i][pt.x + j] != aster) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}
			// 2個目
			else {
				for (int i = 0; i < uranusRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < uranusRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (uranusRange[i][j] == 1) {
							// レンジ内で自身と1個目の場所以外なら選択可
							if (f[pt.y + i][pt.x + j] != aster
									&& field.asterToPoint(aster) != target1) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
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
		case 10: { //ネプチューン
			final int[][] range = new int[def.length][def[0].length];
			// レンジの左上の座標のフィールド内での位置
			final Point pt = new Point(thisPoint.x - (range[0].length / 2), thisPoint.y - (range.length / 2));

			for (int i = 0; i < def.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < def[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// レンジ内であり
					if (def[i][j] == 1) {
						range[i][j] = 1;
						// その位置のアステルにクラスがあり
						final Aster a = field.at(pt.y + i, pt.x + j);
						final AsterClass c = a.getAsterClass();
						if (c != null) {
							if (c.getNumber() == 1) {
								range[i][j] = 0;
							}
						}
					}
				}
			}
			return range;
		}
		case 11: //プルート
		case 12: //ムーン
			return null;
		default:
			System.out.println("ERROR! AsterClass.getRange");
			throw new RuntimeException("hasNext");
		}
	}


	/**
	 * @return 範囲に問題がなければtrue、そうでなければfalse
	 */
	final boolean setPointAndNext(Point pt) {
		if (mode == 0) {
			return swapSetPointAndNext(pt);
		}
		switch (getNumber()) {
		case 2:
		case 9:
			return swapSetPointAndNext(pt);
		case 1:
			if (target1 == null) {
				target1 = pt;
				
			} else {
				asterClassSelect = pt.x;
			}
			return false;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 10:
			target1 = pt;
			return true;
		case 8:
		case 11:
		case 12:
			return false;
		default:
			throw new RuntimeException("setPointAndNext");
		}
	}

	final boolean hasNext() {
		if (mode == 0) {
			return swapHasNext();
		}
		switch (getNumber()) {
		case 2:
		case 9:
			return swapHasNext();
		case 1:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 10:
			return target1 == null;
		case 8:
		case 11:
		case 12:
			return false;
		default:
			throw new RuntimeException("hasNext");
		}
	}
	
	/**
	 * 1つ前の選択に戻る。
	 * 
	 * @return 一つ目の対象選択中に呼ばれた場合true
	 */
	final boolean moveAstern() {
		if (mode == 0) {
			return swapMoveAstern();
		}
		switch (getNumber()) {
		case 1:
		case 2:
		case 9:
			return swapMoveAstern();
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 10:
			return true;
		case 8:
		case 11:
		case 12:
			return false;
		default:
			throw new RuntimeException("moveAstern");
		}
	}
	/**
	 * @return クラス名
	 */
	final String getName() {
		return AsterClass.className[getNumber() - 1];
	}

	/**
	 * @return 特殊コマンド名
	 */
	final String getCommandName() {
		return AsterClass.commandName[getNumber() - 1];
	}

	/**
	 * @return 特殊コマンドの説明
	 */
	final String getExplain() {
		return AsterClass.commandExplain[getNumber() - 1];
	}

	/**
	 * 
	 * @return クラス付与時のコスト
	 */
	final int getCost() {
		return classCost[getNumber() - 1];
	}

	/**
	 * 
	 * @return 特殊コマンド使用時のコスト
	 */
	final int getCommandCost() {
		return AsterClass.commandCost[getNumber() - 1];
	}

	private final void logAction(int commandType, int[] args) {
		Action a = new Action();
		a.aster = aster;
		a.commandType = commandType;
		a.args = args;
		game.logAction(a);
	}

	protected final void logAction(int[] args) {
		logAction(1, args);
	}

	protected final void logAction() {
		logAction(1, new int[0]);
	}

	protected final void logAction(Point pt1) {
		logAction(1, new int[] { pt1.x, pt1.y });
	}

	protected final void logAction(Point pt1, Point pt2) {
		logAction(1, new int[] { pt1.x, pt1.y, pt2.x, pt2.y });
	}
	
	/**
	 * コマンドを実行
	 * 
	 */
	final void execute(String deleteList) {
		System.out.println("AsterClass.execute()");
		switch (mode) {
		case 0:
			executeSwap(deleteList, new int[] {target1.x, target1.y, target2.x, target2.y});
			break;
		case 1:
			executeSpecialCommand();
			postExecute(deleteList);
			break;
		}
	}
	
	final private void postExecute(String deleteList) {
		final Field field = getAster().field;
		// 行動可能回数を減らす
		decActionCount();

		// 消滅判定
		System.out.println("消去開始");
		final CanvasControl canvas = field.getCanvas();
		final Vector disappearing = canvas.disappearControl.disappearing;
		player.addAP(field.deleteAll(disappearing));
		if (deleteList != null && deleteList.length() != 0) {
			disappearing.removeAllElements();
			for (int i = 0; i + 2 < deleteList.length();) {
				int x = HTTPPlayer.parseIntChar(deleteList.charAt(i++));
				int y = HTTPPlayer.parseIntChar(deleteList.charAt(i++));
				disappearing.addElement(new Point(x, y));
				field.field[y][x].setColor(HTTPPlayer.parseIntChar(deleteList
						.charAt(i++)));
			}
		}
		game.logDeleteInfo(field, disappearing);
		System.out.println("消去完了");
		canvas.paintEffect(canvas.disappearControl);

		// ターゲット初期化
		target1 = null;
		target2 = null;	}
	
	final void executeSwap(String deleteList, int[] args) {
		logAction(0, args);
		field.swap(args[0], args[1], args[2], args[3]);

		// スワップエフェクト。
		field.getCanvas().paintEffect(new EffectFieldSwap(args));
		/*
		 * // サン自滅判定（ダイアログは仮なので然るべき演出に置き換えておいてください） if
		 * (field.judgeSelfDestruction() == true) { Dialog d = new
		 * Dialog(Dialog.DIALOG_YESNO, "注意"); d.setText("サンが消えます"); if(d.show() ==
		 * Dialog.BUTTON_NO){ field.restoreField(); incActionCount(); break; } }
		 */
		postExecute(deleteList);
	}

	/**
	 * 特殊コマンドを実行
	 * 
	 */
	final void executeSpecialCommand() {
		switch (getNumber()) {
		case 1: {//サン
			final Aster a = field.at(target1);
			AsterClass ac;
			logAction(new int[] {target1.x, target1.y, asterClassSelect});
			System.out.println("acs = " + asterClassSelect);
			if (0 <= asterClassSelect && asterClassSelect <= 9) {
				ac = new AsterClass(a, getPlayer(), asterClassSelect + 2);
			} else {
				System.out.print("executeSpecialCommand - Sun: Unknown class");
				throw new RuntimeException("executeSpecialCommand - Sun: Unknown class");
			}
			// 選択したクラスのユニットを行動不可能状態で召還
			ac.setActionCount(0);
			break;
		}
		case 2: { //スター
			field.getCanvas().paintEffect(new EffectCommandStar(field, this, target1, target2));
			logAction(target1, target2);
			field.swap(target1.x, target1.y, target2.x, target2.y);
			break;
		}
		case 3: { //マーキュリー
			field.getCanvas().paintEffect(new EffectCommandMercury(field, target1));
			logAction(target1);
			// 対象の行動可能回数を1回増やす
			field.at(target1).getAsterClass().incActionCount();
			logAction(target1);
			break;
		}
		case 4: { //ビーナス
			// 対象の所持者を変更
			final AsterClass ac = field.at(target1).getAsterClass();
			field.getCanvas().paintEffect(new EffectCommandVenus(target1));
			ac.setPlayer(this.getPlayer());
			logAction(target1);
			// 行動済状態に
			ac.setActionCount(0);
			break;
		}
		case 5: { //アース
			Effect effect = new EffectCommandEarth(target1);
			field.getCanvas().paintEffect(effect);
			logAction(target1);
			final Aster a = field.at(target1);
			new AsterClass(a, player, 12);
			a.getAsterClass().setActionCount(0);
			break;
		}
		case 6: { //マーズ
			field.getCanvas().paintEffect(new EffectCommandMars(field, this, target1));
			logAction(target1);
			field.setDeleteFlag(target1);
			field.delete(target1.x, target1.y, game.getCanvas().disappearControl.disappearing);
		}
		case 7: { //ジュピター
			Effect effect = new EffectCommandJupiter(target1);
			game.getCanvas().paintEffect(effect);
			logAction(target1);
			field.setDeleteFlag(target1);
			field.delete(target1.x, target1.y, game.getCanvas().disappearControl.disappearing);
			break;
		}
		case 8:{ //サターン
			// 左回り
			// 修正@2/25 右回りに
			int i, j;
			int num, flag = 0;
			final Point me = getPoint();
			Point pt = new Point(me.x - (saturnRange[0].length / 2), me.y
					- (saturnRange.length / 2));
			final Aster[] queue = new Aster[17];
			final Aster[][] f = field.field;			
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
			field.getCanvas().paintEffect(
					new EffectCommandSaturn(field, this, queue));
			num = --i;
			for (i = 0, j = 0; j < 16; j++) {
				// 右回りに戻していく
				if (field.isXInFieldBound(pt.x) && field.isYInFieldBound(pt.y)) {
					if (flag == 0) {
						f[pt.y][pt.x] = queue[num];
						flag++;
					} else {
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
			break;
			}
		case 9: { //ウラヌス
			field.getCanvas().paintEffect(new EffectCommandUranus(target1, target2));
			logAction(target1, target2);
			field.swap(target1.x, target1.y, target2.x, target2.y);
			break;
		}
		case 10: { //ネプチューン
			// ターゲットと自分をswap
			field.getCanvas().paintEffect(new EffectCommandNeptune(target1));
			logAction(target1);
			Point self = getPoint();
			field.swap(target1.x, target1.y, self.x, self.y);
			break;
		}
		case 11: { //プルート
			System.out.println("るいんくらすと");
			Point me = getPoint();
			Point pt = new Point();
			final int rangeY = plutoRange.length;
			final int rangeX = plutoRange[0].length;
			logAction();

			for (int i = 0; i < rangeY; i++) {
				for (int j = 0; j < rangeX; j++) {
					// レンジ内であり
					if (plutoRange[i][j] == 1) {
						// 自身ではない部分を破壊
						if (i != rangeY / 2 || j != rangeX / 2) {
							pt.x = me.x - rangeX / 2 + j;
							pt.y = me.y - rangeY / 2 + i;

							// フィールドの外にはみ出してたら処理しない
							if (!field.isXInFieldBound(pt.x))
								continue;
							if (!field.isYInFieldBound(pt.y))
								continue;

							// 破壊対象にdeleteFlag
							System.out.println("ruincrust target" + pt.x + ","
									+ pt.y);
							field.setDeleteFlag(pt);
						}
					}
				}
			}
			game.getCanvas().paintEffect(new EffectCommandPluto(field, me));
			field.deleteAll(game.getCanvas().disappearControl.disappearing);
			logAction();
			break;
			}
		case 12: {
				Point me = getPoint();
				for (int i = 0; i < field.Y; i++) {
					for (int j = 0; j < field.X; j++) {
						// 自分のサンをみつける
						final Aster a = field.field[i][j];
						if (a.getNumber() == 1
								&& a.getAsterClass().getPlayer() == getPlayer()) {
							Point pt = new Point(j, i);
							
							Effect eff = new EffectCommandMoon(me, pt);
							game.getCanvas().paintEffect(eff);
							field.swap(pt.x, pt.y, me.x, me.y);
							field.setDeleteFlag(pt);
							field.delete(pt.x, pt.y, game.getCanvas().disappearControl.disappearing);
							logAction();
							return;
						}
					}
				}
				break;
			}
		}
	}

	/**
	 * 行動可能回数、フラグ初期化
	 */
	final void init() {
		// 行動回数リセット
		actionCount = getActionNum();
		// フラグ消去
		isProtected = false;

		mode = 0;
	}

	final int getActionNum() {
		return AsterClass.actionNum[getNumber() - 1];
	}

	/**
	 * 行動可能回数増
	 */
	final void incActionCount() {
		actionCount++;
	}

	/**
	 * 行動可能回数減
	 */
	final void decActionCount() {
		actionCount--;
	}

	final void setActionCount(int i) {
		actionCount = i;
	}

	final int getActionCount() {
		return actionCount;
	}

	/**
	 * 行動可能回数
	 */
	private int actionCount;

	final void setProtectedFlag(boolean b) {
		isProtected = b;
	}

	final boolean getProtectedFlag() {
		return isProtected;
	}

	/**
	 * 対象不可フラグ
	 */
	private boolean isProtected;

	final static int PINK = 5;

	final static int YELLOW = 4;

	final static int GREEN = 3;

	final static int BLUE = 2;

	final static int RED = 1;

	static int COLOR_MAX = 5;

	/**
	 * 現在の選択範囲を返す (スワップ用)
	 * 
	 * @return 現在の選択範囲
	 */
	protected final int[][] swapGetRange(int[][] defaultRange) {
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		final Field f = aster.field;
		// 1個目の対象選択
		if (target1 == null) {
			for (int i = 0; i < defaultRange.length; i++) {
				for (int j = 0; j < defaultRange[0].length; j++) {
					// 上下左右に隣接レンジが無い孤立したレンジを除外
					int fi = i + (f.asterToPoint(aster).y - range.length / 2);
					int fj = j
							+ (f.asterToPoint(aster).x - range[0].length / 2);

					if (i + 1 >= defaultRange.length
							|| defaultRange[i + 1][j] == 0
							|| !f.isYInFieldBound(fi + 1)) {
						if (i - 1 < 0 || defaultRange[i - 1][j] == 0
								|| !f.isYInFieldBound(fi - 1)) {
							if (j + 1 >= defaultRange[0].length
									|| defaultRange[i][j + 1] == 0
									|| !f.isXInFieldBound(fj + 1)) {
								if (j - 1 < 0 || defaultRange[i][j - 1] == 0
										|| !f.isXInFieldBound(fj - 1)) {
									continue;
								}
							}
						}
					}
					// if (defaultRange[i + 1][j] + defaultRange[i - 1][j]
					// + defaultRange[i][j + 1] + defaultRange[i][j - 1] != 0) {
					range[i][j] = defaultRange[i][j];
					// }
				}
			}

		}
		// 2個目の対象選択
		else {
			// target1の座標をレンジ内に修正したもの
			final Point selftPoint = aster.getPoint();
			Point pt = new Point(target1.x
					- (selftPoint.x - range[0].length / 2), target1.y
					- (selftPoint.y - range.length / 2));
			System.out.println("tx:" + target1.x + " ty:" + target1.y + " spx:"
					+ selftPoint.x + " spy:" + selftPoint.y);
			System.out.println("px:" + pt.x + " py:" + pt.y);

			for (int i = 0; i < range.length; i++) {
				for (int j = 0; j < range[0].length; j++) {
					// 1個目の対象の上下左右のマスで、元のレンジに含まれている場所のみ1
					if (defaultRange[i][j] == 1) {
						if (i == pt.y - 1 && j == pt.x || i == pt.y + 1
								&& j == pt.x || i == pt.y && j == pt.x + 1
								|| i == pt.y && j == pt.x - 1) {
							System.out.println("range i:" + i + " j:" + j);
							range[i][j] = 1;
						}
					}
				}
			}
			System.out.println("test");
			// 1個目の対象の位置を移動可・選択不可
			range[pt.y][pt.x] = 0;
		}
		System.out.println("SwapGetRange end");
		return range;
	}

	protected final boolean swapMoveAstern() {
		// 1個目の対象選択中に呼ばれた場合
		if (target1 == null) {
			System.out.println("swapMoveAstern return true");
			return true;
		}
		// 2個目の対象選択中に呼ばれた場合
		if (target2 == null) {
			target1 = null;
		}
		// 2個目の対象選択後に呼ばれた場合
		else {
			target2 = null;
		}
		System.out.println("swapMoveAstern return false");
		return false;
	}

	protected final boolean swapSetPointAndNext(Point pt) {
		if (target1 == null) {
			target1 = pt;
		} else {
			target2 = pt;
		}
		return true;
	}

	protected final boolean swapHasNext() {
		boolean ret = target1 == null || target2 == null;
		System.out.println("swapHasNext return " + ret);
		return ret;
	}

	final static int[][] getDefaultRange(int n, boolean player2) {
		switch (n) {
		case 1:
			return sunRange;
		case 2:
			return starRange;
		case 3:
			return mercuryRange;
		case 4:
			return player2 ? venusRangeP2 : venusRange;
		case 5:
			return earthRange;
		case 6:
			return player2 ? marsRangeP2 : marsRange;
		case 7:
			return player2 ? jupiterRangeP2 : jupiterRange;
		case 8:
			return saturnRange;
		case 9:
			return uranusRange;
		case 10:
			return neptuneRange;
		case 11:
			return plutoRange;
		case 12:
			return moonRange;
		}
		return null;
	}
	
	static final int[][] sunRange = { { 0, 0, 1, 0, 0 },
		{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
		{ 0, 0, 1, 0, 0 } };

	static final int[][] starRange = { { 0, 1, 0 }, { 1, 1, 1 },
		{ 0, 1, 0 } };

	static final int[][] mercuryRange = { { 0, 0, 0, 0, 0 },
		{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
		{ 0, 0, 0, 0, 0 } };

	static final int[][] venusRange = { { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 },
		{ 0, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };

	static final int[][] venusRangeP2 = { { 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 },
		{ 0, 0, 1, 0, 0 } };

	static final int[][] earthRange = { { 1, 1, 1 }, { 1, 1, 1 },
		{ 1, 1, 1 } };

	static final int[][] marsRange = { { 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

	static final int[][] marsRangeP2 = { { 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	static final int[][] jupiterRange = { { 0, 0, 0, 0, 0 },
		{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
		{ 0, 1, 1, 1, 0 } };

	static final int[][] jupiterRangeP2 = { { 0, 1, 1, 1, 0 },
		{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
		{ 0, 0, 0, 0, 0 } };

	static final int[][] saturnRange = { { 1, 1, 1, 1, 1 },
		{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 },
		{ 1, 1, 1, 1, 1 } };

	static final int[][] uranusRange = { { 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
		{ 1, 0, 1, 1, 1, 0, 1 }, { 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	static final int[][] neptuneRange = { 
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }};

	static final int[][] plutoRange = { { 1, 1, 0, 1, 1 },
		{ 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 },
		{ 1, 1, 0, 1, 1 } };

	static final int[][] moonRange = { { 0, 1, 0 }, { 1, 1, 1 },
		{ 0, 1, 0 } };

	static Image getImage(AsterClass ac) {
		return asterImage[ac == null ? 0 : ac.getNumber()];
	}

	static final Image[] asterImage = initImage();

	private static Image[] initImage() {
		Image[] img = new Image[MAX_CLASS + 1];
		for (int i = 0; i < img.length; i++) {
			img[i] = Game.loadImage("aster_".concat(String.valueOf(i)));
		}
		return img;
	}

	static final int MAX_CLASS = 12;

	final static String[] className = { "ｻﾝ", "ｽﾀｰ", "ﾏｰｷｭﾘｰ", "ｳﾞｨｰﾅｽ", "ｱｰｽ",
			"ﾏｰｽﾞ", "ｼﾞｭﾋﾟﾀｰ", "ｻﾀｰﾝ", "ｳﾗﾇｽ", "ﾈﾌﾟﾁｭｰﾝ", "ﾌﾟﾙｰﾄ", "ﾑｰﾝ", };

	final static String[] classNameF = { "サン", "スター", "マーキュリー", "ヴィーナス", "アース",
			"マーズ", "ジュピター", "サターン", "ウラヌス", "ネプチューン", "プルート", "ムーン", };

	final static String[] commandName = { "サモンプラネット", "スワップ", "クイックタイム",
			"テンプテーション", "サモンムーン", "アスターフレア", "プロテクションシステム", "ローテーション",
			"トランスポート", "ソニックムーブ", "ディザスター", "トータルイクリプス", };

	final static String[] commandExplain = { "アステル1個にクラスを与える",
			"2つの隣り合ったアステルを入れ替える", "行動済ユニット1体を行動可能にする", "敵ユニット1体を奪い取る",
			"アステル1個にムーンクラスを与える", "アステル1個を破壊する(サンは不可)", "敵ﾕﾆｯﾄ1体を破壊する(サンは不可)",
			"リング部分を右回りにローテーション", "2つのアステルを入れ替える", "自分とアステル1個を入れ替える",
			"レンジ内のアステルを全て破壊する", "自身を破壊して自分のサンを移動", };

	final static int[] classCost = { 0, 2, 6, 5, 4, 8, 7, 11, 10, 11, 12, 0 };

	final static int[] commandCost = { 0, 0, 3, 7, 4, 5, 1, 4, 5, 6, 18, 4 };

	final static int[] actionNum = { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3 };
}
