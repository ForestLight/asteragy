package jp.ac.tokyo_ct.asteragy;

//現在カオスってるので動かさないで．見ないで＞＜
public final class AIPlayer extends Player {

	public AIPlayer(Game game, String playerName) {
		super(game, playerName);
		System.out.println("AIPlayer");

		// backup = new Field(game);
		// backup.setFieldSize(game.getField().getX(), game.getField().getY());
		// backup.setAster();
		//
		final Field f = game.getField();
		colorBackUp = new int[f.Y][f.X];
		acBackUp = new AsterClass[f.Y][f.X];
	}

	private CanvasControl canvas;

	private Aster[] myUnit;

	private int pNum = 1;

	/**
	 * 試行回数
	 */
	private final static int TRIAL = 15;
	
	private final static int WAIT = 1000;

	private Field backup;

	// private Point[] pt = new Point[TRIAL];

	private Point pt; // 操作中のユニット位置

	private int[] cmd = new int[TRIAL]; // 選択したコマンド

	private Point[][] target = new Point[2][TRIAL]; // 選択したターゲット

	private int[] ap = new int[2]; // 仮想AP

	private int eMax; // 評価値の最大

	private int maxNum; // 何回目の試行でeMaxが更新されたか

	public Action getAction() {
		System.out.println("\n\nAIplayer.getAction()");
		if (canvas == null) {
			canvas = game.getCanvas();
		}
		if (game.getCurrentPlayer() == game.player[0]) {
			pNum = 0;
		} else {
			pNum = 1;
		}
		try {
			int state = 0;
			pt = null;
			int t = 0;
			boolean cancelFlag = false;

			while (state < 4) {
				switch (state) {
				case 0: // 操作クラスの選択
					getMyUnit();
					System.out.println("AIPlayer state0");
					pt = selectAster();
					t = 0;
					eMax = -15000;

					if (pt == null)
						return null;
					System.out.println("ac = "
							+ game.getField().at(pt).getNumber());
					state++;
					break;
				case 1: // スワップか特殊コマンドかを選択
					System.out.println("AIPlayer state1");
					// fieldClone(backup,game.getField()); //フィールドをコピー
					backUpField();
					Effect.setEffect(false);// エフェクト非表示

					ap[0] = game.player[0].getAP();
					ap[1] = game.player[1].getAP();

					if (cancelFlag) { // レンジ指定から1回戻った場合
						cmd[t] = 0;
						state++;
						cancelFlag = false;
						System.out.println("command = " + cmd[t]);
					} else {
						cmd[t] = selectCommand(pt);

						if (cmd[t] == -1) // キャンセルされた
							state--;
						else {
							state++;
							System.out.println("command = " + cmd[t]);
						}
					}
					break;
				case 2: // レンジ選択
				{
					System.out.println("AIPlayer state2");
					int i = 0;
					final Field f = game.getField();
					Aster a = f.at(pt);
					AsterClass ac = a.getAsterClass();
					ac.setCommand(cmd[t]);
					target[0][t] = null;
					target[1][t] = null;

					System.out.println("ターゲット選択");

					while (ac.hasNext() && i <= 1) {
						int[][] range = ac.getRange();
						target[i][t] = selectTarget(range, pt);
						System.out.println("ターゲット選択中");

						if (target[i][t] == null) {
							System.out.println("target null");
							if (ac.moveAstern()) {
								System.out.println("ac back");
								// i = 0;
								state = 0;
								cmd[t] = 0;
								cancelFlag = true;
								break;
							}
							i--;
						}
						// System.out.println("target - x = " + target[i][t].x +
						// " y = "
						// + target[i][t].y);
						ac.setPointAndNext(target[i][t]);
						i++;
					}
					if (i == 2
							&& f.at(target[0][t]).getColor() == f.at(
									target[1][t]).getColor()
							&& f.at(target[0][t]).getAsterClass() == null
							&& f.at(target[1][t]).getAsterClass() == null) {
						ac.moveAstern();
						ac.moveAstern();
						state = 1;

					}

					// サン専用
					if (ac.getNumber() == 1 && cmd[t] == 1) {
						Point acs = selectAsterClass(pt);
						if (acs.x == -1) {
							ac.moveAstern();
							ac.moveAstern();
							cancelFlag = true;
							state = 0;
						} else {
							ac.setPointAndNext(acs);
							target[i][t] = acs;
						}
					}

					state++;
				}
					break;

				case 3:
					System.out.println("AIPlayer state3");
					Field field = game.getField();
					final AsterClass ac = field.at(pt).getAsterClass();
					if (cmd[t] == 1) {
						if (ac.getNumber() == 1) {
							ap[pNum] -= AsterClass.classCost[target[1][t].x + 1];
						} else {
							ap[pNum] -= ac.getCommandCost();
						}
					}
					System.out.println("仮実行開始");
					ac.execute();
					System.out.println("仮実行終了");

					// 消滅判定
					System.out.println("仮消去開始");
					ap[pNum] += field.deleteAll();
					System.out.println("仮消去完了");
					int ev = evaluation(field);
					if (ev > eMax) {
						eMax = ev;
						maxNum = t;
					}
					t++;
					restoreField();
					if (t < TRIAL) {
						System.out.println("->state1");
						state = 1;
						System.out.println((t + 1) + "回目の試行に入ります");
					} else {
						System.out.println("execute : maxNum = " + maxNum
								+ " , eMax = " + eMax);
						Effect.setEffect(true);
						if (execute())
							return null;
						state = 0;
					}
				}
			}
			return null;
		} finally {
			game.getField().repaintField();
			canvas.paintEffect(canvas.disappearControl);
			Game.sleep(WAIT);
			canvas.resetEventProcesser();
			Effect.setEffect(true);
		}
	}

