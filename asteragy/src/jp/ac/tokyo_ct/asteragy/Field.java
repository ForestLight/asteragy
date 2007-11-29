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
	 * �w�����ꂽ�s�����s��
	 * 
	 * @param a
	 * @return
	 */
	public boolean act(Action a) {
		// �s�����N����
		// ...

		GameCanvas c = game.getCanvas();
		c.repaint();
		return false;
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

	/**
	 * �t�B�[���h�̏�����
	 * 
	 */
	public void setAster() {
		field = new Aster[Y][X];
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
	}

	/**
	 * �A�X�e����3�Ȃ����Ă��邩�̔�������邾��
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
	 * @return true ���F�A�X�e����3�Ȃ����Ă���
	 * @return false �Ȃ��������F�A�X�e����3����
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
			 * ���ݒ��ڂ��Ă�����W����߂肳���Ȃ��悤�ɍċA back 1 �� �i�ȑO�ɒ��ڂ��Ă������W�̂�������j 2 �E 3 �� 4 ��
			 * 
			 * �Ȃ��������F�A�X�e����3�����邾���Ȃ画�肷��ʒu�͔��Ȃ���ˁc?
			 */
			if (back != 1)
				judgeMain(x, y + 1, 3, AsterColor);
			if (back != 2)
				judgeMain(x + 1, y, 4, AsterColor);
			if (back != 3)
				judgeMain(x, y - 1, 1, AsterColor);
			if (back != 4)
				judgeMain(x - 1, y, 2, AsterColor);

			// ���Ȃ�߂�ǂ��������Ƃ��Ă�C������ �����̂����S�z �C����]
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
	 * �t���O�������Ă�A�X�e����delete���āA�Ăэ폜����
	 * 
	 * @param x
	 *            ���ڂ���}�X��x���W
	 * @param y
	 *            ���ڂ���}�X��y���W
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
		return null; // ���t����Ȃ������Ƃ�
	}
}
