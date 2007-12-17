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

	private GameCanvas canvas;

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
			int cmd; // 0 = swap, 1 = ����R�}���h
			while (state < 2) {
				switch (state) {
				case 0: // ����N���X�̑I��
					pt = selectAster();
					state++;
					break;
				case 1: // �X���b�v������R�}���h����I��
					cmd = selectCommand(pt);
					if (cmd == -1) // �L�����Z�����ꂽ
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
	 * �t�B�[���h��ɃJ�[�\�����㉺���E�ɓ������Ď��N���X�����̃A�X�e����I������B
	 * 
	 * @return �I�����ꂽ�A�X�e���̍��W
	 */
	private Point selectAster() {
		final class EventProcesserForSelectAster extends KeyProcessedEventProcesserImpl {
			/**
			 * �R���X�g���N�^
			 * 
			 * @param keyInputPlayer
			 *            ��ƂȂ�KeyInputPlayer
			 */
			EventProcesserForSelectAster(KeyInputPlayer keyInputPlayer) {
				player = keyInputPlayer;
				x = (player.game.getField().getX() - 1) / 2;
				y = 0;
				applyPosition();
			}

			/*
			 * (�� Javadoc) selectAster�p��processEvent���󂯎��B
			 * 
			 * @see jp.ac.tokyo_ct.asteragy.EventProcesser#processEvent(int,
			 *      int)
			 */
			public void processKeyEvent(int key) {
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
					if (y < player.game.getField().getX() - 1) {
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
			 * �L�����Z���s��
			 * @return ���false
			 */
			protected boolean onCancel(){
				return false;
			}

			public Point getPoint() {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				super.waitForSelect();
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
		Point pt = ep.getPoint();
		System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x
				+ ", y = " + pt.y);
		return pt;
	}

	/**
	 * �R�}���h�i�X���b�v�E����j��I������
	 * 
	 * @param pt
	 *            �N���X�̈ʒu
	 * @return �R�}���h��ʁB1�͓���A0�̓X���b�v�A-1�̓L�����Z���B
	 */
	int selectCommand(Point pt) {
		final class EventProcesserForSelectCommand extends KeyProcessedEventProcesserImpl {
			EventProcesserForSelectCommand(Point classPosition){
				pt = classPosition;				
			}
			protected void processKeyEvent(int key) {
				switch (key) {
				case Display.KEY_UP:
					if (command > 0){
						command--;
					}
					break;
				case Display.KEY_DOWN:
					if (command < 1){
						command++;
					}
					break;
				}
			}
			
			protected boolean onCancel(){
				command = -1;
				return true;
			}
			
			public int selectCommand(){
				System.out.println("EventProcesserForSelectCommand.selectCommand()");
				waitForSelect();
				return command;
			}

			private volatile int command = 0;
			private final Point pt;
		}

		System.out.println("KeyInputPlayer.selectCommand()");
		
		Command.setCommand(0, pt);
		game.getCanvas().repaint();
		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return -1;
	}

}
