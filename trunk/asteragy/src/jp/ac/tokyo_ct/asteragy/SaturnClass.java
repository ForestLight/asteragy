package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class SaturnClass extends AsterClass {
	private static int[][] defaultRange = { { 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1 } };

	private static Image asterImage;

	public SaturnClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 8;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return null;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			return false;
		}
		return false;
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
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
			break;
		}
		return false;
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		// �����
		int i, j;
		Point pt = new Point();
		final Aster a = getAster();
		final Field field = a.getField();
		pt.x = field.asterToPoint(a).x - (defaultRange[0].length / 2);
		pt.y = field.asterToPoint(a).y - (defaultRange.length / 2);
		Aster[] queue = new Aster[17];

		final Aster[][] f = field.getField();
		for (i = 0, j = 0; j < 16; j++) {
			// �O�������W�̃A�X�e�����E���ɃL���[�i�̂悤�Ȃ��́j�ɓ���Ă���
			if (pt.x >= 0 && pt.x < field.getX() && pt.y >= 0
					&& pt.y < field.getY()) {
				queue[i] = f[pt.y][pt.x];
				i++;
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;

		}
		// pt.x =
		// getAster().getField().asterToPoint(getAster()).x-(defaultRange[0].length/2);
		// pt.y =
		// getAster().getField().asterToPoint(getAster()).y-(defaultRange.length/2);

		for (i = 1, j = 0;; j++) {
			// �L���[��1�Ԗڂ̃A�X�e������E���ɖ߂��Ă���
			if (pt.x >= 0 && pt.x < field.getX() && pt.y >= 0
					&& pt.y < field.getY()) {
				f[pt.y][pt.x] = queue[i];
				i++;
			}

			if (j < 4)
				pt.x++;
			else if (j < 8)
				pt.y++;
			else if (j < 12)
				pt.x--;
			else
				pt.y--;

			// �L���[�i�̂悤�Ȃ��́j����ɂȂ�����0�Ԗڂ̃A�X�e����߂��ă��[�v�𔲂���
			if (queue[i] == null) {
				f[pt.y][pt.x] = queue[0];
				break;
			}
		}
		/* ����肩�E�����v���C���[�ɑI��������̂͂ǂ��ɂȂ�̂� */
	}

	public Image getImage() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (asterImage == null) {
			asterImage = loadImage(8);
		}
		return asterImage;
	}

}
