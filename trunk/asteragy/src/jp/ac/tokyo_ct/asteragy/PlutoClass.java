package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class PlutoClass extends AsterClass {
	private static int[][] defaultRange = { { 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 1 } };

	private static Image asterImage;

	public PlutoClass(Aster a, Player p) {
		super(a, p);
	}

	public PlutoClass(PlutoClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new PlutoClass(this);
	}

	public int getNumber() {
		return 11;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return null;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
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

	public void executeSpecialCommand() {
		System.out.println("�邢�񂭂炷��");
		final Field field = getAster().getField();
		Point me = field.asterToPoint(getAster());
		Point pt = new Point();
		final int rangeY = defaultRange.length;
		final int rangeX = defaultRange[0].length;

		for (int i = 0; i < rangeY; i++) {
			for (int j = 0; j < rangeX; j++) {
				// �����W���ł���
				if (defaultRange[i][j] == 1) {
					// ���g�ł͂Ȃ�������j��
					if (i != rangeY / 2 || j != rangeX / 2) {
						pt.x = me.x - rangeX / 2 + j;
						pt.y = me.y - rangeY / 2 + i;

						// �t�B�[���h�̊O�ɂ͂ݏo���Ă��珈�����Ȃ�
						if (!field.isXInFieldBound(pt.x))
							continue;
						if (!field.isYInFieldBound(pt.y))
							continue;

						// �j��Ώۂ�deleteFlag
						System.out.println("ruincrust target" + pt.x + ","
								+ pt.y);
						field.setDeleteFlag(pt);
					}
				}
			}
		}
		// �T�����Ŕ���i�_�C�A���O�͉��Ȃ̂őR��ׂ����o�ɒu�������Ă����Ă��������j
		if (field.judgeSelfDestruction() == true) {
			Dialog d = new Dialog(Dialog.DIALOG_YESNO, "����");
			d.setText("�T���������܂�");
			if (d.show() == Dialog.BUTTON_NO) {
				// field.removeDeleteFlagAll();
				incActionCount();
				getPlayer().addAP(getCommandCost());
				return;
			}
		} else {
			Effect effect = new EffectCommandPluto(field, me);
			getAster().getField().getScreen().paintEffect(effect);
			field.deleteAll();
			logAction();
		}
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(11);
		}
		return asterImage;
	}
}
