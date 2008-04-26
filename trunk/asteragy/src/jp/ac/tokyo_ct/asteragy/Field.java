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

	static int CONNECTION;

	Field(Game g, int x, int y) {
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

	boolean isFieldInit() {
		return fieldinit;
	}
	
	/**
	 * �t�B�[���h�̏�����
	 * 
	 */
	void setAster() {
		fieldinit = true;
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = new Aster(this);
				// ���Ŕ���
				while (judge(j, i) == true) {
					field[i][j].setDeleteFlag(true);
					field[i][j].delete(0);
				}
			}
		}
		fieldinit = false;
	}

	/**
	 * �A�X�e����3�Ȃ����Ă��邩�̔�������邾��
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @return ���F�A�X�e�����K��Ȃ����Ă����true
	 */
	boolean judge(int x, int y) {
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
	 * �Ȃ����Ă��铯�F�A�X�e�����J�E���g
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @param AsterColor
	 *            ����ΏېF
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
	 * �Ȃ��������F�A�X�e���S�Ă�deleteFlag�𗧂Ă�
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @param AsterColor
	 *            ����ΏېF
	 */
	void setDeleteFlagSameColor(int x, int y, int AsterColor) {
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
	 * �A�X�e����deleteFlag�𗧂Ă�
	 * 
	 * @param pt
	 *            ���ڂ���}�X�̍��W
	 */
	void setDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(true);
	}

	/**
	 * deleteFlag���O��
	 * 
	 * @param pt
	 *            ���ڂ���}�X�̍��W
	 */
	void removeDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(false);
	}

	/**
	 * deleteFlag��S�ĊO��
	 * 
	 */
	void removeDeleteFlagAll() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j].setDeleteFlag(false);
			}
		}
	}

	/**
	 * judgeFlag��S�ĊO��
	 * 
	 */
	void removeJudgeFlagAll() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] != null)
					field[i][j].setJudgeFlag(false);
			}
		}
	}

	/**
	 * deleteFlag�������Ă���A�X�e����S�ď���
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @return �������A�X�e����
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

			// �����_���Ō��肵���F�Ŗ��Ȃ��ꍇ
			if (judge(x, y) == false) {
				return count;
			}

			// ����Ƀ����_���Ō��肵���F���u���Ȃ������ꍇ�A�S�F����
			for (int i = 1; i <= Aster.COLOR_MAX; i++) {
				target.setColor(i);
				if (judge(x, y) == false)
					return count;
			}

			// �S�F�����Ă��u���Ȃ��Ƃ��́A�폜�O�̐F�Ɍ���
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
	 * �t�B�[���h��̏�����ׂ��A�X�e����S�ď���
	 * 
	 * @return �������A�X�e����
	 */
	int deleteAll() {
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
	 * ���݂̃t�B�[���h�̃o�b�N�A�b�v���Ƃ�
	 */
	void backupField() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				backup[i][j] = field[i][j];
			}
		}
	}

	/**
	 * �t�B�[���h�̃o�b�N�A�b�v�𕜌�����
	 */
	void restoreField() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = backup[i][j];
			}
		}
	}

	/**
	 * �T�����Ŕ���
	 * 
	 * @return �T�������ł������ȏ�ԂȂ�true
	 */
	boolean judgeSelfDestruction() {
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
	 * �X���b�v
	 * 
	 * @param a
	 *            b�Ɠ���ւ���
	 * @param b
	 *            a�Ɠ���ւ���
	 */
	void swap(Point a, Point b) {
		Aster tmp = field[a.y][a.x];
		field[a.y][a.x] = field[b.y][b.x];
		field[b.y][b.x] = tmp;
	}

	Aster at(Point pt) {
		return field[pt.y][pt.x];
	}

	Aster at(int y, int x) {
		return field[y][x];
	}

	Point asterToPoint(Aster a) {
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

	void onTurnStart(Player p) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				final AsterClass asterClass = field[i][j].getAsterClass();
				if (asterClass != null && asterClass.getPlayer() == p) {
					asterClass.init();
				}
			}
		}
	}

	Player checkGameOver() {
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
	 * �t�B�[���h�`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	public void paint(Graphics g) {
		// �t�B�[���h�C�A�X�e���`��
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == null)
					continue;
				repaintAsterNoBack(g, j, i);
			}
		}
	}

	void repaintField() {
		final CanvasControl canvas = game.getCanvas();
		canvas.repaint(canvas.getLeftMargin(), canvas.getTopMargin(),
				CanvasControl.measure * X, CanvasControl.measure * Y);
	}

	void repaintAster(Graphics g, Point point) {
		if (point.x < 0 || point.x >= X || point.y < 0 || point.y >= Y)
			return;
		CanvasControl.paintAsterBack(g, point);
		repaintAsterNoBack(g, point.x, point.y);
	}

	void repaintAsterNoBack(Graphics g, int x, int y) {
		if (x < 0 || x >= X || y < 0 || y >= Y)
			return;
		final CanvasControl canvas = game.getCanvas();
		final Aster aster = field[y][x];
		this.setOrignAster(g, x, y);
		aster.paint(g);		
		canvas.range.paint(g, x, y);
		final Cursor cursor = canvas.cursor;
		if (cursor.isCursor(x, y)){
			cursor.paint(g);
		}
	}

	void repaintAsterRect(Graphics g, Point leftTop, Point rightBottom) {
		for (int i = Math.max(leftTop.y, 0); i <= Math
				.min(rightBottom.y, Y - 1); i++) {
			for (int j = Math.max(leftTop.x, 0); j <= Math.min(rightBottom.x,
					X - 1); j++) {
				repaintAsterNoBack(g, j, i);
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
	 * �T���̈ʒu
	 * 
	 * @param p
	 * @return
	 */
	Point getSunPosition(Player p) {
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

	void setOrginField(Graphics g) {
		g.setOrigin(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin());
	}

	void setOrignAster(Graphics g, Point aster) {
		g.setOrigin(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y);
	}

	void setOrignAster(Graphics g, int x, int y) {
		g.setOrigin(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* x, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * y);
	}

	void setOrignAster(Graphics g, Point aster, int dx, int dy) {
		g.setOrigin(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x + dx, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y + dy);
	}

	/*
	void setClipRectField(Graphics g) {
		g.setClip(game.getCanvas().getLeftMargin(), game.getCanvas()
				.getTopMargin(), CanvasControl.measure * X + 1,
				CanvasControl.measure * Y + 1);
	}

	void setClipRectAster(Graphics g, Point aster) {
		g.setClip(game.getCanvas().getLeftMargin() + CanvasControl.measure
				* aster.x, game.getCanvas().getTopMargin()
				+ CanvasControl.measure * aster.y, CanvasControl.measure,
				CanvasControl.measure);
	}
*/
	Point getAsterLocation(Point aster) {
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
