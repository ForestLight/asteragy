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
	 * �t�B�[���h�̃T�C�Y�����߂�
	 * 
	 * @param x
	 *            �t�B�[���h�̉��}�X��
	 * @param y
	 *            �t�B�[���h�̏c�}�X��
	 */
	public void setFieldSize(int x, int y) {
		X = x;
		Y = y;
	}

	public boolean isFieldInit() {
		return fieldinit;
	}

	/**
	 * �t�B�[���h�̏�����
	 * 
	 */
	public void setAster() {
		fieldinit = true;
		field = new Aster[Y][X];
		backup = new Aster[Y][X];
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
	 * �^�[���J�n����
	 * 
	 * @param player
	 */
	public void beginTurn(Player player) {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				// �N���X������
				final AsterClass asterClass = field[i][j].getAsterClass();
				if (asterClass != null) {
					// ���݃^�[���i�s���̃v���C���[�̃��j�b�g�Ȃ珉����
					if (asterClass.getPlayer() == player) {
						asterClass.init();
					}
				}
			}
		}
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
	public void removeDeleteFlagAll() {
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
	public void removeJudgeFlagAll() {
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
	 */
	public int delete(int x, int y) {
		return delete(x, y, 0, null);
	}

	/**
	 * deleteFlag�������Ă���A�X�e����S�ď���
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @param count
	 *            �������A�X�e�������J�E���g�i�ŏ���0������j
	 * @param pt
	 *            �O�񒍖ڂ��Ă������W
	 * @return �������A�X�e����
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
				System.out.println("special_delete:AsterColor=" + AsterColor);
				target.setColor(AsterColor);
				return count;
			}
			/*
			 * // �S�F�����Ă��u���Ȃ��ꍇ�A�܂�����3�q������4�F�̃A�X�e���ň͂܂�Ă��� //
			 * ���̏ꍇ�A���̃}�X�͍ŏ��Ƀ����_���Ō��肵���F�ɖ߂��A�O���delete�̃}�X�͕ʂ̐F�ɕύX // �i4�F�Q�[���̍ۂ̓��ʑ[�u�j
			 * if (judge(x, y) == true) { System.out.println("special_delete");
			 * target.setColor(AsterColor); for (int i = 0; i < Aster.COLOR_MAX;
			 * i++) { field[pt.y][pt.x].setColor(i); if (judge(pt.x, pt.y) ==
			 * false) break; } }
			 */
		}
		return count;
	}

	/**
	 * �t�B�[���h��̏�����ׂ��A�X�e����S�ď���
	 * 
	 * @return �������A�X�e����
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
	 * ���݂̃t�B�[���h�̃o�b�N�A�b�v���Ƃ�
	 */
	public void backupField() {
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				backup[i][j] = field[i][j];
			}
		}
	}

	/**
	 * �t�B�[���h�̃o�b�N�A�b�v�𕜌�����
	 */
	public void restoreField() {
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
	 * �X���b�v
	 * 
	 * @param a
	 *            b�Ɠ���ւ���
	 * @param b
	 *            a�Ɠ���ւ���
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
	 * �t�B�[���h�`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	public void paint(Graphics g) {
		Point point = new Point();
		// �t�B�[���h�C�A�X�e���`��
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
	 * �t�B�[���h�摜�ǂݍ���
	 * 
	 * private void loadField() { // �Ǎ���C���[�W fieldimage = null; try { //
	 * ���\�[�X����ǂݍ��� MediaImage m =
	 * MediaManager.getImage("resource:///fieldimage.jpg"); // ���f�B�A�̎g�p�J�n
	 * m.use(); // �ǂݍ��� fieldimage = m.getImage(); } catch (Exception e) { }
	 * fieldimage = Image.createImage(GameCanvas.measure + 1, GameCanvas.measure +
	 * 1); Graphics g = fieldimage.getGraphics(); // �Վ��}�X
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
	 * �T���̈ʒu
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
