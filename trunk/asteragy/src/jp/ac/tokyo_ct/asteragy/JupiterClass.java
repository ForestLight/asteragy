package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class JupiterClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public JupiterClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		final Aster a = getAster();
		final Field field = a.getField();
		final Point asterPoint = field.asterToPoint(a);
		switch (mode) {
		case 0:
			if (getPlayer() == getPlayer().game.getPlayer2()) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			final Aster[][] f = field.getField();
			pt.x = asterPoint.x - (range[0].length / 2);
			pt.y = asterPoint.y - (range.length / 2);

			// �v���C���[2�̏ꍇ�����WP2���g�p
			if (getPlayer() == getPlayer().game.getPlayer2()) {
				// �ΏۑI��1��(�G���j�b�g�̂�)
				if (target1 == null) {
					for (int i = 0; i < defaultRange.length; i++) {
						if (pt.y + i < 0 || pt.y + i >= f.length)
							continue;
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (pt.x + j < 0 || pt.x + j >= f[0].length)
								continue;

							// �����W���ł���
							if (defaultRangeP2[i][j] == 1) {
								// ���̈ʒu�̃A�X�e���ɃN���X������
								final AsterClass asterClass = f[pt.y + i][pt.x
										+ j].getAsterClass();
								if (asterClass != null) {
									// ���̃N���X�̏����҂������ł͂Ȃ���ΑI���\
									if (asterClass.getPlayer() != getPlayer()) {
										range[i][j] = 1;
									}
								}
							}
						}
					}
				}
				// �ΏۑI��2�� (�A�X�e��)
				else {
					// �^�[�Q�b�g1�̃����W���ł̈ʒu
					pt.x = target1.x - (asterPoint.x - range[0].length / 2);
					pt.y = target1.y - (asterPoint.y - range.length / 2);

					for (int i = 0; i < defaultRange.length; i++) {
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (defaultRangeP2[i][j] == 1) {
								if (i != pt.y && j != pt.x) {
									range[i][j] = 1;
								}
							}
						}
					}
				}
			} else {
				// �ΏۑI��1��(�G���j�b�g�̂�)
				if (target1 == null) {
					for (int i = 0; i < defaultRange.length; i++) {
						if (pt.y + i < 0 || pt.y + i >= f.length)
							continue;
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (pt.x + j < 0 || pt.x + j >= f[0].length)
								continue;

							// �����W���ł���
							if (defaultRange[i][j] == 1) {
								// ���̈ʒu�̃A�X�e���ɃN���X������
								final AsterClass asterClass = f[pt.y + i][pt.x
										+ j].getAsterClass();
								if (asterClass != null) {
									// ���̃N���X�̏����҂������ł͂Ȃ���ΑI���\
									if (asterClass.getPlayer() != getPlayer()) {
										range[i][j] = 1;
									}
								}
							}
						}
					}
				}
				// �ΏۑI��2�� (�A�X�e��)
				else {
					// �^�[�Q�b�g1�̃����W���ł̈ʒu
					pt.x = target1.x - (asterPoint.x - range[0].length / 2);
					pt.y = target1.y - (asterPoint.y - range.length / 2);

					for (int i = 0; i < defaultRange.length; i++) {
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (defaultRange[i][j] == 1) {
								if (i != pt.y && j != pt.x) {
									range[i][j] = 1;
								}
							}
						}
					}
				}
			}
			return range;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		// �X���b�v�̏ꍇ���R�}���h�̏ꍇ������
		return swapSetPointAndNext(pt);
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapHasNext();
	}

	public boolean moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapMoveAstern();
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		getAster().getField().swap(target1, target2);

	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(7);
		}
		return asterImage;
	}

}
