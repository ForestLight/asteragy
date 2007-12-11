package jp.ac.tokyo_ct.asteragy;

public class VenusClass extends AsterClass {
	private static int[][] defaultRange = {
		{0, 0, 1, 0, 0},
		{0, 1, 1, 1, 0},
		{0, 1, 1, 1, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0}
	};
	private Point target1 = null;
	private Point target2 = null;
	private int mode = 0;
	
	public VenusClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 4;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			return swapGetRange(defaultRange,target1);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			//�����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = getAster().getField().asterToPoint(getAster()).x-(range[0].length/2);
			pt.y = getAster().getField().asterToPoint(getAster()).y-(range.length/2);
			
			for(int i = 0;i+pt.y < defaultRange.length;i++){
				if(pt.y+i < 0) continue;
				for(int j = 0;j+pt.x < defaultRange[0].length;j++){
					if(pt.x+j<0) continue;
					
					//�����W���ł���
					if(defaultRange[i][j] == 1){
						//���̈ʒu�̃A�X�e���ɃN���X������
						if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass() != null){
							//���̃N���X�̏����҂�����ł���
							if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass().getPlayer() != getPlayer()){
								//�T���łȂ���ΑΏۂɑI���\
								if(getAster().getField().getField()[pt.y+i][pt.x+j].getNumber() != 1)
								range[i][j] = 1;
							}
						}
					}
				}
			}
			return defaultRange;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			return swapSetPointAndNext(pt,target1,target2);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			return swapHasNext(target1,target2);
		case 1:
			if (target1 == null)
				return true;
			else
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

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�e���v�e�[�V����";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�G���j�b�g��̂������̃��j�b�g�ɂ���";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 7;
	}

	public String getName(){
		return "���B�[�i�X";
	}
	public void execute() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch(mode){
		case 0:
			getAster().getField().swap(target1,target2);
			break;
		case 1:
			//�Ώۂ̏����҂�ύX
			getAster().getField().getAster(target1).getAsterClass().setPlayer(this.getPlayer());
			//�s���Ϗ�Ԃ�
			getAster().getField().getAster(target1).getAsterClass().setActionCount(0);
		}
		incActionCount();
		target1 = null;
		target2 = null;
	}

}