/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke
 *
 */
public abstract class AsterClass {
	
	public AsterClass(Aster a) {
		aster = a;
	}
	
	/**
	 * �N���X�̑Ή�����ԍ���Ԃ�
	 * @return �N���X�ŗL�̔ԍ�
	 */
	public abstract int getNumber();
	
	public Aster getAster() {
		return aster;
	}
	
	void setAster(Aster a) {
		aster = a;
	}
	
	private Aster aster;
	
	public Player getPlayer() {
		return player;
	}
	
	private Player player;
	
	/**
	 * ���݂̑I��͈͂�Ԃ�
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
	 */
	public abstract void moveAstern();
	
	/**
	 * @return ����R�}���h�̖���
	 */
	public abstract String getCommandName();
	
	/**
	 * @return ����R�}���h�̐���
	 */
	public abstract String getExplain();
	
	/**
	 * �s��������
	 */
	protected int actionCount;
	
	/**
	 * �Ώەs�t���O
	 */
	protected boolean isProtected;

}