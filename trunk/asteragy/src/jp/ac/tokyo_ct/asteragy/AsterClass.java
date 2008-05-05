/**
 *
 */
package jp.ac.tokyo_ct.asteragy;

import java.util.Vector;

import com.nttdocomo.ui.*;

/**
 * @author Yusuke
 * 
 */
abstract class AsterClass {
	// aster�����͂����ăR�s�[���Ă��Ȃ��B
	protected AsterClass(AsterClass a) {
		game = a.game;
		player = a.player;
		actionCount = a.actionCount;
		mode = a.mode;
		// aster = a.getAster();
		if (a.target1 == null)
			target1 = null;
		else
			target1 = a.target1.clone();
		if (a.target2 == null)
			target2 = null;
		else
			target2 = a.target2.clone();
		isProtected = a.isProtected;
	}

	abstract AsterClass clone();

	AsterClass(Aster a, Player p) {
		aster = a;
		player = p;
		actionCount = getActionNum();
		game = a.field.game;
		a.setAsterClass(this);
	}

	/**
	 * �N���X�̑Ή�����ԍ���Ԃ�
	 * 
	 * @return �N���X�ŗL�̔ԍ�
	 */
	abstract int getNumber();

	final Aster getAster() {
		return aster;
	}

	private Aster aster;

	final Game game;

	final Player getPlayer() {
		return player;
	}

	final void setPlayer(Player p) {
		player = p;
	}

	private Player player;

	final void setCommand(int cmd) {
		mode = cmd;
	}

	final int getCommand() {
		return mode;
	}

	final void setAster(Aster a) {
		aster = a;
	}

	protected int mode = 0;

	protected Point target1 = null;

	protected Point target2 = null;

	/**
	 * ���݂̑I��͈͂�Ԃ�
	 * 
	 * @return ���݂̑I��͈�
	 */
	abstract int[][] getRange();

	/**
	 * @return �͈͂ɖ�肪�Ȃ����true�A�����łȂ����false
	 */
	abstract boolean setPointAndNext(Point pt);

	abstract boolean hasNext();

	/**
	 * 1�O�̑I���ɖ߂�B
	 * 
	 * @return ��ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇtrue
	 */
	abstract boolean moveAstern();

	/**
	 * @return �N���X��
	 */
	final String getName() {
		return AsterClass.className[getNumber() - 1];
	}

	/**
	 * @return ����R�}���h��
	 */
	final String getCommandName() {
		return AsterClass.commandName[getNumber() - 1];
	}

	/**
	 * @return ����R�}���h�̐���
	 */
	final String getExplain() {
		return AsterClass.commandExplain[getNumber() - 1];
	}

	/**
	 * 
	 * @return �N���X�t�^���̃R�X�g
	 */
	final int getCost() {
		return AsterClass.classCost[getNumber() - 1];
	}

	/**
	 * 
	 * @return ����R�}���h�g�p���̃R�X�g
	 */
	final int getCommandCost() {
		// switch (mode) {
		// case 0:
		// return 0;
		// case 1:
		// return AsterClass.commandCost[getNumber()-1];
		// }
		// return 0;
		return AsterClass.commandCost[getNumber() - 1];
	}

	private final void logAction(int commandType, int[] args) {
		Action a = new Action();
		a.aster = aster;
		a.commandType = commandType;
		a.args = args;
		game.logAction(a);
	}

	protected final void logAction(int[] args) {
		logAction(1, args);
	}

	protected final void logAction() {
		logAction(1, new int[0]);
	}

	protected final void logAction(Point pt1) {
		logAction(1, new int[] { pt1.x, pt1.y });
	}

	protected final void logAction(Point pt1, Point pt2) {
		logAction(1, new int[] { pt1.x, pt1.y, pt2.x, pt2.y });
	}

