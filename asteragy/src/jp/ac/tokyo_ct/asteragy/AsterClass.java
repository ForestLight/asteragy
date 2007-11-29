/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke
 * 
 */
public abstract class AsterClass {

	public AsterClass(Aster a,Player p) {
		aster = a;
		player = p;
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

	void setAster(Aster a) {
		aster = a;
	}

	private Aster aster;

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player p){
		player = p;
	}

	private Player player;

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
	 * 
	 * @return �N���X�t�^���̃R�X�g
	 */
	public abstract int getCost();

	/**
	 * 
	 * @return ����R�}���h�g�p���̃R�X�g
	 */
	public abstract int getCommandCost();
	
	/**
	 * �R�}���h��ʂ̐ݒ�
	 * @param cmd
	 */
	public abstract void setCommand(int cmd);
	
	/**
	 * �R�}���h�����s
	 *
	 */
	public abstract void execute();

	/**
	 * �s��������
	 */
	protected int actionCount;

	/**
	 * �Ώەs�t���O
	 */
	protected boolean isProtected;
	
	/**
	 * ���݂̑I��͈͂�Ԃ� (�X���b�v�p)
	 * 
	 * @return ���݂̑I��͈�
	 */
	protected int[][] swapGetRange(int[][] defaultRange,Point target1){
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		//1�ڂ̑ΏۑI��
		if(target1 == null){
			for(int i = 0;i < defaultRange.length;i++){
				for(int j = 0;j < defaultRange[0].length;j++){
					//�㉺���E�ɗאڃ����W�������Ǘ����������W�����O
					if(defaultRange[i+1][j]+defaultRange[i-1][j]+defaultRange[i][j+1]+defaultRange[i][j-1] != 0)
						range[i][j] = defaultRange[i][j];
				}
			}
		//2�ڂ̑ΏۑI��
		}else{
			//target1�̍��W�������W���ɏC����������
			Point pt = new Point();
			pt.x = target1.x - (getPlayer().game.getField().asterToPoint(getAster()).x - range[0].length/2);
			pt.y = target1.y - (getPlayer().game.getField().asterToPoint(getAster()).y - range.length/2);

			for(int i = 0;i < range.length;i++){
				for(int j = 0;j < range.length;j++){
					//1�ڂ̑Ώۂ̏㉺���E�̃}�X�ŁA���̃����W�Ɋ܂܂�Ă���ꏊ�̂�1
					if(i == pt.y-1 && j == pt.x && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y+1 && j == pt.x && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y && j == pt.x+1 && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y && j == pt.x-1 && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}else{
						//1�ڂ̑Ώۂ̏㉺���E�ȊO�ړ��s��
						range[j][i] = -1;
					}
				}
			}
			//1�ڂ̑Ώۂ̈ʒu���ړ��E�I��s��
			range[pt.y][pt.x] = 0;
		}
		return range;
	}
	protected void swapMoveAstern(Point target1,Point target2){
		//2�ڂ̑ΏۑI�𒆂ɌĂ΂ꂽ�ꍇ
		if(target2 == null){
			target1 = null;
		}
		//2�ڂ̑ΏۑI����ɌĂ΂ꂽ�ꍇ
		else{
			target2 = null;
		}
	}
	protected boolean swapSetPointAndNext(Point pt,Point target1,Point target2){
		if(target1 == null){
			target1 = pt;
		}else{
			target2 = pt;
		}
		return true;
	}
	protected boolean swapHasNext(Point target1,Point target2){
		if(target1 != null && target2 != null) return false;
		return true;
	}
}