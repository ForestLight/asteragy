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
			Point pt = new Point();
			pt.x = tmp.x - (range[0].length / 2);
			pt.y = tmp.y - (range.length / 2);

			for (int i = 0; i + pt.y < defaultRange.length; i++) {
				if (pt.y + i < 0
						|| pt.y + i >= getAster().getField().getField().length)
					continue;
				for (int j = 0; j + pt.x < defaultRange[0].length; j++) {
					if (pt.x + j < 0
							|| pt.x + j >= getAster().getField().getField()[0].length)
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						// ���̈ʒu�̃A�X�e���ɃN���X������
						if (getAster().getField().getField()[pt.y + i][pt.x + j]
								.getAsterClass() != null) {
							// ���̃N���X�̏����҂������ł���
							if (getAster().getField().getField()[pt.y + i][pt.x
									+ j].getAsterClass().getPlayer() == getPlayer()) {
								// �s���\�񐔂�0�Ȃ�ΑI���\
								if (getAster().getField().getField()[pt.y + i][pt.x
										+ j].getAsterClass().getActionCount() == 0) {
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

	public String getName() {
		return "�}�[�L�����[";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�N�C�b�N�^�C��";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�ق�イ";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 8;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 4;
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

	public int getActionNum() {
		return 1;
	}

}
