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
		// TODO 自動生成されたメソッド・スタブ
		return 10;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		return defaultRange;
	}

	public boolean setPointAndNext(Point pt) {
		target = pt;
		return true;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		if(target == null) return true;
		else return false;
	}

	public void moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "スターライトストリーム";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "対象のアステル1個と\nこのユニットの場所を交換";
	}

}
