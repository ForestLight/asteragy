package jp.ac.tokyo_ct.asteragy;

final class MercuryClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0 } };

	MercuryClass(Aster a, Player p) {
		super(a, p);
	}

	MercuryClass(MercuryClass a) {
		super(a);
	}

	AsterClass clone() {
		return new MercuryClass(this);
	}

	int getNumber() {
		return 3;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			final Point tmp = getPoint();
			Point pt = new Point(tmp.x - (range[0].length / 2), tmp.y
					- (range.length / 2));

			for (int i = 0; i < defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.Y)
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x + j >= field.X)
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						// ���̈ʒu�̃A�X�e���ɃN���X������
						final AsterClass ac = field.field[pt.y + i][pt.x + j]
								.getAsterClass();
						if (ac != null) {
							// ���̃N���X�̏����҂������ł���
							if (ac.getPlayer() == getPlayer()) {
								// �s���\�񐔂�0�Ȃ�ΑI���\
								if (ac.getActionCount() == 0) {
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

	boolean setPointAndNext(Point pt) {
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
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return true;
		}
		return false;
	}

	void executeSpecialCommand() {
		field.getCanvas().paintEffect(new EffectCommandMercury(field, target1));
		logAction(target1);
		// �Ώۂ̍s���\�񐔂�1�񑝂₷
		field.at(target1).getAsterClass().incActionCount();
		logAction(target1);
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}
}
