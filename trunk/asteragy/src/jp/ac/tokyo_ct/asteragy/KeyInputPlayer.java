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
			int cmd = -1; // 0 = swap, 1 = 特殊コマンド
			while (state < 4) {
				switch (state) {
				case 0: // 操作クラスの選択
					pt = selectAster();
					if(pt == null) return null;
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
					
					while(ac.hasNext()){
						int[][] range = ac.getRange();
						Range.setRange(pt,range);
						Point target = selectTarget(range,pt);
						System.out.println("ターゲット選択中");
						System.out.println("target - x = "+pt.x+" y = "+pt.y);
						if(target == null){
							if(ac.moveAstern()){
								state=-1;
								break;
							}
						}
						ac.setPointAndNext(target);
					}
					state++;				
					Range.setRange(null,null);
				}
					break;
					
				case 3:
					System.out.println("実行");
					game.getField().getAster(pt).getAsterClass().execute();
					System.out.println("実行完了");
					
//					 消滅判定
					this.addSP(game.getField().deleteAll());
					
					state=0;
					canvas.repaint();
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
				x = (player.game.getField().getX()) / 2;
				y = (player.game.getField().getY()) / 2;
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
			 * キャンセル可
			 * キャンセルの場合ターン終了
			 */
			protected boolean onCancel() {
				pt = null;
				return true;
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
				} while (pt != null && (ac == null || ac.getPlayer() != player));
				
				//return new Point(x, y);
				if(pt != null){
					pt = new Point(x,y);
				}
				return pt;
			}

			private void applyPosition() {
				canvas.drawCursor(x, y, Cursor.CURSOR_1);
			}
			private Point pt = new Point();
			
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
	//	System.out.println("KeyInputPlayer.selectAster() - x = " + pt.x
	//			+ ", y = " + pt.y);
		return pt;
	}

	/**
	 * コマンド（スワップ・特殊）を選択する
	 * 
	 * @param pt
	 *            クラスの位置
	 * @return コマンド種別。1は特殊、0はスワップ、-1はキャンセル。
	 */
	private int selectCommand(Point pt) {
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

	/**
	 * 対象を選択する
	 * @param range
	 * 	対象の選択可能範囲
	 * @param pt
	 * 　 クラスの位置
	 * @return
	 * 　 対象 nullならキャンセル
	 */
	private Point selectTarget(int[][] range,Point pt){
			final class EventProcesserForSelectTarget extends
			KeyProcessedEventProcesserImpl {

		EventProcesserForSelectTarget(KeyInputPlayer keyInputPlayer,Point classPosition,int[][] range) {
			player = keyInputPlayer;
			pt = classPosition;
			x = pt.x;
			y = pt.y;
			frange = new int[game.getField().getField().length][game.getField().getField()[0].length];
			for(int i = 0;i < frange.length;i++){
				for(int j = 0;j < frange[0].length;j++){
					if(i >= pt.y-range.length/2 && i <= pt.y+range.length/2						
						&& j >= pt.x-range[0].length/2 && j <= pt.x+range[0].length/2){
						frange[i][j] = range[i-(pt.y-range.length/2)][j-(pt.x-range[0].length/2)];
//						if(frange[y][x] == -1){
//							x = j;
//							y = i;
//							
//						}
					}else{
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
				if (y > 0 && frange[y-1][x] != -1) {
					y--;
				}
				break;
			case Display.KEY_DOWN:
				if (y < player.game.getField().getY() - 1 && frange[y+1][x] != -1) {
					y++;
				}
				break;
			case Display.KEY_LEFT:
				if (x > 0 && frange[y][x-1] != -1) {
					x--;
				}
				break;
			case Display.KEY_RIGHT:
				if (x < player.game.getField().getX() - 1 && frange[y][x+1] != -1) {
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
	
		public Point getTarget(GameCanvas c) {
			System.out.println("EventProcesserForSelectAster.getPoint()");
			do{
				resetSelected();
				waitForSelect(c);
			}while(target != null && frange[y][x] != 1);
			if(target != null){
				target = new Point(x,y);
			}
			return target;
		}
	
		private void applyPosition() {
			canvas.drawCursor(x, y, Cursor.CURSOR_1);
		}
		private volatile Point target = new Point(0,0);
	
		private int x;
	
		private int y;
		
		private Point pt;
		
		private int[][] frange;
	
		private final KeyInputPlayer player;
	}

			Command.setCommand(-1, null);
			System.out.println("KeyInputPlayer.selectTarget()");
			EventProcesserForSelectTarget ep = new EventProcesserForSelectTarget(this,pt,range);
			canvas.setEventProcesser(ep);
			System.out.println("canvas.setEventProcesser() after");
			return ep.getTarget(canvas);
	}
}
