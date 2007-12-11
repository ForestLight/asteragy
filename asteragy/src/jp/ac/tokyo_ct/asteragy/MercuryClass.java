package jp.ac.tokyo_ct.asteragy;

public class MercuryClass extends AsterClass {
	private static int[][] defaultRange = {
		{0, 0, 0, 0, 0},
		{0, 1, 1, 1, 0},
		{1, 1, 1, 1, 1},
		{0, 1, 1, 1, 0},
		{0, 0, 0, 0, 0}
	};
	private Point target1 = null;
	private Point target2 = null;
	private int mode = 0;
	
	public MercuryClass(Aster a,Player p){
		super(a,p);
	}
	
	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 3;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ		
		switch(mode){
		case 0:
			return swapGetRange(defaultRange,target1);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			//レンジの左上の座標のフィールド内での位置
			Point pt = new Point();
			pt.x = getAster().getField().asterToPoint(getAster()).x-(range[0].length/2);
			pt.y = getAster().getField().asterToPoint(getAster()).y-(range.length/2);
			
			for(int i = 0;i+pt.y < defaultRange.length;i++){
				if(pt.y+i < 0) continue;
				for(int j = 0;j+pt.x < defaultRange[0].length;j++){
					if(pt.x+j<0) continue;
					
					//レンジ内であり
					if(defaultRange[i][j] == 1){
						//その位置のアステルにクラスがあり
						if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass() != null){
							//そのクラスの所持者が自分であり
							if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass().getPlayer() == getPlayer()){
								//1回以上行動していたならば選択可能
								if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass().getActionCount() != 0){
									range[i][j] = 1;
								}
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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			swapMoveAstern(target1,target2);
			break;
		case 1:
			break;
		}

	}
	public String getName(){
		return "マーキュリー";
	}
	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "クイックタイム";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "ほりゅう";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 4;
	}

	public void execute() {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			getAster().getField().swap(target1,target2);
			break;
		case 1:
			//対象の行動回数を1回減らす
			getAster().getField().getAster(target1).getAsterClass().decActionCount();                               

		}
		incActionCount();
		target1 = null;
		target2 = null;
	}

}
