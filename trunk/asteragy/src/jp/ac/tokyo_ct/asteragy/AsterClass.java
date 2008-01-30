/**
 *
 */
package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * @author Yusuke
 * 
 */
public abstract class AsterClass {

	public AsterClass(Aster a, Player p) {
		aster = a;
		player = p;
		actionCount = getActionNum();
	}

	/**
	 * �N���X�̑Ή�����ԍ���Ԃ�
	 * 
	 * @return �N���X�ŗL�̔ԍ�
	 */
	public abstract int getNumber();

	public Aster getAster() {
		return aster;
	}

	/**
	 * �Ή�����A�X�^�[��ݒ肷��
	 * 
	 * @param a
	 *            �A�X�^�[ ���̃��\�b�h�́AAster.setAsterClass����Ă΂�邽�߂ɂ���B ����𒼐ڌĂяo���Ȃ����ƁB
	 */
	void setAster(Aster a) {
		aster = a;
	}

	private Aster aster;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	private Player player;

	public void setCommand(int cmd) {
		mode = cmd;
	}

	public int getCommand() {
		return mode;
	}

	protected int mode = 0;

	protected Point target1 = null;

	protected Point target2 = null;

	/**
	 * ���݂̑I��͈͂�Ԃ�
	 * 
	 * @return ���݂̑I��͈�
	 */
	public abstract int[][] getRange();

	/**
	 * @return �͈͂ɖ�肪�Ȃ����true�A�����łȂ����false
	 */
	public abstract boolean setPointAndNext(Point pt);

	public abstract boolean hasNext();

	/**
	 * 1�O�̑I���ɖ߂�B
	 * 
	 * @return ��ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇtrue
	 */
	public abstract boolean moveAstern();

	/**
	 * @return �N���X��
	 */
	public String getName() {
		return AsterClassData.className[getNumber() - 1];
	}

	/**
	 * @return ����R�}���h��
	 */
	public String getCommandName() {
		return AsterClassData.commandName[getNumber() - 1];
	}

	/**
	 * @return ����R�}���h�̐���
	 */
	public String getExplain() {
		return AsterClassData.commandExplain[getNumber() - 1];
	}

	/**
	 * 
	 * @return �N���X�t�^���̃R�X�g
	 */
	public int getCost() {
		return AsterClassData.classCost[getNumber() - 1];
	}

	/**
	 * 
	 * @return ����R�}���h�g�p���̃R�X�g
	 */
	public int getCommandCost() {
		// switch (mode) {
		// case 0:
		// return 0;
		// case 1:
		// return AsterClassData.commandCost[getNumber()-1];
		// }
		// return 0;
		return AsterClassData.commandCost[getNumber() - 1];
	}
	
	private void logAction(int commandType, int[] args) {
		Action a = new Action(player);
		a.commandType = commandType;
		a.args = args;
		Main.game.logAction(a);
	}

