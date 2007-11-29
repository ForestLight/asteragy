package jp.ac.tokyo_ct.asteragy;

public class StarClass extends AsterClass {
	private static int[][] defaultRange = {
		{0,1,0},
		{1,1,1},
		{0,0,0}
	};
	private Point target1 = null;
	private Point target2 = null;
	
	StarClass(Aster a,Player p){
		super(a,p);
	}

	public int getNumber() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 2;
	}

	public int[][] getRange() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapGetRange(defaultRange,target1);
	}

	public boolean setPointAndNext(Point pt) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapSetPointAndNext(pt,target1,target2);
	}

	public boolean hasNext() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return swapHasNext(target1,target2);
	}

	public void moveAstern() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		swapMoveAstern(target1,target2);
	}

	public String getCommandName() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	
	public void execute(){
		getAster().getField().swap(target1,target2);
	}

	public String getExplain() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	public int getCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 2;
	}

	public int getCommandCost() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	public void setCommand(int cmd) {		
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}