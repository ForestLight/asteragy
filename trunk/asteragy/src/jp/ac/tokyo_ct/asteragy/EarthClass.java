package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EarthClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 1, 1, 1 },
			{ 1, 1, 1 },
			{ 1, 1, 1 } };

	private static Image asterImage;

	public EarthClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 5;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			// SunClass����q��
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Point thisPoint = getAster().getField().asterToPoint(
					getAster());
			final Field field = getAster().getField();
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i< defaultRange.length; i++) {
				if (pt.y + i < 0 || pt.y + i >= field.getY())
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (pt.x + j < 0 || pt.x+j >= field.getX())
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// ���̈ʒu�̃A�X�e���ɃN���X������ΑI��s�\
						final Aster f = field.getField()[pt.y
								+ i][pt.x + j];
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
			if(target1 == null)
				return true;
			target1 = null;
		}
		return false;

	}

	public String getName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�A�[�X";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�T�������[��";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�����W���Ƀ��[�����Ăяo��";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 6;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 6;
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		/* �C�[�W�X
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for (int i = 0; i < defaultRange.length; i++) {
			for (int j = 0; j < defaultRange[0].length; j++) {
				// �����W���ł���
				if (defaultRange[i][j] == 1) {
					pt.x = me.x - defaultRange.length + j;
					pt.y = me.y - defaultRange[0].length + i;

					// �t�B�[���h�̊O�ɂ͂ݏo���Ă��珈�����Ȃ�
					if (pt.x < 0
							|| pt.x >= getAster().getField().getField()[0].length)
						continue;
					if (pt.y < 0
							|| pt.y >= getAster().getField().getField().length)
						continue;

					// �N���X�����ł���
					if (getAster().getField().getAster(pt).getAsterClass() != null) {
						// ���̃��j�b�g�Ɠ���̃v���C���[���������Ă���̂Ȃ�
						if (getAster().getField().getAster(pt).getAsterClass()
								.getPlayer() == this.getPlayer()) {
							// �Ώەs�t���O�����Ă�
							getAster().getField().getAster(pt).getAsterClass()
									.setProtectedFlag(true);
						}
					}
				}
			}
		}
		*/
		final Aster a = getAster().getField().getAster(target1);
		AsterClass ac = new MoonClass(a,getPlayer());
		a.setAsterClass(ac);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(5);
		}
		return asterImage;
	}
}
