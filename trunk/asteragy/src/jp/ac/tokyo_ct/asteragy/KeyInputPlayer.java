package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class KeyInputPlayer extends Player {

	/**
	 * @param playerName
	 *            プレイヤーの名前
	 */
	public KeyInputPlayer(Game game, String playerName) {
		super(game, playerName);
		System.out.println("KeyInputPlayer");
	}

	private CanvasControl canvas;
	
	private Point cursorPoint;
	/*
	 * (非 Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	public Action getAction() {
		System.out.println("KeyInputPlayer.getAction()");
		if (canvas == null) {
			canvas = game.getCanvas();
		}
		try {
			int state = 0;
			Point pt = null;
			cursorPoint = game.getField().getSunPosition(this);
			int cmd = -1; // 0 = swap, 1 = 特殊コマンド
			while (state < 4) {

				switch (state) {
				case 0: // 操作クラスの選択
					pt = selectAster();

					if (pt == null)
						return null;
					cursorPoint = pt;
					state++;
					break;
				case 1: // スワップか特殊コマンドかを選択
				{
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					final Range canvasRange = game.getCanvas().getRange();
					int[][] range = ac.getRange();
					
					canvasRange.setRange(pt, range);
					
					cmd = selectCommand(pt);
					
				//	canvasRange.setRange(null, null);
					
					if (cmd == -1) // キャンセルされた
						state--;
					else
						state++;
					break;
				}
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
							this.addAP(-AsterClass.classCost[acs.x + 1]);
						}
					}

					state++;
				}
					break;

				case 3:
					final Field field = game.getField();
					final AsterClass ac = field.getAster(pt).getAsterClass();
					if (cmd == 1) {
						this.addAP(-ac.getCommandCost());
					}
					// game.getField().fieldBackUp();
					System.out.println("実行開始");
					ac.execute();
					System.out.println("実行完了");
					field.repaintField();

					Player p = field.checkGameOver();
					// ゲームオーバー判定仮
					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addAP(ac.getCommandCost());
						// }
						// state=0;
						// System.out.println("字軍サン消滅 行動キャンセル");
						// break;
						// }
						return null;
					}

					// 消滅判定
					System.out.println("消去開始");
					this.addAP(field.deleteAll());
					System.out.println("消去完了");
					field.repaintField();

					p = field.checkGameOver();

					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addAP(ac.getCommandCost());
						// }
						// state=0;
						// System.out.println("字軍サン消滅 行動キャンセル");
						// break;
						// }
						return null;
					}

					state = 0;
				}
			}
			return null;
		} finally {
			canvas.resetEventProcesser();
		}
	}

	/**
	 * フィールド上にカーソルを上下左右に動かして自クラス持ちのアステルを選択する。
	 * 
	 * @return 選択されたアステルの座標
	 */
	private Point selectAster() {
		final class EventProcesserForSelectAster extends
				KeyProcessedEventProcesserImpl {
			/**
			 * コンストラクタ
			 * 
			 * @param keyInputPlayer
			 *            基となるKeyInputPlayer
			 */
			EventProcesserForSelectAster() {
				final Field field = game.getField();
				x = cursorPoint.x;
				y = cursorPoint.y;
				applyPosition();
			}

			/*
			 * (非 Javadoc) selectAster用にprocessEventを受け取る。
			 * 
			 * @see jp.ac.tokyo_ct.asteragy.EventProcesser#processEvent(int,
			 *      int)
			 */
			protected void processKeyEvent(int key) {
				switch (key) {
				case Display.KEY_UP:
					if (y > 0) {
						y--;
					}
					break;
				case Display.KEY_DOWN:
					if (y < game.getField().getY() - 1) {
						y++;
					}
					break;
				case Display.KEY_LEFT:
					if (x > 0) {
						x--;
					}
					break;
				case Display.KEY_RIGHT:
					if (x < game.getField().getX() - 1) {
						x++;
					}
					break;
				}
				applyPosition();
				System.out
						.println("EventProcesserForSelectAster.processEvent x = "
								+ x + ", y = " + y);
			}

			/**
			 * キャンセル可 キャンセルの場合ターン終了
			 */
			protected boolean onCancel() {
				pt = null;
				return true;
			}

			public Point getPoint(CanvasControl c) {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				AsterClass ac;
				final Field field = game.getField();
				do {
					resetSelected();
					waitForSelect(c);
					ac = field.at(y, x).getAsterClass();
					System.out
							.println("EventProcesserForSelectAster.getPoint x = "
									+ x + ", y = " + y);
				} while (pt != null
						&& (ac == null || ac.getPlayer() != KeyInputPlayer.this || ac
								.getActionCount() == 0));

				// return new Point(x, y);
				if (pt != null) {
					pt = new Point(x, y);
				}
				return pt;
			}

			private void applyPosition() {

				canvas.getCursor().setCursor(new Point(x, y), Cursor.CURSOR_1);
			}

			private Point pt = new Point();

			private int x;

			private int y;
		}

		canvas.getCommonCommand().setCommand(-1, null);
		System.out.println("KeyInputPlayer.selectAster()");
		EventProcesserForSelectAster ep = new EventProcesserForSelectAster();
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		Point pt = ep.getPoint(canvas);
		canvas.removeEventProcesser(ep);
		// System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x
		// + ", y = " + pt.y);
		return pt;
	}

	/**
	 * コマンド（スワップ・特殊）を選択する
	 * 
	 * @param pt
	 *            クラスの位置
	 * @return コマンド種別。1は特殊、0はスワップ、-1はキャンセル。
	 */
	private int selectCommand(final Point pt) {
		final class EventProcesserForSelectCommand extends
				KeyProcessedEventProcesserImpl {
			EventProcesserForSelectCommand() {
				ac = game.getField().getAster(pt).getAsterClass();
			}

			protected void processKeyEvent(int key) {
				System.out.println("selectCommand.processKeyEvent");
				switch (key) {
				case Display.KEY_UP:
					if (command > 0) {
						command--;
					}else{
						command = 1;
					}
					break;
				case Display.KEY_DOWN:
					if (command < 1) {
						command++;
					}else{
						command = 0;
					}
					break;
				}
				System.out.println("selectCommand.processKeyEvent");
				canvas.getCommonCommand().setCommand(command, pt);
				canvas.getCommonCommand().setAsterClass(ac);
			}

			protected boolean onCancel() {
				command = -1;
				return true;
			}

			public int selectCommand(CanvasControl c) {
				System.out
						.println("EventProcesserForSelectCommand.selectCommand()");
				do {
					resetSelected();
					waitForSelect(c);
					// AP足らないのにコマンド選んでる場合のみ受け付けない
				} while (command == 1
						&& AsterClass.commandCost[ac.getNumber() - 1] > KeyInputPlayer.this
								.getAP());
				switch (command) {
				case -1:
					System.out.println("selectCommand - キャンセル");
					break;
				case 0:
					System.out.println("selectCommand - スワップ");
					break;
				case 1:
					System.out.println("selectCommand - 特殊");
					break;
				}
				return command;
			}

			private final AsterClass ac;

			private volatile int command = 0;
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		canvas.getCommonCommand().setCommand(0, pt);
		EventProcesserForSelectCommand ep = new EventProcesserForSelectCommand();
		return ep.selectCommand(canvas);
	}

	/**
	 * 対象を選択する
	 * 
	 * @param range
	 *            対象の選択可能範囲
	 * @param pt
	 *            クラスの位置
	 * @return 対象 nullならキャンセル
	 */
	private Point selectTarget(final int[][] range, final Point pt) {
		final class EventProcesserForSelectTarget extends
				KeyProcessedEventProcesserImpl {

			EventProcesserForSelectTarget() {
				x = pt.x;
				y = pt.y;
				final Field field = game.getField();
				final int fieldY = field.getY();
				final int fieldX = field.getX();
				frange = new int[fieldY][fieldX];
				for (int i = 0; i < fieldY; i++) {
					for (int j = 0; j < fieldX; j++) {
						if (i >= pt.y - range.length / 2
								&& i <= pt.y + range.length / 2
								&& j >= pt.x - range[0].length / 2
								&& j <= pt.x + range[0].length / 2) {
							frange[i][j] = range[i - (pt.y - range.length / 2)][j
									- (pt.x - range[0].length / 2)];
							// if(frange[y][x] == -1){
							// x = j;
							// y = i;
							//							
							// }
						} else {
							frange[i][j] = -1;
						}
					}
				}
				applyPosition();
			}

			/*
			 * (非 Javadoc) selectAster用にprocessEventを受け取る。
			 * 
			 * @see jp.ac.tokyo_ct.asteragy.EventProcesser#processEvent(int,
			 *      int)
			 */
			protected void processKeyEvent(int key) {
				switch (key) {
				case Display.KEY_UP:
					if (y > 0 && frange[y - 1][x] != -1) {
						y--;
					}
					break;
				case Display.KEY_DOWN:
					if (y < game.getField().getY() - 1
							&& frange[y + 1][x] != -1) {
						y++;
					}
					break;
				case Display.KEY_LEFT:
					if (x > 0 && frange[y][x - 1] != -1) {
						x--;
					}
					break;
				case Display.KEY_RIGHT:
					if (x < game.getField().getX() - 1
							&& frange[y][x + 1] != -1) {
						x++;
					}
					break;
				}
				applyPosition();
				System.out
						.println("EventProcesserForSelectTarget.processEvent x = "
								+ x + ", y = " + y);
			}

			protected boolean onCancel() {
				target = null;
				return true;
			}

			public Point getTarget(CanvasControl c) {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				do {
					resetSelected();
					waitForSelect(c);
				} while (target != null && frange[y][x] != 1);
				if (target != null) {
					target = new Point(x, y);
				}
				return target;
			}

			private void applyPosition() {
				canvas.getCursor().setCursor(new Point(x, y), Cursor.CURSOR_1);
			}

			private volatile Point target = new Point(0, 0);

			private int x;

			private int y;

			private int[][] frange;
		}

		canvas.getCommonCommand().setCommand(-1, null);
		EventProcesserForSelectTarget ep = new EventProcesserForSelectTarget();
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		return ep.getTarget(canvas);
	}

	/**
	 * サン専用 クラスを選択する
	 * 
	 * @return 対象 非常にアレだけど都合によりPoint型
	 */
	private Point selectAsterClass(final Point pt) {
		final SunCommand sunCommand = canvas.getSunCommand();
		final class EventProcesserForSelectAsterClass extends
				KeyProcessedEventProcesserImpl {

			protected void processKeyEvent(int key) {
				switch (key) {
				case Display.KEY_UP:
					if (command > 0) {
						command--;
					}else{
						command = 9;
					}
					break;
				case Display.KEY_DOWN:
					if (command < 9) {
						command++;
					}else{
						command = 0;
					}
					break;
				}
				System.out.println("selectAsterClass.processKeyEvent");
				System.out.println("select = " + command);
				sunCommand.setCommand(command, pt);
				// Command.setAsterClass(ac);
			}

			protected boolean onCancel() {
				command = -1;
				return true;
			}

			public int selectAsterClass(CanvasControl c) {
				System.out
						.println("EventProcesserForSelectCommand.selectCommand()");
				do {
					resetSelected();
					waitForSelect(c);
					// コスト足らないのにクラスを選んでる場合のみ受け付けない
				} while (command != -1
						&& AsterClass.classCost[command + 1] > KeyInputPlayer.this
								.getAP());

				return command;
			}

			private volatile int command = 0;
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		sunCommand.setCommand(0, pt);
		EventProcesserForSelectAsterClass ep = new EventProcesserForSelectAsterClass();
		Point r = new Point();
		r.x = ep.selectAsterClass(canvas);
		sunCommand.setCommand(-1, null);
		return r;
	}
}
