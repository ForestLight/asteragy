package jp.ac.tokyo_ct.asteragy;
import com.nttdocomo.ui.*;

public class EarthClass extends AsterClass {
	private static int[][] defaultRange = {
		{1, 1, 1},
		{1, 1, 1},
		{1, 1, 1}
	};
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
		switch(mode){
		case 0:
			return swapGetRange(defaultRange,target1);
		case 1:
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			return swapSetPointAndNext(pt,target1,target2);
		case 1:
		}
		
		return false;
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			return swapHasNext(target1,target2);
		case 1:
			return false;
		}
		return false;
	}

	public void moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			swapMoveAstern(target1,target2);
			break;
		case 1:
			break;
		}

	}

	public String getName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�A�[�X";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�C�[�W�X";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�����W���̖������j�b�g�́A���̃^�[���ΏۂɂȂ�Ȃ�";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 6;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for(int i = 0;i < defaultRange.length;i++){
			for(int j = 0;j < defaultRange[0].length;j++){
				//�����W���ł���
				if(defaultRange[i][j] == 1){
					pt.x = me.x-defaultRange.length+j;
					pt.y = me.y-defaultRange[0].length+i;
					
					//�t�B�[���h�̊O�ɂ͂ݏo���Ă��珈�����Ȃ�
					if(pt.x < 0 || pt.x >= getAster().getField().getField()[0].length) continue;
					if(pt.y < 0 || pt.y >= getAster().getField().getField().length) continue;
					
					//�N���X�����ł���
					if(getAster().getField().getAster(pt).getAsterClass() != null){
						//���̃��j�b�g�Ɠ���̃v���C���[���������Ă���̂Ȃ�
						if(getAster().getField().getAster(pt).getAsterClass().getPlayer() == this.getPlayer()){
							//�Ώەs�t���O�����Ă�
							getAster().getField().getAster(pt).getAsterClass().setProtectedFlag(true);
						}
					}
				}
			}
		}

	}
	public Image getImage(){
		if(asterImage == null){
			asterImage = loadImage(5);
		}
		return asterImage;
	}
	public int getActionNum(){
		return 1;
	}
}
