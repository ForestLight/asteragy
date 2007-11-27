package jp.ac.tokyo_ct.asteragy;

public class NeptuneClass extends AsterClass {
	private static int[][] defaultRange = 
	{
		{0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0},
		{0,0,1,1,1,0,0},
		{1,1,1,1,1,1,1},
		{0,0,1,1,1,0,0},
		{0,0,0,1,0,0,0},
		{0,0,0,1,0,0,0}
	};
	
	private Point target = null;
	
	public NeptuneClass(Aster a){
		super(a);
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 10;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return defaultRange;
	}

	public boolean setPointAndNext(Point pt) {
		target = pt;
		return true;
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if(target == null) return true;
		else return false;
	}

	public void moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�X�^�[���C�g�X�g���[��";
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return "�Ώۂ̃A�X�e��1��\n���̃��j�b�g�̏ꏊ������";
	}

}
