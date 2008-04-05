package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

/**
 * @author Okubo
 */
class Field implements PaintItem {

	private Aster[][] field;

	private Aster[][] backup;

	private int X, Y;

	private int countAster;

	private final Game game;

	private boolean fieldinit;

	public static int CONNECTION;

	public Field(Game g) {
		super();
		game = g;
	}

	Field clone() {
		Field f = new Field(game);
		f.field = new Aster[Y][X];
		f.backup = new Aster[Y][X];
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; ++j)
				f.field[i][j] = field[i][j];
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; ++j)
				f.backup[i][j] = backup[i][j];
		f.X = X;
		f.Y = Y;
		f.countAster = countAster;
		f.fieldinit = fieldinit;
		return f;
	}

	/**
	 * フィールドのサイズを決める
	 * 
	 * @param x
	 *            フィールドの横マス数
	 * @param y
	 *            フィールドの縦マス数
	 */
	public void setFieldSize(int x, int y) {
		X = x;
		Y = y;
	}

	public boolean isFieldInit() {
		return fieldinit;
	}

	/**
	 * フィールドの初期化
	 * 
	 */
	public void setAster() {
		fieldinit = true;
		field = new Aster[Y][X];
		backup = new Aster[Y][X];
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = new Aster(this);
				// 消滅判定
				while (judge(j, i) == true) {
					field[i][j].setDeleteFlag(true);
					field[i][j].delete(0);
				}
			}
		}
		fieldinit = false;
	}

	/**
	 * ターン開始処理
	 * 
	 * @param player
	 */
	public void beginTurn(Player player) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				// クラス持ちで
				final AsterClass asterClass = field[i][j].getAsterClass();
				if (asterClass != null) {
					// 現在ターン進行中のプレイヤーのユニットなら初期化
					if (asterClass.getPlayer() == player) {
						asterClass.init();
					}
				}
			}
		}
	}

	/**
	 * アステルが3個つながっているかの判定をするだけ
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 * @return 同色アステルが規定個つながっていればtrue
	 */
	public boolean judge(int x, int y) {
		int AsterColor = field[y][x].getColor();

		countAster = 0;
		field[y][x].setJudgeFlag(true);

		if (y > 0)
			judgeMain(x, y - 1, AsterColor);
		if (x > 0)
			judgeMain(x - 1, y, AsterColor);
		if (y < Y - 1)
			judgeMain(x, y + 1, AsterColor);
		if (x < X - 1)
			judgeMain(x + 1, y, AsterColor);

		if (countAster < CONNECTION - 1) {
			countAster = 0;
			removeJudgeFlagAll();
			return false;
		} else {
			countAster = 0;
			removeJudgeFlagAll();
			return true;
		}
	}

	/**
	 * つながっている同色アステルをカウント
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 * @param AsterColor
	 *            判定対象色
	 */
	private void judgeMain(int x, int y, int AsterColor) {

		if (x < 0 || y < 0 || x >= X || y >= Y || countAster == CONNECTION - 1) {
			return;
		}
		if (field[y][x] == null)
			return;
		if (field[y][x].getColor() == AsterColor
				&& field[y][x].getJudgeFlag() == false) {
			countAster++;
			field[y][x].setJudgeFlag(true);

			judgeMain(x, y + 1, AsterColor);
			judgeMain(x + 1, y, AsterColor);
			judgeMain(x, y - 1, AsterColor);
			judgeMain(x - 1, y, AsterColor);
		}
	}

	/**
	 * つながった同色アステル全てにdeleteFlagを立てる
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 * @param AsterColor
	 *            判定対象色
	 */
	public void setDeleteFlagSameColor(int x, int y, int AsterColor) {
		final Aster a = field[y][x];
		if (a.getColor() == AsterColor && a.getDeleteFlag() == false) {
			a.setDeleteFlag(true);
			if (y > 0)
				setDeleteFlagSameColor(x, y - 1, AsterColor);
			if (x > 0)
				setDeleteFlagSameColor(x - 1, y, AsterColor);
			if (y < Y - 1)
				setDeleteFlagSameColor(x, y + 1, AsterColor);
			if (x < X - 1)
				setDeleteFlagSameColor(x + 1, y, AsterColor);
		}
	}

	/**
	 * アステルにdeleteFlagを立てる
	 * 
	 * @param pt
	 *            注目するマスの座標
	 */
	void setDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(true);
	}

	/**
	 * deleteFlagを外す
	 * 
	 * @param pt
	 *            注目するマスの座標
	 */
	void removeDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(false);
	}

	/**
	 * deleteFlagを全て外す
	 * 
	 */
	public void removeDeleteFlagAll() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j].setDeleteFlag(false);
			}
		}
	}

	/**
	 * judgeFlagを全て外す
	 * 
	 */
	public void removeJudgeFlagAll() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] != null)
					field[i][j].setJudgeFlag(false);
			}
		}
	}

	/**
	 * deleteFlagが立っているアステルを全て消す
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 */
	public int delete(int x, int y) {
		return delete(x, y, 0, null);
	}

	/**
	 * deleteFlagが立っているアステルを全て消す
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 * @param count
	 *            消したアステル数をカウント（最初は0を入れる）
	 * @param pt
	 *            前回注目していた座標
	 * @return 消したアステル数
	 */
	private int delete(int x, int y, int count, Point pt) {
		final Aster target = field[y][x];
		final int AsterColor = target.getColor();
		final Point currentPt = new Point(x, y);

		if (target.getDeleteFlag() == true) {
			target.delete(0);
			count++;
			if (y > 0)
				count = delete(x, y - 1, count, currentPt);
			if (x > 0)
				count = delete(x - 1, y, count, currentPt);
			if (y < Y - 1)
				count = delete(x, y + 1, count, currentPt);
			if (x < X - 1)
				count = delete(x + 1, y, count, currentPt);

			// ランダムで決定した色で問題ない場合
			if (judge(x, y) == false) {
				return count;
			}

			// 初回にランダムで決定した色が置けなかった場合、全色試す
			for (int i = 1; i <= Aster.COLOR_MAX; i++) {
				target.setColor(i);
				if (judge(x, y) == false)
					return count;
			}

			// 全色試しても置けないときは、削除前の色に決定
			if (judge(x, y) == true) {
				System.out.println("special_delete:AsterColor=" + AsterColor);
				target.setColor(AsterColor);
				return count;
			}
			/*
			 * // 全色試しても置けない場合、つまり周りを3つ繋がった4色のアステルで囲まれている //
			 * その場合、このマスは最初にランダムで決定した色に戻し、前回のdeleteのマスは別の色に変更 // （4色ゲームの際の特別措置）
			 * if (judge(x, y) == true) { System.out.println("special_delete");
			 * target.setColor(AsterColor); for (int i = 0; i < Aster.COLOR_MAX;
			 * i++) { field[pt.y][pt.x].setColor(i); if (judge(pt.x, pt.y) ==
			 * false) break; } }
			 */
		}
		return count;
	}

	/**
	 * フィールド上の消えるべきアステルを全て消す
	 * 
	 * @return 消したアステル数
	 */
	public int deleteAll() {
		int i, j;
		int count = 0;

		for (i = 0; i < Y; i++) {
			for (j = 0; j < X; j++) {
				if (judge(j, i) == true) {
					setDeleteFlagSameColor(j, i, field[i][j].getColor());
					count += delete(j, i);
				} else if (field[i][j].getDeleteFlag() == true) {
					count += delete(j, i);
				}
			}
		}

		return count;
	}

	/**
	 * 現在のフィールドのバックアップをとる
	 */
	public void backupField() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				backup[i][j] = field[i][j];
			}
		}
	}

	/**
	 * フィールドのバックアップを復元する
	 */
	public void restoreField() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = backup[i][j];
			}
		}
	}

	/**
	 * サン自滅判定
	 * 
	 * @return サンが自滅しそうな状態ならtrue
	 */
	public boolean judgeSelfDestruction() {
		Player player = game.getCurrentPlayer();

		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j].getNumber() == 1
						&& field[i][j].getAsterClass().getPlayer() == player) {
					if (field[i][j].getDeleteFlag() == true) {
						return true;
					}
					if (judge(j, i) == true) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * スワップ
	 * 
	 * @param a
	 *            bと入れ替える
	 * @param b
	 *            aと入れ替える
	 */
	public void swap(Point a, Point b) {
		Aster tmp = field[a.y][a.x];
		field[a.y][a.x] = field[b.y][b.x];
		field[b.y][b.x] = tmp;

		// EffectFieldSwap swap = new EffectFieldSwap(this, a, b);
		// swap.start();
	}

	public Aster[][] getField() {
		return field;
	}

	public Aster getAster(Point pt) {
		return field[pt.y][pt.x];
	}

	public Aster at(Point pt) {
		return field[pt.y][pt.x];
	}

	public Aster at(int y, int x) {
		return field[y][x];
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public Point asterToPoint(Aster a) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == a) {
					return new Point(j, i);
				}
			}
		}
		System.out.println("Field.asterToPoint() - Not found.");
		return null;
	}

	public void onTurnStart(Player p) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				final AsterClass asterClass = field[i][j].getAsterClass();
				if (asterClass != null && asterClass.getPlayer() == p) {
					asterClass.init();
				}
			}
		}
	}

	public Player checkGameOver() {
		boolean p1 = false, p2 = false;
		final Player player1 = game.getPlayer1();
		final Player player2 = game.getPlayer2();
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				final AsterClass ac = field[i][j].getAsterClass();
				if (ac != null && ac.getNumber() == 1) {
					if (ac.getPlayer() == player1)
						p1 = true;
					if (ac.getPlayer() == player2)
						p2 = true;
				}
			}
		}
		if (!p1)
			return player1;
		if (!p2)
			return player2;

		return null;
	}

	// public void fieldBackUp(){
	//
	// }
	// public void restoreField(){
	//
	// }
	/**
	 * フィールド描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	public void paint(Graphics g) {
		Point point = new Point();
		// フィールド，アステル描画
		for (point.y = 0; point.y < Y; point.y++) {
			for (point.x = 0; point.x < X; point.x++) {
				if (field[point.y][point.x] == null)
					continue;
				repaintAsterNoBack(g, point);
			}
		}
	}

	public void repaintField(Graphics g) {
		final CanvasControl canvas = game.getCanvas();
		canvas.getBackImage().paintFieldBack(g);
		paint(g);
		canvas.getCursor().paint(g);
	}

	public void repaintAster(Graphics g, Point point) {
		// TODO 自動生成されたメソッド・スタブ
		if (point == null)
			return;
		if (point.x < 0 || point.x >= X || point.y < 0 || point.y >= Y)
			return;
		final CanvasControl canvas = game.getCanvas();
		synchronized (g) {
			canvas.getBackImage().paintAsterBack(g, point);
			repaintAsterNoBack(g, point);
		}
	}

	public void repaintAsterNoBack(Graphics g, Point point) {
		if (point == null)
			return;
		if (point.x < 0 || point.x >= X || point.y < 0 || point.y >= Y)
			return;
		final CanvasControl canvas = game.getCanvas();
		synchronized (g) {
			Aster aster = field[point.y][point.x];
			this.setOrignAster(g, point);
			aster.getPaint().paint(g);
			canvas.getRange().paint(g, point);
			final Cursor cursor = canvas.getCursor();
			if (cursor.isCursor(point))
				cursor.paint(g);
		}
	}

	public void repaintAsterRect(Graphics g, Point lefttop, Point rightbottom) {
		// TODO 自動生成されたメソッド・スタブ
		final CanvasControl canvas = game.getCanvas();
		if (lefttop == null || rightbottom == null)
			return;
		synchronized (g) {
			canvas.getBackImage().paintAsterBackRect(g, lefttop, rightbottom);
			repaintAsterRectNoBack(g, lefttop, rightbottom);
		}
	}

	public void repaintAsterRectNoBack(Graphics g, Point lefttop,
			Point rightbottom) {
		if (lefttop == null || rightbottom == null)
			return;
		final CanvasControl canvas = game.getCanvas();
		synchronized (g) {
			Point point = new Point();
			final Cursor cursor = canvas.getCursor();
			for (point.y = lefttop.y; point.y < rightbottom.y + 1; point.y++) {
				for (point.x = lefttop.x; point.x < rightbottom.x + 1; point.x++) {
					if (point.x >= 0 && point.x < X && point.y >= 0
							&& point.y < Y)
						repaintAsterNoBack(g, point);
					if (cursor.isCursor(point))
						cursor.paint(g);
				}
			}
		}
	}

	Game getGame() {
		return game;
	}

	public Screen getScreen() {
		return game.getCanvas().getScreen();
	}

	/*
	 * フィールド画像読み込み
	 * 
	 * private void loadField() { // 読込先イメージ fieldimage = null; try { //
	 * リソースから読み込み MediaImage m =
	 * MediaManager.getImage("resource:///fieldimage.jpg"); // メディアの使用開始
	 * m.use(); // 読み込み fieldimage = m.getImage(); } catch (Exception e) { }
	 * fieldimage = Image.createImage(GameCanvas.measure + 1, GameCanvas.measure +
	 * 1); Graphics g = fieldimage.getGraphics(); // 臨時マス
	 * g.setColor(Graphics.getColorOfRGB(255, 243, 236)); g.fillRect(0, 0,
	 * GameCanvas.measure + 1, GameCanvas.measure + 1);
	 * g.setColor(Graphics.getColorOfName(Graphics.BLACK)); g.drawRect(0, 0,
	 * GameCanvas.measure, GameCanvas.measure); g.dispose(); }
	 */

	boolean isYInFieldBound(int y) {
		return 0 <= y && y < getY();
	}

	boolean isXInFieldBound(int x) {
		return 0 <= x && x < getX();
	}

	/**
	 * サンの位置
	 * 
	 * @param p
	 * @return
	 */
	public Point getSunPosition(Player p) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				final AsterClass ac = field[i][j].getAsterClass();
				if (ac != null && ac.getNumber() == 1 && ac.getPlayer() == p)
					return new Point(j, i);
			}
		}
		return null;
	}

	public void setOrginField(Graphics g) {
		g.setOrigin(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin());
	}

	public void setOrignAster(Graphics g, Point aster) {
		g.setOrigin(game.getCanvas().getLeftMargin() + GameCanvas.measure
				* aster.x, game.getCanvas().getTopMargin() + GameCanvas.measure
				* aster.y);
	}

	public void setOrignAster(Graphics g, Point aster, int x, int y) {
		g.setOrigin(game.getCanvas().getLeftMargin() + GameCanvas.measure
				* aster.x + x, game.getCanvas().getTopMargin()
				+ GameCanvas.measure * aster.y + y);
	}

	public void setClipRectField(Graphics g) {
		g.setClip(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin(), GameCanvas.measure * X + 1, GameCanvas.measure
				* Y + 1);
	}

	public void setClipRectAster(Graphics g, Point aster) {
		g.setClip(game.getCanvas().getLeftMargin() + GameCanvas.measure
				* aster.x, game.getCanvas().getTopMargin() + GameCanvas.measure
				* aster.y, GameCanvas.measure, GameCanvas.measure);
	}

	public int getWidth() {
		return X * GameCanvas.measure + 1;
	}

	public int getHeight() {
		return Y * GameCanvas.measure + 1;
	}

}
