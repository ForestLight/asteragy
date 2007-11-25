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
			int cmd; //0 = swap, 1 = ����R�}���h
			while (state < 2) {
				switch (state) {
				case 0: //����N���X�̑I��
					pt = selectAster();
					state++;
					break;
				case 1: //�X���b�v������R�}���h����I��
					cmd = selectCommand();
					if (cmd == -1) //�L�����Z�����ꂽ
						state--;
					else
						state++;
					break;
				case 2:
					{
						Aster a = game.getField().getAster(pt);
					}
				}
			}				
			return null;
		} finally {
			canvas.resetEventProcesser();
		}
	}
	
	private Point selectAster() {
		final class EventProcesserForSelectAster implements EventProcesser {
			/* (�� Javadoc)
			 * @see jp.ac.tokyo_ct.asteragy.EventProcesser#processEvent(int, int)
			 */
			public void processEvent(int type, int param) {
				if (type != Display.KEY_PRESSED_EVENT) {
					return;
				}
				if (isSelected) {
					return;
				}

				System.out.println("selectAster()");
				switch (param) {
				case Display.KEY_UP:
					y--;
					if (y < 0) {
						y = 0;
					}
					break;
				case Display.KEY_DOWN:
					y++;
					break;
				case Display.KEY_LEFT:
					x--;
					if (x < 0) {
						x = 0;
					}
					break;
				case Display.KEY_RIGHT:
					x++;
					break;
				case Display.KEY_SELECT:
					isSelected = true;
					return;					
				}
				canvas.drawCursor(x, y, GameCanvas.CURSOR_1);
				System.out.println("EventProcesserForSelectAster.processEvent x = " + x + ", y = " + y);				
			}
			
			public Point getPoint() {
				System.out.println("EventProcesserForSelectAster.getPoint()");
				while (!isSelected) {
					try {
						Thread.sleep(10);
					} catch(InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				return new Point(x, y);
			}
			//TODO: (0, 0)�ł͂Ȃ��A�����ƓK���ȏꏊ�ɂ��ׂ�
			private int x = 0;
			private int y = 0;
			private volatile boolean isSelected = false;
		}
		
		System.out.println("KeyInputPlayer.selectAster()");
		EventProcesserForSelectAster ep = new EventProcesserForSelectAster();
		canvas.setEventProcesser(ep);
		System.out.println("canvas.setEventProcesser() after");
		Point pt = ep.getPoint();
		System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x + ", y = " + pt.y);
		return pt;
	}
	
	int selectCommand() {
		return -1;
	}
	
}
