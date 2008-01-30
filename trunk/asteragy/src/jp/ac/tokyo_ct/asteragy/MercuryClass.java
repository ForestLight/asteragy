package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class MercuryClass extends AsterClass {

	private static int[][] defaultRange = { { 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public MercuryClass(Aster a, Player p) {
		super(a, p);
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 3;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			final Point tmp = getAster().getPoint();
			final Field field = getAster().getField();
			Point pt = new Point(tmp.x - (range[0].length / 2), tmp.y
					- (range.length / 2));

			for (int i = 0; i < defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.getY())
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x + j >= field.getX())
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						// ���̈ʒu�̃A�X�e���ɃN���X������
						final AsterClass asterClass = field.getField()[pt.y + i][pt.x
								+ j].getAsterClass();
						if (asterClass != null) {
							// ���̃N���X�̏����҂������ł���
							if (asterClass.getPlayer() == getPlayer()) {
								// �s���\�񐔂�0�Ȃ�ΑI���\
								if (asterClass.getActionCount() == 0) {
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
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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

	public boolean moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return true;
		}
		return false;
	}

	public void executeSpecialCommand() {
		// �Ώۂ̍s���\�񐔂�1�񑝂₷
		getAster().getField().getAster(target1).getAsterClass()
				.incActionCount();
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(3);
		}
		return asterImage;
	}
}
