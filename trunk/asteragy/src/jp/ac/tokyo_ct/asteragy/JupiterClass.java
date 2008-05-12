package jp.ac.tokyo_ct.asteragy;

final class JupiterClass extends AsterClass {
	static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static int[][] defaultRangeP2 = { { 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

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
		final Point asterPoint = getPoint();
		// �v���C���[2�̏ꍇ�����WP2���g�p
		final int[][] def = getPlayer() == game.player[0]
			? defaultRange
			: defaultRangeP2;
		switch (mode) {
		case 0:
			return swapGetRange(def);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point(asterPoint.x - (range[0].length / 2),
					asterPoint.y - (range.length / 2));
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
}
