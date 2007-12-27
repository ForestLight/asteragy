package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class VenusClass extends AsterClass {
	private static int[][] defaultRange = {
			{ 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, 
			{ 0, 1, 1, 1, 0 }, 
			{ 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 } };

	private static Image asterImage;

	public VenusClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 4;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getAster().getField().asterToPoint(
					getAster());
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i + pt.y < defaultRange.length; i++) {
				if (pt.y + i < 0)
					continue;
				for (int j = 0; j + pt.x < defaultRange[0].length; j++) {
					if (pt.x + j < 0)
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						// ���̈ʒu�̃A�X�e���ɃN���X������
						final Aster f = getAster().getField().getField()[pt.y
								+ i][pt.x + j];
						if (f.getAsterClass() != null) {
							// ���̃N���X�̏����҂�����ł���
							if (f.getAsterClass().getPlayer() != getPlayer()) {
								// �T���łȂ���ΑΏۂɑI���\
								if (f.getNumber() != 1)
									range[i][j] = 1;
							}
						}
					}
				}
			}
			return defaultRange;
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
			return swapHasNext(target1, target2);
		case 1:
			if (target1 == null)
				return true;
			else
				return false;
		}
		return false;
	}

	public void moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			swapMoveAstern();
			break;
		case 1:
			break;
		}
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�e���v�e�[�V����";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�G���j�b�g��̂������̃��j�b�g�ɂ���";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public String getName() {
		return "���B�[�i�X";
	}

	public void executeSpecialCommand() {
		// �Ώۂ̏����҂�ύX
		getAster().getField().getAster(target1).getAsterClass().setPlayer(
				this.getPlayer());
		// �s���Ϗ�Ԃ�
		getAster().getField().getAster(target1).getAsterClass().setActionCount(
				0);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(4);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}
}
