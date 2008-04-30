package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class PlutoClass extends AsterClass {
	private static final int[][] defaultRange = { { 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 1 } };

	private static Image asterImage;

	PlutoClass(Aster a, Player p) {
		super(a, p);
	}

	PlutoClass(PlutoClass a) {
		super(a);
	}

	AsterClass clone() {
		return new PlutoClass(this);
	}

	int getNumber() {
		return 11;
	}

	int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return defaultRange;
		}
		return null;
	}

	boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			return false;
		}
		return false;
	}

	boolean hasNext() {
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			return false;
		}
		return false;
	}

	boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return false;
		}
		return false;
	}

	void executeSpecialCommand() {
		System.out.println("�邢�񂭂炷��");
		final Field field = getAster().field;
		Point me = field.asterToPoint(getAster());
		Point pt = new Point();
		final int rangeY = defaultRange.length;
		final int rangeX = defaultRange[0].length;
		logAction();

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
//		if (field.judgeSelfDestruction() == true) {
//			Dialog d = new Dialog(Dialog.DIALOG_YESNO, "����");
//			d.setText("�T���������܂�");
//			if (d.show() == Dialog.BUTTON_NO) {
//				// field.removeDeleteFlagAll();
//				incActionCount();
//				getPlayer().addAP(getCommandCost());
//				return;
//			}
//		} else {
			game.getCanvas().paintEffect(new EffectCommandPluto(field, me));
			field.deleteAll(game.getCanvas().disappearControl.disappearing);
			logAction();
	//	}
	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(11);
		}
		return asterImage;
	}
}
