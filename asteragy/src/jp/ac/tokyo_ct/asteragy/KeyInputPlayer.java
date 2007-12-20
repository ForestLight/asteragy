package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class KeyInputPlayer extends Player {

	/**
	 * @param playerName
	 *            プレイヤーの名前
	 */
	public KeyInputPlayer(Game game, String playerName) {
		super(game, playerName);
		canvas = game.getCanvas();
	}

	private GameCanvas canvas;

	/*
	 * (非 Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	public Action getAction() {
		System.out.println("KeyInputPlayer.getAction()");
		try {
			int state = 0;
			Point pt = null;
			int cmd; // 0 = swap, 1 = 特殊コマンド
			while (state < 2) {
				switch (state) {
				case 0: // 操作クラスの選択
					pt = selectAster();
					state++;
					break;
				case 1: // スワップか特殊コマンドかを選択
					cmd = selectCommand(pt);
					if (cmd == -1) // キャンセルされた
						state--;
					else
						state++;
					break;
				case 2: {
					Aster a = game.getField().getAster(pt);
				}
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
			EventProcesserForSelectAster(KeyInputPlayer keyInputPlayer) {
				player = keyInputPlayer;
				x = (player.game.getField().getX() - 1) / 2;
				y = 0;
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
					if (y < player.game.getField().getY() - 1) {
						y++;
					}
					break;
				case Display.KEY_LEFT:
					if (x > 0) {
						x--;
					}
					break;
				case Display.KEY_RIGHT:
					if (x < player.game.getField().getX() - 1) {
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
			 * キャンセル不可
			 * 
			 * @return 常にfalse
			 */
			protected boolean onCancel() {
				return false;
			}

			public Point getPoint(GameCanvas c) {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				AsterClass ac;
				do {
					resetSelected();
					waitForSelect(c);
					ac = game.getField().getField()[y][x].getAsterClass();
					System.out
					.println("EventProcesserForSelectAster.getPoint x = "
							+ x + ", y = " + y);
				} while (ac == null || ac.getPlayer() != player);
				return new Point(x, y);
			}

			private void applyPosition() {
				canvas.drawCursor(x, y, Cursor.CURSOR_1);
			}

			private int x;

			private int y;

			private final KeyInputPlayer player;
		}

		Command.setCommand(-1, null);
		System.out.println("KeyInputPlayer.selectAster()");
		EventProcesserForSelectAster ep = new EventProcesserForSelectAster(this);
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		Point pt = ep.getPoint(canvas);
		canvas.removeEventProcesser(ep);
		System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x
				+ ", y = " + pt.y);
		return pt;
	}

	/**
	 * コマンド（スワップ・特殊）を選択する
	 * 
	 * @param pt
	 *            クラスの位置
	 * @return コマンド種別。1は特殊、0はスワップ、-1はキャンセル。
	 */
	int selectCommand(Point pt) {
		final class EventProcesserForSelectCommand extends
				KeyProcessedEventProcesserImpl {
			EventProcesserForSelectCommand(Player p, Point classPosition) {
				pt = classPosition;
				player = p;
			}

			protected void processKeyEvent(int key) {
				System.out.println("selectCommand.processKeyEvent");
				switch (key) {
				case Display.KEY_UP:
					if (command > 0) {
						command--;
					}
					break;
				case Display.KEY_DOWN:
					if (command < 1) {
						command++;
					}
					break;
				}
				System.out.println("selectCommand.processKeyEvent");
				Command.setCommand(command, pt);
				player.game.getCanvas().repaint();
			}

			protected boolean onCancel() {
				command = -1;
				return true;
			}

			public int selectCommand(GameCanvas c) {
				System.out
						.println("EventProcesserForSelectCommand.selectCommand()");
				waitForSelect(c);
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

			private volatile int command = 0;

			private final Point pt;

			private final Player player;
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		Command.setCommand(0, pt);
		game.getCanvas().repaint();
		EventProcesserForSelectCommand ep = new EventProcesserForSelectCommand(
				this, pt);
		return ep.selectCommand(canvas);
	}

}
