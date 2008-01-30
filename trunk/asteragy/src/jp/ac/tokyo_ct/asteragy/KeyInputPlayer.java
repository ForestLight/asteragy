package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class KeyInputPlayer extends Player {

	/**
	 * @param playerName
	 *            �v���C���[�̖��O
	 */
	public KeyInputPlayer(Game game, String playerName) {
		super(game, playerName);
		canvas = game.getCanvas();
	}

	private CanvasControl canvas;

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	public Action getAction() {
		System.out.println("KeyInputPlayer.getAction()");
		try {
			int state = 0;
			Point pt = null;
			int cmd = -1; // 0 = swap, 1 = ����R�}���h
			while (state < 4) {

				switch (state) {
				case 0: // ����N���X�̑I��
					pt = selectAster();

					if (pt == null)
						return null;
					state++;
					break;
				case 1: // �X���b�v������R�}���h����I��
					cmd = selectCommand(pt);
					if (cmd == -1) // �L�����Z�����ꂽ
						state--;
					else
						state++;
					break;
				case 2: // �����W�I��
				{
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					ac.setCommand(cmd);

					System.out.println("�^�[�Q�b�g�I��");

					while (ac.hasNext()) {
						int[][] range = ac.getRange();
						game.getCanvas().getRange().setRange(pt, range);
						Point target = selectTarget(range, pt);
						System.out.println("�^�[�Q�b�g�I��");
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
					game.getCanvas().getRange().setRange(null, null);

					// �T����p
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
					final AsterClass ac = game.getField().getAster(pt)
							.getAsterClass();
					if (cmd == 1) {
						this.addSP(-ac.getCommandCost());
					}
					// game.getField().fieldBackUp();
					System.out.println("���s�J�n");
					ac.execute();
					System.out.println("���s����");
					game.getField().repaintField();

					Player p = game.getField().checkGameOver();
					// �Q�[���I�[�o�[���艼
					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addSP(ac.getCommandCost());
						// }
						// state=0;
						// System.out.println("���R�T������ �s���L�����Z��");
						// break;
						// }
						return null;
					}

					// ���Ŕ���
					System.out.println("�����J�n");
					this.addSP(game.getField().deleteAll());
					System.out.println("��������");
					game.getField().repaintField();

					p = game.getField().checkGameOver();

					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addSP(ac.getCommandCost());
						// }
						// state=0;
						// System.out.println("���R�T������ �s���L�����Z��");
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
	 * �t�B�[���h��ɃJ�[�\�����㉺���E�ɓ������Ď��N���X�����̃A�X�e����I������B
	 * 
	 * @return �I�����ꂽ�A�X�e���̍��W
	 */
	private Point selectAster() {
		final class EventProcesserForSelectAster extends
				KeyProcessedEventProcesserImpl {
			/**
			 * �R���X�g���N�^
			 * 
			 * @param keyInputPlayer
			 *            ��ƂȂ�KeyInputPlayer
			 */
			EventProcesserForSelectAster(KeyInputPlayer keyInputPlayer) {

				player = keyInputPlayer;
				final Field field = player.game.getField();
				x = field.getX() / 2;
				y = field.getY() / 2;
				applyPosition();
			}

			/*
			 * (�� Javadoc) selectAster�p��processEvent���󂯎��B
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
			 * �L�����Z���� �L�����Z���̏ꍇ�^�[���I��
			 */
			protected boolean onCancel() {
				pt = null;
				return true;
			}

			public Point getPoint(CanvasControl c) {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				AsterClass ac;
				do {
					resetSelected();
					waitForSelect(c);
					ac = game.getField().getField()[y][x].getAsterClass();
					System.out
							.println("EventProcesserForSelectAster.getPoint x = "
									+ x + ", y = " + y);
				} while (pt != null
						&& (ac == null || ac.getPlayer() != player || ac
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

			private final KeyInputPlayer player;
		}

		if (canvas == null)
			System.out.println("�ʂ��");
		canvas.getCommonCommand().setCommand(-1, null);
		System.out.println("KeyInputPlayer.selectAster()");
		EventProcesserForSelectAster ep = new EventProcesserForSelectAster(this);
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		Point pt = ep.getPoint(canvas);
		canvas.removeEventProcesser(ep);
		// System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x
		// + ", y = " + pt.y);
		return pt;
	}

	/**
	 * �R�}���h�i�X���b�v�E����j��I������
	 * 
	 * @param pt
	 *            �N���X�̈ʒu
	 * @return �R�}���h��ʁB1�͓���A0�̓X���b�v�A-1�̓L�����Z���B
	 */
	private int selectCommand(Point pt) {
		final class EventProcesserForSelectCommand extends
				KeyProcessedEventProcesserImpl {
			EventProcesserForSelectCommand(Player p, Point classPosition) {
				pt = classPosition;
				player = p;
				ac = game.getField().getAster(pt).getAsterClass();
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
					// SP����Ȃ��̂ɃR�}���h�I��ł�ꍇ�̂ݎ󂯕t���Ȃ�
				} while (command == 1
						&& AsterClassData.commandCost[ac.getNumber() - 1] > player
								.getSP());
				switch (command) {
				case -1:
					System.out.println("selectCommand - �L�����Z��");
					break;
				case 0:
					System.out.println("selectCommand - �X���b�v");
					break;
				case 1:
					System.out.println("selectCommand - ����");
					break;
				}
				return command;
			}

			private final AsterClass ac;

			private volatile int command = 0;

			private final Point pt;

			private final Player player;
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		canvas.getCommonCommand().setCommand(0, pt);
		EventProcesserForSelectCommand ep = new EventProcesserForSelectCommand(
				this, pt);
		return ep.selectCommand(canvas);
	}

	/**
	 * �Ώۂ�I������
	 * 
	 * @param range
	 *            �Ώۂ̑I���\�͈�
	 * @param pt
	 *            �N���X�̈ʒu
	 * @return �Ώ� null�Ȃ�L�����Z��
	 */
	private Point selectTarget(int[][] range, Point pt) {
		final class EventProcesserForSelectTarget extends
				KeyProcessedEventProcesserImpl {

			EventProcesserForSelectTarget(KeyInputPlayer keyInputPlayer,
					Point classPosition, int[][] range) {
				player = keyInputPlayer;
				pt = classPosition;
				x = pt.x;
				y = pt.y;
				final Aster[][] f = game.getField().getField();
				frange = new int[f.length][f[0].length];
				for (int i = 0; i < frange.length; i++) {
					for (int j = 0; j < frange[0].length; j++) {
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
			 * (�� Javadoc) selectAster�p��processEvent���󂯎��B
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
					if (y < player.game.getField().getY() - 1
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
					if (x < player.game.getField().getX() - 1
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

			private Point pt;

			private int[][] frange;

			private final KeyInputPlayer player;
		}

		canvas.getCommonCommand().setCommand(-1, null);
		System.out.println("KeyInputPlayer.selectTarget()");
		EventProcesserForSelectTarget ep = new EventProcesserForSelectTarget(
				this, pt, range);
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		return ep.getTarget(canvas);
	}

	/**
	 * �T����p �N���X��I������
	 * 
	 * @return �Ώ� ���ɃA�������Ǔs���ɂ��Point�^
	 */
	private Point selectAsterClass(Point pt) {
		final class EventProcesserForSelectAsterClass extends
				KeyProcessedEventProcesserImpl {
			EventProcesserForSelectAsterClass(Player p, Point classPosition) {
				pt = classPosition;
				player = p;
			}

			protected void processKeyEvent(int key) {
				switch (key) {
				case Display.KEY_UP:
					if (command > 0) {
						command--;
					}
					break;
				case Display.KEY_DOWN:
					if (command < 9) {
						command++;
					}
					break;
				}
				System.out.println("selectAsterClass.processKeyEvent");
				System.out.println("select = " + command);
				canvas.getSunCommand().setCommand(command, pt);
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
					// �R�X�g����Ȃ��̂ɃN���X��I��ł�ꍇ�̂ݎ󂯕t���Ȃ�
				} while (command != -1
						&& AsterClassData.classCost[command + 1] > player
								.getSP());

				return command;
			}

			private volatile int command = 0;

			private final Point pt;

			private final Player player;
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		canvas.getSunCommand().setCommand(0, pt);
		EventProcesserForSelectAsterClass ep = new EventProcesserForSelectAsterClass(
				this, pt);
		Point r = new Point();
		r.x = ep.selectAsterClass(canvas);
		canvas.getSunCommand().setCommand(-1, null);
		return r;
	}
}
