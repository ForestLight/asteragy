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
	 * �^�[���J�n����
	 * 
	 * @param player
	 */
	public void beginTurn(Player player) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				// �N���X������
				if (field[i][j].getAsterClass() != null) {
					// ���݃^�[���i�s���̃v���C���[�̃��j�b�g�Ȃ珉����
					if (field[i][j].getAsterClass().getPlayer() == player) {
						field[i][j].getAsterClass().init();
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
	 * �A�X�e����deleteFlag�𗧂Ă�
	 * 
	 * @param pt
	 *            ���ڂ���}�X�̍��W
	 */
	public void setDeleteFlag(Point pt) {
		field[pt.y][pt.x].setDeleteFlag(true);
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

			// �����_���Ō��肵���F�Ŗ��Ȃ��ꍇ
			if (judge(x, y) == false) {
				return count;
			}

			// ����Ƀ����_���Ō��肵���F���u���Ȃ������ꍇ�A4�F����
			int t = r.nextInt(4);

			for (int i = 1; i <= 4; i++, t++) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(table[t]);
				if (judge(x, y) == false)
					return count;
			}
			// 4�F�����Ă��u���Ȃ��ꍇ�Adelete�O�̐F�Ɍ��肷��
			if (judge(x, y) == true) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete(AsterColor);
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
