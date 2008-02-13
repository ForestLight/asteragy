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
	 * @return true ���F�A�X�e����3�Ȃ����Ă��� false 3����
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
	 * �Ȃ����Ă��铯�F�A�X�e�����J�E���g
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @param back
	 *            �O���judgeMain�Œ��ڂ��Ă������W�̕����i���o�[
	 * @param AsterColor
	 *            ����ΏېF
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
			 * ���ݒ��ڂ��Ă�����W����߂肳���Ȃ��悤�ɍċA back�i�ȑO�ɒ��ڂ��Ă������W�̂�������j1 �� 2 �E 3 �� 4 ��
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
	 * deleteFlag�������Ă���A�X�e����S�ď����i�J�E���g���s��Ȃ��j
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 */
	public int delete(int x, int y) {
		return delete(x, y, 0);
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
	 * @return �������A�X�e����
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

			// �����_���Ō��肵���F�Ŗ��Ȃ��ꍇ
			if (judge(x, y) == false) {
				return count;
			}

			// ����Ƀ����_���Ō��肵���F���u���Ȃ������ꍇ�A5�F����
			int t = Game.random.nextInt(5);

			for (int i = 1; i <= 5; i++, t++) {
				target.setColor(table[t]);
				if (judge(x, y) == false)
					return count;
			}
			// 5�F�����Ă��u���Ȃ��ꍇ�Adelete�O�̐F�Ɍ��肷��
			if (judge(x, y) == true) {
				target.setColor(AsterColor);
			}
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
					count += delete(j, i, 0);
				} else if (field[i][j].getDeleteFlag() == true) {
					count += delete(j, i, 0);
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
		// ���_���W�ʒu�v�Z
		g.setOrigin((240 - X * GameCanvas.measure) / 2, (240 - Y
				* GameCanvas.measure) / 2);
		// �t�B�[���h�C�A�X�e���`��
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				if (field[i][j] == null)
					continue;
				paint(g, i, j);
			}
		}
	}

	private void paint(Graphics g, int i, int j) {
		// �t�B�[���h
		// g.drawImage(fieldimage, i * GameCanvas.measure, j
		// * GameCanvas.measure);

		// �A�X�e��
		// �v���C���[2�̃��j�b�g�͔��]
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

		// �����W
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

}
