package jp.ac.tokyo_ct.asteragy;

import java.util.Random;

/**
 * @author Okubo
 */
class Field {

	private Aster[][] field;

	private int X, Y;

	private int countAster;

	private int[] table = { 1, 2, 3, 4, 1, 2, 3, 4 };

	private static Random r = new Random(System.currentTimeMillis());

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
	 * ターン開始処理
	 * 
	 * @param player
	 */
	public void beginTurn(Player player) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				// クラス持ちで
				if (field[i][j].getAsterClass() != null) {
					// 現在ターン進行中のプレイヤーのユニットなら初期化
					if (field[i][j].getAsterClass().getPlayer() == player) {
						field[i][j].getAsterClass().init();
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

		if(y > 0)
			judgeMain(x, y - 1, 1, AsterColor);
		if(x > 0)
			judgeMain(x - 1, y, 2, AsterColor);
		if(y < Y - 1)
			judgeMain(x, y + 1, 3, AsterColor);
		if(x < X - 1)
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
		if (field[y][x].getColor() == AsterColor
				&& field[y][x].getDeleteFlag() == false) {
			field[y][x].setDeleteFlag(true);
			if(y > 0)
				setDeleteFlagSameColor(x, y - 1, AsterColor);
			if(x > 0)
				setDeleteFlagSameColor(x - 1, y, AsterColor);
			if(y < Y - 1)
				setDeleteFlagSameColor(x, y + 1, AsterColor);
			if(x < X - 1)
				setDeleteFlagSameColor(x + 1, y, AsterColor);
		}
	}

	/**
	 * アステルにdeleteFlagを立てる
	 * 
	 * @param pt
	 *            注目するマスの座標
	 */
	public void setDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(true);
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
	public int delete(int x, int y, int count) {
		int AsterColor = field[y][x].getColor();

		if (field[y][x].getDeleteFlag() == true) {
			field[y][x].delete(0);
			count++;
			if(y > 0)
				count = delete(x, y - 1, count);
			if(x > 0)
				count = delete(x - 1, y, count);
			if(y < Y - 1)
				count = delete(x, y + 1, count);
			if(x < X - 1)
				count = delete(x + 1, y, count);

			// ランダムで決定した色で問題ない場合
			if (judge(x, y) == false) {
				return count;
			}

			// 初回にランダムで決定した色が置けなかった場合、4色試す
			int t = r.nextInt(4);

			for (int i = 1; i <= 4; i++, t++) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(table[t]);
				if (judge(x, y) == false)
					return count;
			}
			// 4色試しても置けない場合、delete前の色に決定する
			if (judge(x, y) == true) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(AsterColor);
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

		for(i = 0; i < Y; i++) {
			for(j = 0; j < X; j++) {
				if(judge(j, i) == true) {
					setDeleteFlagSameColor(j, i, field[i][j].getColor());
					count += delete(j, i, 0);
				}
			}
		}

		return count;
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
		return null;
	}
	
	public void onTurnStart(Player p){
		for(int i = 0;i < Y; i++){
			for(int j = 0;j < X;j++){
				if(field[i][j].getAsterClass() != null && field[i][j].getAsterClass().getPlayer() == p){
					field[i][j].getAsterClass().init();
				}
			}
		}
	}
	
	public boolean checkGameOver(Player p){
//		for(int i = 0;i < Y; i++){
//			for(int j = 0;j < X;j++){
//				final AsterClass ac = field[i][j].getAsterClass();
//				if(ac != null && ac.getNumber() == 1 && ac.getPlayer() == p){
//					return false;
//				}
//			}
//		}
		return false;
	}
}
