package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class JupiterClass extends AsterClass {
	private static int[][] defaultRange = { 
			{ 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 }, 
			{ 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 } };

	private static Image asterImage;

	public JupiterClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = getAster().getField().asterToPoint(getAster()).x
					- (range[0].length / 2);
			pt.y = getAster().getField().asterToPoint(getAster()).y
					- (range.length / 2);

			// �ΏۑI��1��(�G���j�b�g�̂�)
			if (target1 == null) {
				for (int i = 0; i < defaultRange.length; i++) {
					if (pt.y + i < 0
							|| pt.y + i >= getAster().getField().getField().length)
						continue;
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (pt.x + j < 0
								|| pt.x + j >= getAster().getField().getField()[0].length)
							continue;

						// �����W���ł���
						if (defaultRange[i][j] == 1) {
							// ���̈ʒu�̃A�X�e���ɃN���X������
							if (getAster().getField().getField()[pt.y + i][pt.x
									+ j].getAsterClass() != null) {
								// ���̃N���X�̏����҂������ł͂Ȃ���ΑI���\
								if (getAster().getField().getField()[pt.y + i][pt.x
										+ j].getAsterClass().getPlayer() != getPlayer()) {
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
				pt.x = target1.x
						- (getPlayer().game.getField().asterToPoint(getAster()).x - range[0].length / 2);
				pt.y = target1.y
						- (getPlayer().game.getField().asterToPoint(getAster()).y - range.length / 2);

				for (int i = 0; i < defaultRange.length; i++) {
					for (int j = 0; j < defaultRange[0].length; j++) {
						if (defaultRange[i][j] == 1) {
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

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		// �X���b�v�̏ꍇ���R�}���h�̏ꍇ������
		return swapSetPointAndNext(pt);
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapHasNext();
	}

	public boolean moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapMoveAstern();
	}

	public String getName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�W���s�^�[";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�v���e�N�V�����V�X�e��";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�G���j�b�g1�̂ƃA�X�e��1�����ւ���";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		getAster().getField().swap(target1, target2);

	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(7);
		}
		return asterImage;
	}

	public int getActionNum() {
		return 1;
	}
}
