package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class KeyInputPlayer extends Player {

	/**
	 * @param playerName
	 *            �v���C���[�̖��O
	 */
	public KeyInputPlayer(Game game, String playerName) {
		super(game, playerName);
		System.out.println("KeyInputPlayer");
	}

	private CanvasControl canvas;
	
	private Point cursorPoint;
	/*
	 * (�� Javadoc)
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
			int cmd = -1; // 0 = swap, 1 = ����R�}���h
			while (state < 4) {

				switch (state) {
				case 0: // ����N���X�̑I��
					pt = selectAster();

					if (pt == null)
						return null;
					cursorPoint = pt;
					state++;
					break;
				case 1: // �X���b�v������R�}���h����I��
				{
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					final Range canvasRange = game.getCanvas().getRange();
					int[][] range = ac.getRange();
					
					canvasRange.setRange(pt, range);
					
					cmd = selectCommand(pt);
					
				//	canvasRange.setRange(null, null);
					
					if (cmd == -1) // �L�����Z�����ꂽ
						state--;
					else
						state++;
					break;
				}
				case 2: // �����W�I��
				{
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					ac.setCommand(cmd);

					System.out.println("�^�[�Q�b�g�I��");
					final Range canvasRange = game.getCanvas().getRange();
					
					while (ac.hasNext()) {
						int[][] range = ac.getRange();
						canvasRange.setRange(pt, range);
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
					canvasRange.setRange(null, null);

					// �T����p
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
					System.out.println("���s�J�n");
					ac.execute();
					System.out.println("���s����");
					field.repaintField();

					Player p = field.checkGameOver();
					// �Q�[���I�[�o�[���艼
					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addAP(ac.getCommandCost());
						// }
						// state=0;
						// System.out.println("���R�T������ �s���L�����Z��");
						// break;
						// }
						return null;
					}

					// ���Ŕ���
					System.out.println("�����J�n");
					this.addAP(field.deleteAll());
					System.out.println("��������");
					field.repaintField();

					p = field.checkGameOver();

					if (p != null) {
						// if(p == game.getCurrentPlayer()){
						// game.getField().restoreField();
						// if (cmd == 1) {
						// this.addAP(ac.getCommandCost());
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
			EventProcesserForSelectAster() {
				final Field field = game.getField();
				x = cursorPoint.x;
				y = cursorPoint.y;
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
			 * �L�����Z���� �L�����Z���̏ꍇ�^�[���I��
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
	 * �R�}���h�i�X���b�v�E����j��I������
	 * 
	 * @param pt
	 *            �N���X�̈ʒu
	 * @return �R�}���h��ʁB1�͓���A0�̓X���b�v�A-1�̓L�����Z���B
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
					// AP����Ȃ��̂ɃR�}���h�I��ł�ꍇ�̂ݎ󂯕t���Ȃ�
				} while (command == 1
						&& AsterClass.commandCost[ac.getNumber() - 1] > KeyInputPlayer.this
								.getAP());
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
		}

		System.out.println("KeyInputPlayer.selectCommand()");

		canvas.getCommonCommand().setCommand(0, pt);
		EventProcesserForSelectCommand ep = new EventProcesserForSelectCommand();
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
	 * �T����p �N���X��I������
	 * 
	 * @return �Ώ� ���ɃA�������Ǔs���ɂ��Point�^
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
					// �R�X�g����Ȃ��̂ɃN���X��I��ł�ꍇ�̂ݎ󂯕t���Ȃ�
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
