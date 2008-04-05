package jp.ac.tokyo_ct.asteragy;
//現在カオスってるので動かさないで．見ないで＞＜
public class AIPlayer extends Player {

	public AIPlayer(Game game, String playerName) {
		super(game, playerName);
		System.out.println("AIPlayer");
		
		backup = new Field(game);
		backup.setFieldSize(game.getField().getX(),game.getField().getY());
		backup.setAster();
		
		final Field f = game.getField();
		colorBackUp = new int[f.getY()][f.getX()];
		acBackUp = new AsterClass[f.getY()][f.getX()];
	}

	private CanvasControl canvas;

	private Aster[] myUnit;
	
	private int pNum;
	
	/**
	 * 試行回数
	 */
	private final static int TRIAL = 1; 
	
	private final static int WAIT = 1000;
	
	private Field backup;
	
//	private Point[] pt = new Point[TRIAL];
	
	private Point pt; //操作中のユニット位置
	
	private int[] cmd = new int[TRIAL]; //選択したコマンド
	
	private Point[][] target = new Point[2][TRIAL]; //選択したターゲット
	
	private int[] ap = new int[2];
	
	private int eMax; //評価値の最大
	
	private int maxNum; //何回目の試行でeMaxが更新されたか

	public Action getAction() {
		System.out.println("\n\nAIplayer.getAction()");
		if (canvas == null) {
			canvas = game.getCanvas();
		}
		if (game.getCurrentPlayer() == game.getPlayer1()) {
			pNum = 0;
		}else{
			pNum = 1;
		}
		try {
			int state = 0;
			pt = null;
			int t = 0;
			while (state < 4) {
				switch (state) {
				case 0: // 操作クラスの選択
					getMyUnit();
					System.out.println("AIPlayer state0");
					pt = selectAster();
					t=0;
					eMax = -1;

					if (pt == null)
						return null;
					state++;
					break;
				case 1: // スワップか特殊コマンドかを選択
					System.out.println("AIPlayer state1");
					//fieldClone(backup,game.getField()); //フィールドをコピー
					backUpField();
					Effect.setEffect(false);//エフェクト非表示
					cmd[t] = selectCommand(pt);
					if (cmd[t] == -1) // キャンセルされた
						state--;
					else
						state++;
					break;
				case 2: // レンジ選択
				{
					System.out.println("AIPlayer state2");
					int i = 0;
					Aster a = game.getField().getAster(pt);
					AsterClass ac = a.getAsterClass();
					ac.setCommand(cmd[t]);
					target[0][t] = null;
					target[1][t] = null;

					System.out.println("ターゲット選択");

					while (ac.hasNext()) {
						int[][] range = ac.getRange();
						target[i][t] = selectTarget(range, pt);
						System.out.println("ターゲット選択中");
						System.out.println("target - x = " + pt.x + " y = "
								+ pt.y);
						if (target[i][t] == null) {
							if (ac.moveAstern()) {
								state = -1;
								cmd[t] = 0;
								break;
							}
						}
						ac.setPointAndNext(target[i][t]);
						i++;
					}

					// サン専用
					if (ac.getNumber() == 1 && cmd[t] == 1) {
						Point acs = selectAsterClass(pt);
						if (acs.x == -1) {
							ac.moveAstern();
							state = -1;
						} else {
							ac.setPointAndNext(acs);
						}
						target[i][t] = acs;
					}

					state++;
				}
					break;

				case 3:
					System.out.println("AIPlayer state3");
					ap[0] = game.getPlayer1().getAP();
					ap[1] = game.getPlayer2().getAP();
					Field field = game.getField();
					final AsterClass ac = field.getAster(pt).getAsterClass();
					if (cmd[t] == 1) {
						if(ac.getNumber() == 1){
							ap[pNum] -= AsterClass.classCost[target[1][t].x + 1];
						}else{
							ap[pNum] -= ac.getCommandCost();
						}
					}
					ac.execute();

					// 消滅判定
					ap[pNum] += field.deleteAll();

					int ev = evaluation(field);
					if(ev > eMax){
						ev = eMax;
						maxNum = t;
					}
					t++;
					//fieldClone(field,backup); //フィールド復元
					restoreField();
					if(t < TRIAL){
						System.out.println("->state1");
						state = 1;
						System.out.println("t = "+t);
					}else{
						System.out.println("execute");
						Effect.setEffect(true);
						if(execute())return null;
						state = 0;
					}
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
	//	canvas.getCursor().setCursor(pt, Cursor.CURSOR_1);
//		try {
//			Thread.sleep(1000);
//		} catch (Exception e) {
//		}
		return pt;
	}

	private int selectCommand(Point pt) {
		final AsterClass ac = game.getField().getAster(pt).getAsterClass();
		System.out.println("AIPlayer selectCommand()");
		if (AsterClass.commandCost[ac.getNumber() - 1] > this.getAP()) {
			return 0;
		} else {
			int cmd = Game.random.nextInt(2);
			//return cmd;
			return 0;

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
		//p = targetlist[0];

		return p;
	}

	private Point selectAsterClass(Point pt) {
		int cmdMax = 0;

		for (int i = 0; i <= 9; i++) {
			if (AsterClass.classCost[i + 1] > this.getAP())
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
	private int evaluation(Field field) {
		int p = 0;
		final Field field2 = field;
		final int y = field2.getY();
		final int x = field2.getX();
		final Aster[][] f = field2.getField();

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				final AsterClass ac = f[i][j].getAsterClass();
				if (ac != null) {
					if (ac.getPlayer() == game.getCurrentPlayer()) {
						p += AsterClass.classCost[ac.getNumber() - 1]*1.5;
						if (ac.getNumber() == 1) {
							p += 500;
						}
					} else {
						p -= AsterClass.classCost[ac.getNumber() - 1]*1.5;
						if (ac.getNumber() == 1) {
							p -= 500;
						}
					}
				}
			}
		}

		if(pNum == 0){
			p += ap[0];
			p -= ap[1];
		}else{
			p += ap[1];
			p -= ap[0];
		}

		System.out.println("AIPlayer.evaluation return" + p);
		return p;
	}
	private void fieldClone(Field f1,Field f2){
//		Field f = new Field(game);
//		f.setFieldSize(field.getX(), field.getY());
//		f.setAster();
		//System.out.println("clone");
		for(int i = 0;i < f1.getY();i++){
			for(int j = 0;j < f1.getX();j++){

				f1.getField()[i][j] = f2.getField()[i][j].clone();
			}
		}
	}
	private void backUpField(){
		final Field f = game.getField();

		for(int i = 0;i < f.getY();i++){
			for(int j = 0;j < f.getX();j++){
				final Aster a = f.getField()[i][j];
				colorBackUp[i][j] = a.getColor();
				a.setNum(i*f.getX()+j);
				final AsterClass ac = a.getAsterClass();
				if(ac == null){
				//	acBackUp[i][j] = null;
				}else{
				//	acBackUp[i][j] = ac.clone();
				}
			}
		}
	}
	private void restoreField(){
		final Field f = game.getField();
		
		for(int i = 0;i < f.getY();i++){
			for(int j = 0;j < f.getX();j++){
				Aster a = f.getField()[i][j];
				int n = a.getNum();
				while((i * f.getX() + j) != n){//アステルの順番が入れ替わってた場合
					System.out.println("swap-");
					f.swap(new Point(j,i),new Point(n%f.getX(),n/f.getX()));
					a = f.getField()[i][j];
					n = a.getNum();
				}
				f.getField()[i][j].setColor(colorBackUp[i][j]);
				if(acBackUp[i][j] == null){
					System.out.println("setnull"+i+","+j);
				//	f.getField()[i][j].setAsterClass(null);
				}else{
			//		f.getField()[i][j].setAsterClass(acBackUp[i][j].clone());
				}
				f.getField()[i][j].init();
			}
		}
	}
	private int[][] colorBackUp;
	private AsterClass[][] acBackUp;
	/**
	 * 一番良さそうな行動を実行
	 *
	 */
	private boolean execute(){
		final Field field = game.getField();
		final AsterClass ac = field.getAster(pt).getAsterClass();
		final Range canvasRange = game.getCanvas().getRange();
		int[][] range = ac.getRange();
		
		canvas.getCursor().setCursor(pt, Cursor.CURSOR_1);
		try {
			Thread.sleep(WAIT);
		} catch (Exception e) {
		}
		canvasRange.setRange(pt, range);
		canvas.getCommonCommand().setCommand(cmd[maxNum], pt);
		canvas.getCommonCommand().setAsterClass(ac);
		try {
			Thread.sleep(WAIT);
		} catch (Exception e) {
		}

		canvas.getCommonCommand().setCommand(-1, null);
		int i = 0;
		while (i < 2 && target[i][maxNum] != null) {
			range = ac.getRange();
			canvasRange.setRange(pt, range);
			ac.setPointAndNext(target[i][maxNum]);
			
			canvas.getCursor().setCursor(target[i][maxNum], Cursor.CURSOR_1);
			try {
				Thread.sleep(WAIT);
			} catch (Exception e) {
			}
			i++;
		}
		canvasRange.setRange(null, null);
		
		if (cmd[maxNum] == 1) {
			this.addAP(-ac.getCommandCost());
		}
		System.out.println("実行開始");
		ac.execute();
		System.out.println("実行完了");
//				field.repaintField();

		Player p = field.checkGameOver();
		// ゲームオーバー判定
		if (p != null) {
			return true;
		}

		// 消滅判定
		System.out.println("消去開始");
		this.addAP(field.deleteAll());
		System.out.println("消去完了");

		p = field.checkGameOver();
		if (p != null) {
			return true;
		}

		return false;
	}
}


