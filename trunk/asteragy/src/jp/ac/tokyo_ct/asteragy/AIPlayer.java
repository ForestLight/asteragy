package jp.ac.tokyo_ct.asteragy;

public class AIPlayer extends Player {

	public AIPlayer(Game game, String playerName) {
		super(game, playerName);
		System.out.println("AIPlayer");
	}

	private CanvasControl canvas;

	private Aster[] myUnit;

	public Action getAction() {
		System.out.println("KeyInputPlayer.getAction()");
		if (canvas == null) {
			canvas = game.getCanvas();
		}

		try {

			int state = 0;
			Point pt = null;
			int cmd = -1; // 0 = swap, 1 = 特殊コマンド
			while (state < 4) {
				getMyUnit();
				switch (state) {
				case 0: // 操作クラスの選択
					pt = selectAster();

					if (pt == null)
						return null;
					state++;
					break;
				case 1: // スワップか特殊コマンドかを選択
					cmd = selectCommand(pt);
					if (cmd == -1) // キャンセルされた
						state--;
					else
						state++;
					break;
				case 2: // レンジ選択
				{
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					ac.setCommand(cmd);

					System.out.println("ターゲット選択");

					final Range canvasRange = game.getCanvas().getRange();
					while (ac.hasNext()) {
						int[][] range = ac.getRange();
						canvasRange.setRange(pt, range);
						Point target = selectTarget(range, pt);
						System.out.println("ターゲット選択中");
						System.out.println("target - x = " + pt.x + " y = "
								+ pt.y);
						if (target == null) {
							if (ac.moveAstern()) {
								state = -1;
								cmd = 0;
								break;
							}
						}
						ac.setPointAndNext(target);
					}
					canvasRange.setRange(null, null);

					// サン専用
					if (ac.getNumber() == 1 && cmd == 1) {
						Point acs = selectAsterClass(pt);
						if (acs.x == -1) {
							ac.moveAstern();
							state = -1;
						} else {
							ac.setPointAndNext(acs);
						}
					}

					state++;
				}
					break;

				case 3:
					final Field field = game.getField();
					final AsterClass ac = field.getAster(pt).getAsterClass();
					if (cmd == 1) {
						this.addSP(-ac.getCommandCost());
					}
					// game.getField().fieldBackUp();
					System.out.println("実行開始");
					ac.execute();
					System.out.println("実行完了");
					field.repaintField();

					Player p = field.checkGameOver();
					// ゲームオーバー判定仮
					if (p != null) {
						return null;
					}

					// 消滅判定
					System.out.println("消去開始");
					this.addSP(game.getField().deleteAll());
					System.out.println("消去完了");
					field.repaintField();

					p = field.checkGameOver();

					if (p != null) {
						return null;
					}
					evaluation();
					state = 0;
				}
			}
			return null;
		} finally {
			canvas.resetEventProcesser();
		}
	}

	/**
	 * 行動可能な自ユニットを取得
	 * 
	 */
	private void getMyUnit() {
		final Field f = game.getField();
		final int x = f.getX();
		final int y = f.getY();
		Aster[] a = new Aster[x * y];
		final Aster[][] field = f.getField();
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
		canvas.getCursor().setCursor(pt, Cursor.CURSOR_1);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		return pt;
	}

	private int selectCommand(Point pt) {
		final AsterClass ac = game.getField().getAster(pt).getAsterClass();
		System.out.println("AIPlayer selectCommand()");
		if (AsterClass.commandCost[ac.getNumber() - 1] > this.getSP()) {
			return 0;
		} else {
			int cmd = Game.random.nextInt(2);
			canvas.getCommonCommand().setCommand(cmd, pt);
			canvas.getCommonCommand().setAsterClass(ac);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			canvas.getCommonCommand().setCommand(-1, null);
			return cmd;

		}
	}

	private Point selectTarget(int[][] range, Point pt) {
		final Aster[][] f = game.getField().getField();
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
		canvas.getCursor().setCursor(p, Cursor.CURSOR_1);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		return p;
	}

	private Point selectAsterClass(Point pt) {
		int cmdMax = 0;

		for (int i = 0; i <= 9; i++) {
			if (AsterClass.classCost[i + 1] > this.getSP())
				break;

			cmdMax++;
		}

		if (cmdMax == 0)
			return new Point(-1, 0);
		else {
			return new Point(Game.random.nextInt(cmdMax + 1), 0);
		}
	}

	/**
	 * 評価関数もどき
	 * 
	 * @return
	 */
	private int evaluation() {
		int p = 0;
		final Field field2 = game.getField();
		final int y = field2.getY();
		final int x = field2.getX();
		final Aster[][] f = field2.getField();

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				final AsterClass ac = f[i][j].getAsterClass();
				if (ac != null) {
					if (ac.getPlayer() == game.getCurrentPlayer()) {
						p += AsterClass.classCost[ac.getNumber() - 1];
						if (ac.getNumber() == 1) {
							p += 500;
						}
					} else {
						p -= AsterClass.classCost[ac.getNumber() - 1];
						if (ac.getNumber() == 1) {
							p -= 500;
						}
					}
				}
			}
		}

		p += this.getSP();
		if (game.getCurrentPlayer() == game.getPlayer1()) {
			p -= game.getPlayer2().getSP();
		} else {
			p -= game.getPlayer1().getSP();
		}

		System.out.println("AIPlayer.evaluation return" + p);
		return p;
	}
}
