package jp.ac.tokyo_ct.asteragy;

public class StarClass extends AsterClass {
	private static int[][] defaultRange = {
		{0,1,0},
		{1,1,1},
		{0,0,0}
	};
	StarClass(Aster a,Player p){
		super(a,p);
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		return swapGetRange(defaultRange,target1);
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		return swapSetPointAndNext(pt,target1,target2);
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return swapHasNext(target1,target2);
	}

	public void moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		swapMoveAstern(target1,target2);
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public void execute(){
		getAster().getField().swap(target1,target2);
		
		incActionCount();
		target1 = null;
		target2 = null;
	}
	public String getName(){
		return "スター";
	}
	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	public void executeSpecialCommand(){
	
	}

}
