package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class MarsClass extends AsterClass {
	private static int[][] defaultRange = { 
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 } };
	
	private static int[][] defaultRangeP2 = { 
		{ 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 } };

	private static Image asterImage;

	public MarsClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 6;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			if(getPlayer() == getPlayer().game.getPlayer2()){
				return swapGetRange(defaultRangeP2);
			}else{
			return swapGetRange(defaultRange);
			}
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			Field f = getAster().getField();
			pt.x = f.asterToPoint(getAster()).x
					- (range[0].length / 2);
			pt.y = f.asterToPoint(getAster()).y
					- (range.length / 2);

			if(getPlayer() == getPlayer().game.getPlayer2()){
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0 || pt.y + i >= f.getY())
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0 || pt.y + i >= f.getX())
							continue;

						if (defaultRangeP2[i][j] == 1) {
							// �����W���Ŏ��g���T���ȊO�Ȃ�I����
							if (f.getField()[pt.y + i][pt.x + j]
									.getNumber() != 1
									&& f.getField()[pt.y + i][pt.x
											+ j] != getAster()) {
								range[i][j] = 1;
							}
							// ���g���T���Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						} else {
							range[i][j] = 0;
						}
					}
				}
			}else{
				for (int i = 0; i < defaultRange.length; i++) {
						if (pt.y + i < 0 || pt.y + i >= f.getY())
							continue;
						for (int j = 0; j < defaultRange[0].length; j++) {
							if (pt.x + j < 0 || pt.y + i >= f.getX())
								continue;
	
						if (defaultRange[i][j] == 1) {
							// �����W���Ŏ��g���T���ȊO�Ȃ�I����
							if (getAster().getField().getField()[pt.y + i][pt.x + j]
									.getNumber() != 1
									&& getAster().getField().getField()[pt.y + i][pt.x
											+ j] != getAster()) {
								range[i][j] = 1;
							}
							// ���g���T���Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						} else {
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
		return "�}�[�Y";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�t���A�X�^�[";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�A�X�e��1��j�󂷂� (�T���ɂ͖���)";
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
		getAster().getField().setDeleteFlag(target1);
		getAster().getField().delete(target1.x, target1.y, 0);
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(6);
		}
		return asterImage;
	}

}
