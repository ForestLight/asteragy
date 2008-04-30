package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class JupiterClass extends AsterClass {
	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	JupiterClass(Aster a, Player p) {
		super(a, p);
	}

	JupiterClass(JupiterClass a) {
		super(a);
	}

	AsterClass clone() {
		return new JupiterClass(this);
	}
	
	int getNumber() {
		return 7;
	}

	int[][] getRange() {
		final Aster a = getAster();
		final Field field = a.field;
		final Point asterPoint = field.asterToPoint(a);
		switch (mode) {
		case 0:
			if (getPlayer() == getPlayer().game.player[1]) {
				return swapGetRange(defaultRangeP2);
			} else {
				return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = asterPoint.x - (range[0].length / 2);
			pt.y = asterPoint.y - (range.length / 2);
			
			// �v���C���[2�̏ꍇ�����WP2���g�p
			final int[][] def = getPlayer() == getPlayer().game.player[0]
				? defaultRange
				: defaultRangeP2;

			// �ΏۑI��1��(�G���j�b�g�̂�)
			if (target1 == null) {
				for (int i = 0; i < def.length; i++) {
					if (!field.isYInFieldBound(pt.y + i))
						continue;
					for (int j = 0; j < def[0].length; j++) {
						if (!field.isXInFieldBound(pt.x + j))
							continue;

						// �����W���ł���
						if (def[i][j] == 1) {
							// ���̈ʒu�̃A�X�e���ɃN���X������
							final AsterClass asterClass =
								field.at(pt.y + i, pt.x + j).getAsterClass();
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

				for (int i = 0; i < def.length; i++) {
					for (int j = 0; j < def[0].length; j++) {
						if (def[i][j] == 1) {
							if (i != pt.y && j != pt.x) {
								range[i][j] = 1;
							}
						}
					}
				}
			}
			return range;
		}
		return null;
	}

	boolean setPointAndNext(Point pt) {
		// �X���b�v�̏ꍇ���R�}���h�̏ꍇ������
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	boolean hasNext() {
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			if (target1 == null)
				return true;
			else
				return false;
		}
		return false;
	}

	boolean moveAstern() {
		return swapMoveAstern();
	}

	void executeSpecialCommand() {
		final Field field = getAster().field;
		
		Effect effect = new EffectCommandJupiter(target1);
		game.getCanvas().paintEffect(effect);
		logAction(target1);
		field.setDeleteFlag(target1);
		field.delete(target1.x, target1.y, game.getCanvas().disappearControl.disappearing);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(7);
		}
		return asterImage;
	}

}
