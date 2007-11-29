package jp.ac.tokyo_ct.asteragy;

/**
 * @author Okubo
 */
class Field {

	private Aster[][] field;

	private int X, Y;

	private int countAster;

	private final Game game;

	public Field(Game g) {
		super();
		game = g;
	}

	/**
	 * 指示された行動を行う
	 * 
	 * @param a
	 * @return
	 */
	public boolean act(Action a) {
		// 行動を起こす
		// ...

		GameCanvas c = game.getCanvas();
		c.repaint();
		return false;
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

	/**
	 * フィールドの初期化
	 * 
	 */
	public void setAster() {
		field = new Aster[Y][X];
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
	}

	/**
	 * アステルが3個つながっているかの判定をするだけ
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 * @return true 同色アステルが3個つながっている
	 * @return false つながった同色アステルが3個未満
	 */
	public boolean judge(int x, int y) {
		int AsterColor = field[y][x].getColor();

		countAster = 0;

		judgeMain(x, y - 1, 1, AsterColor);
		judgeMain(x - 1, y, 2, AsterColor);
		judgeMain(x, y + 1, 3, AsterColor);
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
			 * 現在注目している座標を後戻りさせないように再帰 back 1 下 （以前に注目していた座標のある方向） 2 右 3 上 4 左
			 * 
			 * つながった同色アステルを3個見つけるだけなら判定する位置は被らないよね…?
			 */
			if (back != 1)
				judgeMain(x, y + 1, 3, AsterColor);
			if (back != 2)
				judgeMain(x + 1, y, 4, AsterColor);
			if (back != 3)
				judgeMain(x, y - 1, 1, AsterColor);
			if (back != 4)
				judgeMain(x - 1, y, 2, AsterColor);

			// かなりめんどくさいことしてる気がする 動くのかも心配 修正希望
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
	public void setDeleteFlag(int x, int y, int AsterColor) {
		if (field[y][x].getColor() == AsterColor
				&& field[y][x].getDeleteFlag() == false) {
			field[y][x].setDeleteFlag(true);
			setDeleteFlag(x, y - 1, AsterColor);
			setDeleteFlag(x - 1, y, AsterColor);
			setDeleteFlag(x, y + 1, AsterColor);
			setDeleteFlag(x + 1, y, AsterColor);
		}
	}

	/**
	 * フラグが立ってるアステルをdeleteして、再び削除判定
	 * 
	 * @param x
	 *            注目するマスのx座標
	 * @param y
	 *            注目するマスのy座標
	 */
	public void delete(int x, int y) {
		int AsterColor = field[y][x].getColor();

		if (field[y][x].getDeleteFlag() == true) {
			field[y][x].delete(0);
			delete(x, y - 1);
			delete(x - 1, y);
			delete(x, y + 1);
			delete(x + 1, y);

			for (int i = 1; i <= 4; i++) {
				if (judge(x, y) == false) {
					return;
				}
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(i);
			}

			if (judge(x, y) == true) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(AsterColor);
			}
		}
	}

	/**
	 * swap
	 * 
	 */
	public void swap(Point a,Point b) {
		Aster tmp = field[a.y][a.x];
		field[a.y][a.x] = field[b.y][b.x];
		field[b.y][b.x] = tmp;
	}

	public Aster[][] getField() {
		return field;
	}

	public Aster getAster(Point pt) {
		return field[pt.y][pt.x];
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public Point asterToPoint(Aster a) {
		Point point;

		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == a) {
					point = new Point(j, i);
					return point;
				}
			}
		}
		return null; // 見付からなかったとき
	}
}