	protected void logAction(int[] args) {
		logAction(1, args);
	}
	/**
	 * �R�}���h�����s
	 * 
	 */
	public void execute() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		final Field field = getAster().getField();
		System.out.println("----AsterClass.execute()");
		switch (mode) {
		case 0:
			/*
			field.backupField();
			field.swap(target1, target2);
			// �T�����Ŕ���i�_�C�A���O�͉��Ȃ̂őR��ׂ����o�ɒu�������Ă����Ă��������j
			if (field.judgeSelfDestruction() == true) {
				Dialog d = new Dialog(Dialog.DIALOG_YESNO, "����");
				d.setText("�T���������܂�");
				if(d.show() == Dialog.BUTTON_NO){
					field.restoreField();
					incActionCount();
				}
			}
			*/
			// �������Ă��̂łƂ肠�����R�����g�A�E�g

			field.swap(target1, target2);
			logAction(0, new int[] {target1.x, target1.y, target2.x, target2.y});
			break;
		case 1:
			executeSpecialCommand();
			break;
		}
		// �s���\�񐔂����炷
		decActionCount();
		// �^�[�Q�b�g������
		target1 = null;
		target2 = null;
	}

	/**
	 * ����R�}���h�����s
	 * 
	 */
	public abstract void executeSpecialCommand();

	/**
	 * �s���\�񐔁A�t���O������
	 */
	public void init() {
		// �s���񐔃��Z�b�g
		actionCount = getActionNum();
		// �t���O����
		isProtected = false;

		aster.getField().repaintAster(aster.getPoint());
	}

	public int getActionNum() {
		return AsterClassData.actionNum[getNumber() - 1];
	}

	/**
	 * �s���\�񐔑�
	 */
	public void incActionCount() {
		actionCount++;
	}

	/**
	 * �s���\�񐔌�
	 */
	public void decActionCount() {
		actionCount--;
	}

	public void setActionCount(int i) {
		actionCount = i;
	}

	public int getActionCount() {
		return actionCount;
	}

	/**
	 * �s���\��
	 */
	private int actionCount;

	public void setProtectedFlag(boolean b) {
		isProtected = b;
	}

	public boolean getProtectedFlag() {
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
	protected int[][] swapGetRange(int[][] defaultRange) {
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		// 1�ڂ̑ΏۑI��
		if (target1 == null) {

			for (int i = 0; i < defaultRange.length; i++) {
				for (int j = 0; j < defaultRange[0].length; j++) {
					// �㉺���E�ɗאڃ����W�������Ǘ����������W�����O
					// if (defaultRange[i + 1][j] + defaultRange[i - 1][j]
					// + defaultRange[i][j + 1] + defaultRange[i][j - 1] != 0) {
					range[i][j] = defaultRange[i][j];
					// }
				}
			}

		}
		// 2�ڂ̑ΏۑI��
		else {
			range = new int[defaultRange.length][defaultRange[0].length];
			// target1�̍��W�������W���ɏC����������
			Point selftPoint = getAster().getPoint();
			Point pt = new Point();
			System.out.println("tx:" + target1.x + "ty:" + target1.y + "spx:"
					+ selftPoint.x + "spy:" + selftPoint.y);
			pt.x = target1.x - (selftPoint.x - range[0].length / 2);
			pt.y = target1.y - (selftPoint.y - range.length / 2);
			System.out.println("px:" + pt.x + "py:" + pt.y);

			for (int i = 0; i < range.length; i++) {
				for (int j = 0; j < range[0].length; j++) {
					// 1�ڂ̑Ώۂ̏㉺���E�̃}�X�ŁA���̃����W�Ɋ܂܂�Ă���ꏊ�̂�1
					if (defaultRange[i][j] == 1) {
						if (i == pt.y - 1 && j == pt.x ||
							i == pt.y + 1 && j == pt.x ||
							i == pt.y && j == pt.x + 1 ||
							i == pt.y && j == pt.x - 1) {
							range[i][j] = 1;
						}
					}
				}
			}
			// 1�ڂ̑Ώۂ̈ʒu���ړ��E�I��s��
			range[pt.y][pt.x] = 0;
		}
		return range;
	}

	protected boolean swapMoveAstern() {
		// 1�ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇ
		if (target1 == null) {
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
		return false;
	}

	protected boolean swapSetPointAndNext(Point pt) {
		if (target1 == null) {
			target1 = pt;
		} else {
			target2 = pt;
		}
		return true;
	}

	protected boolean swapHasNext() {
		boolean ret = target1 == null || target2 == null;
		System.out.println("swapHasNext return " + ret);
		return ret;
	}

	public abstract Image getImage();

	static Image loadImage(int n) {
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///aster_" + n
					+ ".gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}

	protected static boolean isYInFieldBound(Field f, int y) {
		return 0 <= y && y < f.getY();
	}

	protected static boolean isXInFieldBound(Field f, int x) {
		return 0 <= x && x < f.getX();
	}
}
