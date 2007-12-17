package jp.ac.tokyo_ct.asteragy;
import com.nttdocomo.ui.*;

public class UranusClass extends AsterClass {
	private static int[][] defaultRange = {
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 0, 0, 0, 1, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 1, 0, 1, 1, 1, 0, 1 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 0, 0, 0, 1, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
	};
	private static Image asterImage;

	public UranusClass(Aster a, Player p) {
		super(a, p);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 9;
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

			// 1��
			if(target1 == null){
				for(int i = 0;i < defaultRange.length;i++){
					if(pt.y+i < 0 || pt.y + i >= getAster().getField().getField().length) continue;
					for(int j = 0;j < defaultRange[0].length;j++){
						if(pt.x+j<0 || pt.x + j >= getAster().getField().getField()[0].length) continue;
				
						if(defaultRange[i][j] == 1){
							// �����W���Ŏ��g�ȊO�Ȃ�I����
							if(getAster().getField().getField()[pt.y+i][pt.x+j] != getAster()) {
								range[i][j] = 1;
							}
							// ���g�Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						}
						else {
							range[i][j] = 0;
						}
					}
				}
			}
			// 2��
			else{
				for(int i = 0;i < defaultRange.length;i++){
					if(pt.y+i < 0 || pt.y + i >= getAster().getField().getField().length) continue;
					for(int j = 0;j < defaultRange[0].length;j++){
						if(pt.x+j<0 || pt.x + j >= getAster().getField().getField()[0].length) continue;
				
						if(defaultRange[i][j] == 1){
							// �����W���Ŏ��g��1�ڂ̏ꏊ�ȊO�Ȃ�I����
							if(getAster().getField().getField()[pt.y+i][pt.x+j] != getAster()
									&& getAster().getField().asterToPoint(getAster()) != target1) {
								range[i][j] = 1;
							}
							// ���g�Ȃ�ړ��̂݉�
							else {
								range[i][j] = 0;
							}
						}
						else {
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

		// �X���b�v�̏ꍇ���R�}���h�̏ꍇ������
		return swapSetPointAndNext(pt, target1, target2);
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapHasNext(target1, target2);
	}

	public void moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		swapMoveAstern(target1, target2);
	}

	public String getName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�E���k�X";
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�����O�����W�X���b�v";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�A�X�e��2�i���������j�����ւ���";
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 10;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		getAster().getField().swap(target1, target2);
	}

	public Image getImage(){
		if(asterImage == null){
			asterImage = loadImage(9);
		}
		return asterImage;
	}

	public int getActionNum(){
		return 1;
	}

}
