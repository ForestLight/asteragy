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

	// �t�B�[���h�̃T�C�Y
	public void setFieldSize(int x, int y) {
		X = x;
		Y = y;
	}

	// �t�B�[���h�̏�����
	public void setAster() {
		field = new Aster[Y][X];
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = new Aster(this);
				// ���Ŕ���
				while (judge(j, i) == true) {
					field[i][j].setDeleteFlag(true);
					field[i][j].delete();
				}
			}
		}
	}

	// �A�X�e����3�Ȃ����Ă��邩�̔�������邾��
	public boolean judge(int x, int y) {
		int AsterColor = field[y][x].getColor();

		countAster = 0;

		judgeMain(x, y - 1, 1, AsterColor);
		judgeMain(x - 1, y, 2, AsterColor);
		judgeMain(x, y + 1, 3, AsterColor);
		judgeMain(x + 1, y, 4, AsterColor);

		if (countAster < 3) {
			countAster = 0;
			return false; // �Ȃ������A�X�e����3�����Ȃ�false��Ԃ�
		} else {
			countAster = 0;
			return true; // 3�ȏ�Ȃ�true
		}
	}

	// �Ȃ����Ă��铯�F�A�X�e�����J�E���g
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

	// �Ȃ��������F�A�X�e���S�Ă�deleteFlag���Ă� �ċA�g��������c
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

	// �t���O�������Ă�A�X�e����delete���āA�Ăэ폜���� ������ċA
	public void delete(int x, int y) {
		if (field[y][x].getDeleteFlag() == true) {
			field[y][x].delete();
			delete(x, y - 1);
			delete(x - 1, y);
			delete(x, y + 1);
			delete(x + 1, y);

			while (judge(x, y) == true) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete();
			}
		}
	}

	// swap
	public void swap(int x1, int y1, int x2, int y2) {
		Aster tmp = field[y1][x1];
		field[y1][x1] = field[y2][x2];
		field[y2][x2] = tmp;
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

}
