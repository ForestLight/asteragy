package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class UranusClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 1, 0, 1, 1, 1, 0, 1 }, { 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };

	private static Image asterImage;

	public UranusClass(Aster a, Player p) {
		super(a, p);
	}

	public UranusClass(UranusClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new UranusClass(this);
	}

	public int getNumber() {
		return 9;
	}

	public int[][] getRange() {
		final Aster a = getAster();
		final Field field = a.getField();
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			final Aster[][] f = field.getField();
			Point pt = new Point(field.asterToPoint(a).x
					- (range[0].length / 2), field.asterToPoint(a).y
					- (range.length / 2));

			// 1��
			if (target1 == null) {
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (defaultRange[i][j] == 1) {
							// �����W���Ŏ��g�ȊO�Ȃ�I����
							if (f[pt.y + i][pt.x + j] != a) {
								range[i][j] = 1;
							}
							// ���g�Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}
			// 2��
			else {
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.length)
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.x + j >= f[0].length)
							continue;

						if (defaultRange[i][j] == 1) {
							// �����W���Ŏ��g��1�ڂ̏ꏊ�ȊO�Ȃ�I����
							if (f[pt.y + i][pt.x + j] != a
									&& field.asterToPoint(a) != target1) {
								range[i][j] = 1;
							}
							// ���g�Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}
			return range;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// �X���b�v�̏ꍇ���R�}���h�̏ꍇ������
		return swapSetPointAndNext(pt);
	}

	public boolean hasNext() {
		return swapHasNext();
	}

	public boolean moveAstern() {
		return swapMoveAstern();
	}

	public void executeSpecialCommand() {
		getAster().getField().swap(target1, target2);
		logAction(target1, target2);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(9);
		}
		return asterImage;
	}
}