	/**
	 * 行動可能な自ユニットを取得
	 * 
	 */
	private void getMyUnit() {
		final Field f = game.getField();
		final int x = f.X;
		final int y = f.Y;
		Aster[] a = new Aster[x * y];
		final Aster[][] field = f.field;
		int c = 0;

		final Player currentPlayer = game.getCurrentPlayer();
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				final AsterClass ac = field[i][j].getAsterClass();
				if (ac != null) {
					if (ac.getPlayer() == currentPlayer
							&& ac.getActionCount() != 0) {
						a[c] = field[i][j];
						c++;
					}
				}
			}
		}

		if (c != 0)
			myUnit = new Aster[c];
		else
			myUnit = null;
		for (int i = 0; i < c; i++) {
			myUnit[i] = a[i];
		}
	}

	/**
	 * 操作対象選択
	 * 
	 * @return
	 */
	private Point selectAster() {
		System.out.println("AIPlayer selectAster()");
		if (myUnit == null)
			return null;
		int n = Game.random.nextInt(myUnit.length);
		Point pt = myUnit[n].getPoint();
		// canvas.getCursor().setCursor(pt, Cursor.CURSOR_1);
		// try {
		// Thread.sleep(1-000);
		// } catch (Exception e) {
		// }
		return pt;
	}

	private int selectCommand(Point pt) {
		final AsterClass ac = game.getField().at(pt).getAsterClass();
		System.out.println("AIPlayer selectCommand()");
		if (AsterClass.commandCost[ac.getNumber() - 1] > ap[pNum]) {
			return 0;
		} else {
			int cmd = Game.random.nextInt(2);
			return cmd;
			// return 0;

		}
	}

	private Point selectTarget(int[][] range, Point pt) {
		final Aster[][] f = game.getField().field;
		int[][] frange = new int[f.length][f[0].length];
		Point[] targetlist;
		int c = 0;
		System.out.println("AIPlayer selectTarget()");
		for (int i = 0; i < frange.length; i++) {
			for (int j = 0; j < frange[0].length; j++) {
				if (i >= pt.y - range.length / 2
						&& i <= pt.y + range.length / 2
						&& j >= pt.x - range[0].length / 2
						&& j <= pt.x + range[0].length / 2) {
					frange[i][j] = range[i - (pt.y - range.length / 2)][j
							- (pt.x - range[0].length / 2)];
					if (frange[i][j] == 1) {
						c++;
					}
				} else {
					frange[i][j] = -1;
				}
			}
		}

		if (c != 0)
			targetlist = new Point[c];
		else
			return null;
		c = 0;
		for (int i = 0; i < frange.length; i++) {
			for (int j = 0; j < frange[0].length; j++) {
				if (frange[i][j] == 1) {
					targetlist[c] = new Point(j, i);
					c++;
				}
			}
		}

		Point p = targetlist[Game.random.nextInt(c)];
		// p = targetlist[0];

		return p;
	}

	private Point selectAsterClass(Point pt) {
		int cmdMax = 0;

		for (int i = 0; i <= 9; i++) {
			if (AsterClass.classCost[i + 1] > ap[pNum])
				break;
			cmdMax++;
		}

		if (cmdMax == 0)
			return new Point(-1, 0);
		else {
			return new Point(Game.random.nextInt(cmdMax), 0);
		}
	}

	/**
	 * 評価関数もどき
	 * 
	 * @return
	 */
	private int evaluation(Field field) {
		int ev = 0;
		final Field field2 = field;
		final int y = field2.Y;
		final int x = field2.X;
		final Aster[][] f = field2.field;
		final Player[] p = game.player;

		Point[] sunPoint = new Point[2];
		sunPoint[0] = field2.getSunPosition(p[0]);
		sunPoint[1] = field2.getSunPosition(p[1]);

		if (sunPoint[pNum] == null) {
			System.out.println("AIPlayer.evaluation return -10000");
			return -10000;
		}
		if (sunPoint[1 - pNum] == null) {
			System.out.println("AIPlayer.evaluation return 10000");
			return 10000;
		}

		
		if(sunPoint[pNum].x < 3) ev -= (3-sunPoint[pNum].x)*150;
		else if(sunPoint[pNum].x > x - 4) ev -= (sunPoint[pNum].x - (x-4)) * 200;
		if(sunPoint[pNum].y < 2) ev -= (2-sunPoint[pNum].y)*100;
		else if(sunPoint[pNum].y > y - 3) ev -= (sunPoint[pNum].y - (x-3)) * 100;

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				final AsterClass ac = f[i][j].getAsterClass();
				if (ac != null) {
					if (ac.getPlayer() == game.getCurrentPlayer()) {
						int dist = field2.getDistance(sunPoint[1 - pNum],
								new Point(j, i));
						// System.out.println("dist = " +dist+", x = " + j +" y
						// = "+i);
						ev += AsterClass.classCost[ac.getNumber() - 1]
								* ((100 / dist) + 1);
						if (ac.getNumber() == 1) {
							ev += 500;
						}
					} else {
						int dist = field2.getDistance(sunPoint[pNum],
								new Point(j, i));
						ev -= AsterClass.classCost[ac.getNumber() - 1]
								* ((100 / dist) + 1);
						if (ac.getNumber() == 1) {
							ev -= 500;
						}
					}
				}
			}
		}

		ev += ap[pNum] * 10;
		ev -= ap[1 - pNum] * 10;

		if (ap[pNum] < 5)
			ev -= (5 - ap[pNum]) * 20;

		System.out.println("AIPlayer.evaluation return" + ev);
		return ev;
	}

	private void fieldClone(Field f1, Field f2) {
		// Field f = new Field(game);
		// f.setFieldSize(field.getX(), field.getY());
		// f.setAster();
		// System.out.println("clone");
		for (int i = 0; i < f1.Y; i++) {
			for (int j = 0; j < f1.X; j++) {

				f1.field[i][j] = f2.field[i][j].clone();
			}
		}
	}

	private void backUpField() {
		final Field f = game.getField();

		for (int i = 0; i < f.Y; i++) {
			for (int j = 0; j < f.X; j++) {
				final Aster a = f.field[i][j];
				colorBackUp[i][j] = a.getColor();
				a.setNum(i * f.X + j);
				final AsterClass ac = a.getAsterClass();
				if (ac == null) {
					acBackUp[i][j] = null;
				} else {
					acBackUp[i][j] = ac.clone();
				}
			}
		}
	}

	private void restoreField() {
		final Field f = game.getField();

		for (int i = 0; i < f.Y; i++) {
			for (int j = 0; j < f.X; j++) {
				final Aster cur = f.field[i][j];
				Aster a = cur;
				int n = a.getNum();
				final int x = f.X;
				while ((i * x + j) != n) {// アステルの順番が入れ替わってた場合
					//System.out.println("swap-"+i*x+j+"-"+n+"i="+i+"j="+j);
					f.swap(new Point(j, i), new Point(n % x, n / x));
					a = f.field[i][j];
					n = a.getNum();
				}
				a.setColor(colorBackUp[i][j]);
				if (acBackUp[i][j] == null) {
					a.setAsterClass(null);
				} else {
					a.setAsterClass(acBackUp[i][j]);
					a.getAsterClass().setAster(cur);
				}
				cur.init();
			}
		}
	}

	private int[][] colorBackUp;

	private AsterClass[][] acBackUp;

	/**
	 * 一番良さそうな行動を実行
	 * 
	 */
	private boolean execute() {
		final Field field = game.getField();
		final AsterClass ac = field.at(pt).getAsterClass();
		final Range canvasRange = game.getCanvas().range;
		int[][] range = ac.getRange();
		Effect.setEffect(true);

		canvas.cursor.setCursor(pt, Cursor.CURSOR_1);
		canvas.repaint();
		System.out.println("wait1");
		Game.sleep(WAIT);

		canvasRange.setRange(pt, range);
		final CommonCommand cc = canvas.commonCommand;
		cc.setCommand(cmd[maxNum], pt);
		cc.setAsterClass(ac);
		canvas.repaint();

		ac.setCommand(cmd[maxNum]);
		System.out.println("wait2");
		Game.sleep(WAIT);

		cc.setCommand(-1, null);
		canvas.repaint();
		int i = 0;
		while (i < 2 && target[i][maxNum] != null) {
			if (ac.getNumber() == 1 && cmd[maxNum] == 1 && i == 1) {
				ac.setPointAndNext(target[1][maxNum]);
				break;
			}
			range = ac.getRange();
			canvasRange.setRange(pt, range);
			canvas.repaint();
			ac.setPointAndNext(target[i][maxNum]);

			canvas.cursor.setCursor(target[i][maxNum], Cursor.CURSOR_1);
			System.out.println("wait3");
			Game.sleep(WAIT);
			i++;
		}

		if (ac.getNumber() == 1 && ac.getCommand() == 1) {// サンコマンド専用
			final SunCommand sc = canvas.sunCommand;
			sc.setCommand(target[1][maxNum].x, pt);
			canvas.repaint();
			Game.sleep(WAIT);
			sc.setCommand(-1, null);
			canvas.repaint();
		}

		canvasRange.setRange(null, null);
		canvas.repaint();
		if (cmd[maxNum] == 1) {
			if (ac.getNumber() == 1) {
				this.addAP(-AsterClass.classCost[target[1][maxNum].x + 1]);
			} else {
				this.addAP(-ac.getCommandCost());
			}
		}
		System.out.println("実行開始");
		ac.execute();
		System.out.println("実行完了");
		field.repaintField();
		canvas.repaint();

		Player p = field.checkGameOver();
		// ゲームオーバー判定
		if (p != null) {
			return true;
		}

		// 消滅判定
		System.out.println("消去開始");
		int n = field.deleteAll();
		if (n > 0) {
			this.addAP(n);
			field.repaintField();
			canvas.paintEffect(canvas.disappearControl);
			Game.sleep(WAIT);
		}
		System.out.println("消去完了");

		p = field.checkGameOver();
		if (p != null) {
			return true;
		}

		return false;
	}
}
