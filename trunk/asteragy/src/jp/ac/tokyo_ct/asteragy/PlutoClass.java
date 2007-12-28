package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class PlutoClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 0, 1, 0, 1, 0 },
			{ 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, 
			{ 0, 0, 1, 0, 0 }, 
			{ 0, 1, 0, 1, 0 } };

	private static Image asterImage;

	public PlutoClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 11;
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
			return false;
		}
		return false;
	}

	public String getName() {
		return "�v���[�g";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "���C���N���X�g";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�����W���̃A�X�e��\n�S�Ă�j�󂷂� (�T���ɂ��L��)";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 11;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 8;
	}

	public void executeSpecialCommand() {
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for (int i = 0; i < defaultRange.length; i++) {
			for (int j = 0; j < defaultRange[0].length; j++) {
				// �����W���ł���
				if (defaultRange[i][j] == 1) {
					// ���g�ł͂Ȃ�������j��
					if (i != defaultRange.length / 2
							&& j != defaultRange[0].length / 2) {
						pt.x = me.x - defaultRange.length + j;
						pt.y = me.y - defaultRange[0].length + i;

						// �t�B�[���h�̊O�ɂ͂ݏo���Ă��珈�����Ȃ�
						if (pt.x < 0
								|| pt.x >= getAster().getField().getField()[0].length)
							continue;
						if (pt.y < 0
								|| pt.y >= getAster().getField().getField().length)
							continue;

						// �^�[�Q�b�g��j��
						getAster().getField().setDeleteFlag(pt);
						getAster().getField().delete(pt.x, pt.y, 0);
					}
				}
			}
		}
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(11);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}

}