	/**
	 * �R�}���h�����s
	 * 
	 */
	final void execute(String deleteList) {
		final Field field = getAster().field;
		System.out.println("AsterClass.execute()");
		switch (mode) {
		case 0:
			logAction(0,
					new int[] { target1.x, target1.y, target2.x, target2.y });
			field.swap(target1.x, target1.y, target2.x, target2.y);

			// �X���b�v�G�t�F�N�g�B
			field.getCanvas().paintEffect(
					new EffectFieldSwap(field, target1, target2));
			/*
			 * // �T�����Ŕ���i�_�C�A���O�͉��Ȃ̂őR��ׂ����o�ɒu�������Ă����Ă��������j if
			 * (field.judgeSelfDestruction() == true) { Dialog d = new
			 * Dialog(Dialog.DIALOG_YESNO, "����"); d.setText("�T���������܂�");
			 * if(d.show() == Dialog.BUTTON_NO){ field.restoreField();
			 * incActionCount(); break; } }
			 */
			break;
		case 1:
			executeSpecialCommand();
			break;
		}
		// �s���\�񐔂����炷
		decActionCount();

		// ���Ŕ���
		System.out.println("�����J�n");
		final CanvasControl canvas = field.getCanvas();
		player.addAP(field.deleteAll(canvas.disappearControl.disappearing));
		if (deleteList != null && deleteList.length() != 0) {
			Vector v = new Vector();
			for (int i = 0; i + 2 < deleteList.length();) {
				int x = HTTPPlayer.parseIntChar(deleteList.charAt(i++));
				int y = HTTPPlayer.parseIntChar(deleteList.charAt(i++));
				v.addElement(new Point(x, y));
				field.field[y][x].setColor(HTTPPlayer.parseIntChar(deleteList
						.charAt(i++)));
			}
			canvas.disappearControl.disappearing = v;
		}
		game.logDeleteInfo(field, canvas.disappearControl.disappearing);
		System.out.println("��������");
		canvas.paintEffect(canvas.disappearControl);

		// �^�[�Q�b�g������
		target1 = null;
		target2 = null;
	}

	/**
	 * ����R�}���h�����s
	 * 
	 */
	abstract void executeSpecialCommand();

	/**
	 * �s���\�񐔁A�t���O������
	 */
	final void init() {
		// �s���񐔃��Z�b�g
		actionCount = getActionNum();
		// �t���O����
		isProtected = false;

		mode = 0;
	}

	final int getActionNum() {
		return AsterClass.actionNum[getNumber() - 1];
	}

	/**
	 * �s���\�񐔑�
	 */
	final void incActionCount() {
		actionCount++;
	}

	/**
	 * �s���\�񐔌�
	 */
	final void decActionCount() {
		actionCount--;
	}

	final void setActionCount(int i) {
		actionCount = i;
	}

	final int getActionCount() {
		return actionCount;
	}

	/**
	 * �s���\��
	 */
	private int actionCount;

	final void setProtectedFlag(boolean b) {
		isProtected = b;
	}

	final boolean getProtectedFlag() {
		return isProtected;
	}

	/**
	 * �Ώەs�t���O
	 */
	private boolean isProtected;

	/**
	 * ���݂̑I��͈͂�Ԃ� (�X���b�v�p)
	 * 
	 * @return ���݂̑I��͈�
	 */
	protected final int[][] swapGetRange(int[][] defaultRange) {
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		final Field f = aster.field;
		// 1�ڂ̑ΏۑI��
		if (target1 == null) {
			for (int i = 0; i < defaultRange.length; i++) {
				for (int j = 0; j < defaultRange[0].length; j++) {
					// �㉺���E�ɗאڃ����W�������Ǘ����������W�����O
					int fi = i + (f.asterToPoint(aster).y - range.length / 2);
					int fj = j
							+ (f.asterToPoint(aster).x - range[0].length / 2);

					if (i + 1 >= defaultRange.length
							|| defaultRange[i + 1][j] == 0
							|| !f.isYInFieldBound(fi + 1)) {
						if (i - 1 < 0 || defaultRange[i - 1][j] == 0
								|| !f.isYInFieldBound(fi - 1)) {
							if (j + 1 >= defaultRange[0].length
									|| defaultRange[i][j + 1] == 0
									|| !f.isXInFieldBound(fj + 1)) {
								if (j - 1 < 0 || defaultRange[i][j - 1] == 0
										|| !f.isXInFieldBound(fj - 1)) {
									continue;
								}
							}
						}
					}
					// if (defaultRange[i + 1][j] + defaultRange[i - 1][j]
					// + defaultRange[i][j + 1] + defaultRange[i][j - 1] != 0) {
					range[i][j] = defaultRange[i][j];
					// }
				}
			}

		}
		// 2�ڂ̑ΏۑI��
		else {
			// target1�̍��W�������W���ɏC����������
			final Point selftPoint = aster.getPoint();
			Point pt = new Point(target1.x
					- (selftPoint.x - range[0].length / 2), target1.y
					- (selftPoint.y - range.length / 2));
			System.out.println("tx:" + target1.x + " ty:" + target1.y + " spx:"
					+ selftPoint.x + " spy:" + selftPoint.y);
			System.out.println("px:" + pt.x + " py:" + pt.y);

			for (int i = 0; i < range.length; i++) {
				for (int j = 0; j < range[0].length; j++) {
					// 1�ڂ̑Ώۂ̏㉺���E�̃}�X�ŁA���̃����W�Ɋ܂܂�Ă���ꏊ�̂�1
					if (defaultRange[i][j] == 1) {
						if (i == pt.y - 1 && j == pt.x || i == pt.y + 1
								&& j == pt.x || i == pt.y && j == pt.x + 1
								|| i == pt.y && j == pt.x - 1) {
							System.out.println("range i:" + i + " j:" + j);
							range[i][j] = 1;
						}
					}
				}
			}
			System.out.println("test");
			// 1�ڂ̑Ώۂ̈ʒu���ړ��E�I��s��
			range[pt.y][pt.x] = 0;
		}
		System.out.println("SwapGetRange end");
		return range;
	}

