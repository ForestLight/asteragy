package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class KeyInputPlayer extends Player implements EventProcesser {
	/**
	 * @param playerName
	 *            プレイヤーの名前
	 */
	KeyInputPlayer(Game game, String playerName) {
		super(game, playerName);
		field = game.getField();
		canvas = game.getCanvas();
		canvasRange = canvas.range;
		frange = new int[game.getField().Y][game.getField().X];
	}

	private final CanvasControl canvas;

	private final Field field;

	private Action action;

	private int phase;

	private volatile boolean selected;

	private final Range canvasRange;

	private AsterClass ac;

	private final int[][] frange;

	private Point pt; // selectAster用

	private Point ptAster;

	synchronized Action getAction() {
		System.out.println("KeyInputPlayer.getAction()");
		phase = 0;
		selected = false;
		pt = game.getField().getSunPosition(this);
		if (pt == null)
			return null;
		canvas.cursor.setCursor(pt, 1);
		action = new Action();
		canvas.eventProcesser = this;
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			// Thread.currentThread().interrupt();
			return null;
		} finally {
			canvas.eventProcesser = null;
		}
		canvasRange.setRange(null, null);
		canvas.repaint();
		return action;
	}

	synchronized public void processEvent(int type, int param) {
		if (selected || type != Display.KEY_PRESSED_EVENT) {
			return;
		}
		System.out.println("phase = " + phase + ", param = " + param);
		switch (param) {
		case Display.KEY_0:
		case Display.KEY_2:
		case Display.KEY_IAPP:
		case Display.KEY_CLEAR:
			System.out.println("Cancel " + phase);
			switch (phase--) {
			case 0:
				action = null;
				notifyAll();
				break;
			case 1:
				pt = ptAster;
				ptAster = null;
				ac = null;
				canvasRange.setRange(null, null);
				break;
			case 2: // ここbreakなし
			case 3:
				canvas.sunCommand.setCommand(-1, null);
				pt = ptAster.clone();
				ac.moveAstern();
				break;
			}
			break;
		case Display.KEY_SELECT:
			System.out.println("Select " + phase);
			if (phase == 2 || phase == 3) {
				if (frange[pt.y][pt.x] != 1) {
					return;
				}
				if (ac instanceof SunClass && action.commandType == 1) {
					if (phase == 2) {
						canvas.sunCommand.setCommand(0, ptAster);
					} else {
						canvas.sunCommand.setCommand(-1, null);
						notifyAll();
						return;
					}
				}
				action.args[(phase - 2) * 2] = pt.x;
				action.args[(phase - 2) * 2 + 1] = pt.y;
				ac.setPointAndNext(pt.clone());
			}
			switch (phase) {
			case 0: // アステルが選択された
				final Aster a = field.at(pt);
				ac = a.getAsterClass();
				if (ac == null || ac.getPlayer() != this
						|| ac.getActionCount() == 0) {
					return;
				}
				action.aster = a;
				canvasRange.setRange(pt, ac.getRange());
				ptAster = pt.clone();
				break;
			case 1: // コマンドが選択された
				ac.setCommand(action.commandType);
				canvas.commonCommand.setCommand(-1, null);
				action.args = new int[4];
			// ここbreakなし
			case 2:
			case 3:
				if (!(ac instanceof SunClass && action.commandType == 1)
						&& !ac.hasNext()) {
					notifyAll();
					return;
				}
				break;
			}
			phase++;
			break;
		default:
			switch (phase) {
			case 0:
				selectAster(param);
				return;
			case 1:
				selectCommand(param);
				return;
			case 2:
				selectTarget(param);
				return;
			case 3:
				if (ac instanceof SunClass && action.commandType == 1) {
					selectSunTarget(param);
				} else {
					selectTarget(param);
				}
				return;
			}
			return;
		}
		// この下はphaseが増えたとき減ったとき両方で行う処理。
		switch (phase) {
		case 0:
			canvas.commonCommand.setCommand(-1, null);
			break;
		case 1: {
			action.commandType = 0;
			ac.setCommand(action.commandType);
			final Range canvasRange = canvas.range;
			int[][] range = ac.getRange();
			canvasRange.setRange(pt, range);
			canvas.commonCommand.setCommand(0, pt);
			break;
		}
		case 2:
		case 3:
			ac.setCommand(action.commandType);
			final int[][] range = ac.getRange();
			System.out.println("processEvent phase 2/3");
			canvasRange.setRange(ptAster, range);
			final int top = ptAster.y - range.length / 2;
			final int bottom = ptAster.y + range.length / 2;
			final int left = ptAster.x - range[0].length / 2;
			final int right = ptAster.x + range[0].length / 2;
			for (int i = 0; i < field.Y; i++) {
				for (int j = 0; j < field.X; j++) {
					if (top <= i && i <= bottom && left <= j && j <= right) {
						frange[i][j] = range[i - top][j - left];
					} else {
						frange[i][j] = -1;
					}
				}
			}
			applyPosition();
			break;
		}
		System.out.println("repaint in processEvent");
		canvas.repaint();
	}

	private void selectAster(int key) {
		switch (key) {
		case Display.KEY_UP:
			if (pt.y > 0) {
				pt.y--;
				break;
			}
			return;
		case Display.KEY_DOWN:
			if (pt.y < field.Y - 1) {
				pt.y++;
				break;
			}
			return;
		case Display.KEY_LEFT:
			if (pt.x > 0) {
				pt.x--;
				break;
			}
			return;
		case Display.KEY_RIGHT:
			if (pt.x < field.X - 1) {
				pt.x++;
				break;
			}
			return;
		default:
			return; // 下のrepaintを回避
		}
		applyPosition();
	}

	private void selectCommand(int key) {
		switch (key) {
		case Display.KEY_UP:
		case Display.KEY_DOWN:
			action.commandType++;
			action.commandType &= 1;
			break;
		default:
			return; // 下のrepaintを回避
		}
		final CommonCommand cc = canvas.commonCommand;
		cc.setAsterClass(ac);
		cc.setCommand(action.commandType, pt);
		ac.setCommand(action.commandType);
		final Range canvasRange = canvas.range;
		int[][] range = ac.getRange();
		canvasRange.setRange(pt, range);
		canvas.repaint();
	}

	private void selectTarget(int key) {
		System.out.println("st: x = " + pt.x + " y = " + pt.y);
		switch (key) {
		case Display.KEY_UP:
			if (pt.y > 0 && frange[pt.y - 1][pt.x] != -1) {
				pt.y--;
				break;
			}
			return;
		case Display.KEY_DOWN:
			if (pt.y < field.Y - 1 && frange[pt.y + 1][pt.x] != -1) {
				pt.y++;
				break;
			}
			return;
		case Display.KEY_LEFT:
			if (pt.x > 0 && frange[pt.y][pt.x - 1] != -1) {
				pt.x--;
				break;
			}
			return;
		case Display.KEY_RIGHT:
			if (pt.x < field.X - 1 && frange[pt.y][pt.x + 1] != -1) {
				pt.x++;
				break;
			}
			return;
		default:
			return; // 下のrepaintを回避
		}
		applyPosition();
	}

	private void selectSunTarget(int key) {
		switch (key) {
		case Display.KEY_UP:
			if (action.args[2] > 0) {
				action.args[2]--;
			} else {
				action.args[2] = 9;
			}
			break;
		case Display.KEY_DOWN:
			if (action.args[2] < 9) {
				action.args[2]++;
			} else {
				action.args[2] = 0;
			}
			break;
		}
		System.out.println("KeyInputPlayer.selectSunTarget: select = "
				+ action.args[2]);
		canvas.sunCommand.setCommand(action.args[2], ptAster);
		canvas.repaint();
	}

	private void applyPosition() {
		canvas.cursor.setCursor(pt, Cursor.CURSOR_1);
	}

}
