package jp.ac.tokyo_ct.asteragy;

final class EarthClass extends AsterClass {
	static int[][] defaultRange = { { 1, 1, 1 }, { 1, 1, 1 },
			{ 1, 1, 1 } };

	EarthClass(Aster a, Player p) {
		super(a, p);
	}

	EarthClass(EarthClass a) {
		super(a);
	}

	AsterClass clone() {
		return new EarthClass(this);
	}

	int getNumber() {
		return 5;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			// SunClass����q��
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getPoint();
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point(thisPoint.x - (range[0].length / 2),
					thisPoint.y - (range.length / 2));

			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// ���̈ʒu�̃A�X�e���ɃN���X������ΑI��s�\
						final Aster f = field.at(pt.y + i, pt.x + j);
						if (f.getAsterClass() != null) {
							range[i][j] = 0;
						}
					}
				}
			}
			return range;
		}
		return null;
	}
}