	protected final boolean swapMoveAstern() {
		// 1�ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇ
		if (target1 == null) {
			System.out.println("swapMoveAstern return true");
			return true;
		}
		// 2�ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇ
		if (target2 == null) {
			target1 = null;
		}
		// 2�ڂ̑ΏۑI����ɌĂ΂ꂽ�ꍇ
		else {
			target2 = null;
		}
		System.out.println("swapMoveAstern return false");
		return false;
	}

	protected final boolean swapSetPointAndNext(Point pt) {
		if (target1 == null) {
			target1 = pt;
		} else {
			target2 = pt;
		}
		return true;
	}

	protected final boolean swapHasNext() {
		boolean ret = target1 == null || target2 == null;
		System.out.println("swapHasNext return " + ret);
		return ret;
	}

	final static int[][] getDefaultRange(int n) {
		switch (n) {
		case 1:
			return SunClass.getDefaultRange();
		case 2:
			return StarClass.getDefaultRange();
		case 3:
			return MercuryClass.getDefaultRange();
		case 4:
			return VenusClass.getDefaultRange();
		case 5:
			return EarthClass.getDefaultRange();
		case 6:
			return MarsClass.getDefaultRange();
		case 7:
			return JupiterClass.getDefaultRange();
		case 8:
			return SaturnClass.getDefaultRange();
		case 9:
			return UranusClass.getDefaultRange();
		case 10:
			return NeptuneClass.getDefaultRange();
		case 11:
			return PlutoClass.getDefaultRange();
		case 12:
			return MoonClass.getDefaultRange();
		}
		return null;
	}

	abstract Image getImage();

	static Image loadImage(int n) {
		return Game.loadImage("aster_".concat(String.valueOf(n)));
	}

	static final int MAX_CLASS = 12;

	final static String[] className = { "��", "���", "ϰ��ذ", "�ި�Ž", "���",
			"ϰ��", "�ޭ����", "����", "��ǽ", "�������", "��ٰ�", "Ѱ�", };

	final static String[] classNameF = { "�T��", "�X�^�[", "�}�[�L�����[", "���B�[�i�X", "�A�[�X",
			"�}�[�Y", "�W���s�^�[", "�T�^�[��", "�E���k�X", "�l�v�`���[��", "�v���[�g", "���[��", };

	final static String[] commandName = { "�T�����v���l�b�g", "�X���b�v", "�N�C�b�N�^�C��",
			"�e���v�e�[�V����", "�T�������[��", "�A�X�^�[�t���A", "�v���e�N�V�����V�X�e��", "���[�e�[�V����",
			"�g�����X�|�[�g", "�\�j�b�N���[�u", "�f�B�U�X�^�[", "�g�[�^���C�N���v�X", };

	final static String[] commandExplain = { "�A�X�e��1�ɃN���X��^����",
			"2�ׂ̗荇�����A�X�e�������ւ���", "�s���σ��j�b�g1�̂��s���\�ɂ���", "�G���j�b�g1�̂�D�����",
			"�A�X�e��1�Ƀ��[���N���X��^����", "�A�X�e��1��j�󂷂�(�T���͕s��)", "�G�Ư�1�̂�j�󂷂�(�T���͕s��)",
			"�����O�������E���Ƀ��[�e�[�V����", "2�̃A�X�e�������ւ���", "�����ƃA�X�e��1�����ւ���",
			"�����W���̃A�X�e����S�Ĕj�󂷂�", "���g��j�󂵂Ď����̃T�����ړ�", };

	final static int[] classCost = { 0, 2, 6, 5, 4, 8, 7, 11, 10, 11, 12, 0 };

	final static int[] commandCost = { 0, 0, 3, 7, 4, 5, 1, 4, 5, 6, 18, 4 };

	final static int[] actionNum = { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3 };
}
