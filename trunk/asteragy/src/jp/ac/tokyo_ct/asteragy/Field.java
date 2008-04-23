package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.io.InputStream;

import com.nttdocomo.ui.Graphics;

/**
 * @author Okubo
 */
final class Field /* implements PaintItem */{

	final Aster[][] field;

	private final Aster[][] backup;

	final int X, Y;

	private int countAster;

	final Game game;

	private boolean fieldinit;

	public static int CONNECTION;

	public Field(Game g, int x, int y) {
		super();
		game = g;
		X = x;
		Y = y;
		field = new Aster[Y][X];
		backup = new Aster[Y][X];
	}

	Field clone() {
		Field f = new Field(game, X, Y);
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; ++j)
				f.field[i][j] = field[i][j];
		for (int i = 0; i < Y; i++)
			for (int j = 0; j < X; ++j)
				f.backup[i][j] = backup[i][j];
		f.countAster = countAster;
		f.fieldinit = fieldinit;
		return f;
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
	 * @return 消したアステル数
	 */
	int delete(int x, int y) {
		final Aster target = field[y][x];
		if (target.getDeleteFlag() == true) {
			int count = 1;
			target.delete(0);
			game.getCanvas().disappearControl.Add(new Point(x, y));
			if (y > 0)
				count += delete(x, y - 1);
			if (x > 0)
				count += delete(x - 1, y);
			if (y < Y - 1)
				count += delete(x, y + 1);
			if (x < X - 1)
				count += delete(x + 1, y);

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
				final int AsterColor = target.getColor();
				System.out.println("special_delete:AsterColor = " + AsterColor);
				target.setColor(AsterColor);
				return count;
			}
		}
		return 0;
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
	}

	public Aster at(Point pt) {
		return field[pt.y][pt.x];
	}

	public Aster at(int y, int x) {
		return field[y][x];
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
		final Player player[] = game.player;
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				final AsterClass ac = field[i][j].getAsterClass();
				if (ac != null && ac.getNumber() == 1) {
					final Player sunPlayer = ac.getPlayer();
					if (sunPlayer == player[0])
						p1 = true;
					if (sunPlayer == player[1])
						p2 = true;
				}
			}
		}
		if (!p1)
			return player[0];
		else if (!p2)
			return player[1];
		else
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
		final Point pt = new Point();
		// フィールド，アステル描画
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == null)
					continue;
				pt.y = i;
				pt.x = j;
				repaintAsterNoBack(g, pt);
			}
		}
	}

	void repaintField() {
		final CanvasControl canvas = game.getCanvas();
		canvas.repaint(canvas.getLeftMargin(), canvas.getTopMargin(),
				CanvasControl.measure * X, CanvasControl.measure * Y);
	}

	public void repaintAster(Graphics g, Point point) {
		if (point.x < 0 || point.x >= X || point.y < 0 || point.y >= Y)
			return;
		CanvasControl.paintAsterBack(g, point);
		repaintAsterNoBack(g, point);
	}

	public void repaintAsterNoBack(Graphics g, Point point) {
		if (point.x < 0 || point.x >= X || point.y < 0 || point.y >= Y)
			return;
		final CanvasControl canvas = game.getCanvas();
		final Aster aster = field[point.y][point.x];
		this.setOrignAster(g, point);
		aster.paint(g);
		canvas.range.paint(g, point);
		final Cursor cursor = canvas.cursor;
		if (cursor.isCursor(point))
			cursor.paint(g);
	}

	public void repaintAsterRect(Graphics g, Point leftTop, Point rightBottom) {
		final Point pt = new Point();
		for (int i = Math.max(leftTop.y, 0); i <= Math
				.min(rightBottom.y, Y - 1); i++) {
			for (int j = Math.max(leftTop.x, 0); j <= Math.min(rightBottom.x,
					X - 1); j++) {
				pt.y = i;
				pt.x = j;
				repaintAsterNoBack(g, pt);
			}
		}
	}

	boolean isYInFieldBound(int y) {
		return 0 <= y && y < Y;
	}

	boolean isXInFieldBound(int x) {
		return 0 <= x && x < X;
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
		System.out.println("getSunPosition - not found");
		return null;
	}

	public int getDistance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	public void setOrginField(Graphics g) {
		g.setOrigin(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin());
	}

	public void setOrignAster(Graphics g, Point aster) {
		g.setOrigin(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y);
	}

	public void setOrignAster(Graphics g, Point aster, int dx, int dy) {
		g.setOrigin(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x + dx, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y + dy);
	}
	/*
	public void setClipRectField(Graphics g) {
		g.setClip(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin(), CanvasControl.measure * X + 1,
				CanvasControl.measure * Y + 1);
	}

	public void setClipRectAster(Graphics g, Point aster) {
		g.setClip(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y, CanvasControl.measure,
				CanvasControl.measure);
	}
*/
	public Point getAsterLocation(Point aster) {
		return new Point(game.getCanvas().getLeftMargin()
				+ CanvasControl.measure * aster.x + 1, game.getCanvas()
				.getTopMargin()
				+ CanvasControl.measure * aster.y + 1);
	}

	CanvasControl getCanvas() {
		return game.getCanvas();
	}
	
	String toStringForInit() {
		StringBuffer buf = new StringBuffer(200);
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				buf.append(field[i][j].getColor());
			}
		}
		return buf.toString();
	}
	
	void inputFromStream(InputStream is) throws IOException {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = new Aster(this, HTTPPlayer.readIntChar(is));
			}
		}		
	}

}
