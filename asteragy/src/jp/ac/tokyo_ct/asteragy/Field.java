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

	private static final int[] table = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };

	private final Game game;

	private boolean fieldinit;

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
	 * @return true 同色アステルが3個つながっている false 3個未満
	 */
	public boolean judge(int x, int y) {
		int AsterColor = field[y][x].getColor();

		countAster = 0;

		if (y > 0)
			judgeMain(x, y - 1, 1, AsterColor);
		if (x > 0)
			judgeMain(x - 1, y, 2, AsterColor);
		if (y < Y - 1)
			judgeMain(x, y + 1, 3, AsterColor);
		if (x < X - 1)
			judgeMain(x + 1, y, 4, AsterColor);

		if (countAster < 3) {
			countAster = 0;
			return false;
		} else {
			countAster = 0;
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
	 * @param back
	 *            前回のjudgeMainで注目していた座標の方向ナンバー
	 * @param AsterColor
	 *            判定対象色
	 */
	private void judgeMain(int x, int y, int back, int AsterColor) {

		if (x < 0 || y < 0 || x >= X || y >= Y || countAster == 3) {
			return;
		}
		if (field[y][x] == null)
			return;
		if (field[y][x].getColor() == AsterColor) {
			countAster++;
			/*
			 * 現在注目している座標を後戻りさせないように再帰 back（以前に注目していた座標のある方向）1 下 2 右 3 上 4 左
			 */
			if (back != 1 && y < Y - 1)
				judgeMain(x, y + 1, 3, AsterColor);
			if (back != 2 && x < X - 1)
				judgeMain(x + 1, y, 4, AsterColor);
			if (back != 3 && y > 0)
				judgeMain(x, y - 1, 1, AsterColor);
			if (back != 4 && x > 0)
				judgeMain(x - 1, y, 2, AsterColor);

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
	 * deleteFlagが立っているアステルを全て消す（カウントを行わない）
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 */
	public int delete(int x, int y) {
		return delete(x, y, 0);
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
	 * @return 消したアステル数
	 */
	private int delete(int x, int y, int count) {
		final Aster target = field[y][x];
		int AsterColor = target.getColor();

		if (target.getDeleteFlag() == true) {
			target.delete(0);
			count++;
			if (y > 0)
				count = delete(x, y - 1, count);
			if (x > 0)
				count = delete(x - 1, y, count);
			if (y < Y - 1)
				count = delete(x, y + 1, count);
			if (x < X - 1)
				count = delete(x + 1, y, count);

			// ランダムで決定した色で問題ない場合
			if (judge(x, y) == false) {
				return count;
			}

			// 初回にランダムで決定した色が置けなかった場合、5色試す
			int t = Game.random.nextInt(5);

			for (int i = 1; i <= 5; i++, t++) {
				target.setColor(table[t]);
				if (judge(x, y) == false)
					return count;
			}
			// 5色試しても置けない場合、delete前の色に決定する
			if (judge(x, y) == true) {
				target.setColor(AsterColor);
			}
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
					count += delete(j, i, 0);
				} else if (field[i][j].getDeleteFlag() == true) {
					count += delete(j, i, 0);
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
		// 原点座標位置計算
		g.setOrigin((240 - X * GameCanvas.measure) / 2, (240 - Y
				* GameCanvas.measure) / 2);
		// フィールド，アステル描画
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == null)
					continue;
				paint(g, i, j);
			}
		}
	}

	private void paint(Graphics g, int i, int j) {
		// フィールド
		// g.drawImage(fieldimage, i * GameCanvas.measure, j
		// * GameCanvas.measure);

		// アステル
		// プレイヤー2のユニットは反転
		final Aster a = field[i][j];
		final AsterClass asterClass = a.getAsterClass();
		if (asterClass != null && asterClass.getPlayer() == game.getPlayer2()) {
			g.setFlipMode(Graphics.FLIP_VERTICAL);
		} else {
			g.setFlipMode(Graphics.FLIP_NONE);
		}

		final CanvasControl canvas = game.getCanvas();
		g.setOrigin(canvas.getLeftMargin() + j * GameCanvas.measure, canvas
				.getTopMargin()
				+ i * GameCanvas.measure);

		a.getPaint().paint(g);

		// レンジ
		canvas.getRange().paint(g, j, i);
	}

	public void repaintField() {
		final CanvasControl canvas = game.getCanvas();
		Graphics g = canvas.getGraphics();
		g.lock();
		canvas.getBackImage().paintFieldBack(g);
		paint(g);
		g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
		canvas.getCursor().paint(g);
		g.unlock(false);
	}

	public void repaintAster(Point point) {
		if (point == null)
			return;
		final CanvasControl canvas = game.getCanvas();
		Graphics g = canvas.getGraphics();
		synchronized (g) {
			g.lock();
			canvas.getBackImage().paintAsterBack(g, point);
			paint(g, point.y, point.x);
			g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
			final Cursor cursor = canvas.getCursor();
			if (cursor.isCursor(point))
				cursor.paint(g);
			g.unlock(false);
		}
	}

	Game getGame() {
		return game;
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

}
