package jp.ac.tokyo_ct.asteragy;

public class JupiterClass extends AsterClass {
	private static int[][] defaultRange = {
		{0, 0, 0, 0, 0},
		{0, 0, 1, 0, 0},
		{0, 1, 1, 1, 0},
		{1, 1, 1, 1, 1},
		{0, 1, 1, 1, 0}
	};
	public JupiterClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 7;
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
			
			//対象選択1個目(敵ユニットのみ)
			if(target1 == null){
				for(int i = 0;i < defaultRange.length;i++){
					if(pt.y+i < 0 || pt.y + i >= getAster().getField().getField().length) continue;
					for(int j = 0;j < defaultRange[0].length;j++){
						if(pt.x+j<0 || pt.x + j >= getAster().getField().getField()[0].length) continue;
						
						//レンジ内であり
						if(defaultRange[i][j] == 1){
							//その位置のアステルにクラスがあり
							if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass() != null){
								//そのクラスの所持者が自分ではなければ選択可能
								if(getAster().getField().getField()[pt.y+i][pt.x+j].getAsterClass().getPlayer() != getPlayer()){
									range[i][j] = 1;
								}
							}
						}
					}
				}
			}
			//対象選択2個目 (アステル)
			else{
				//ターゲット1のレンジ内での位置
				pt.x = target1.x - (getPlayer().game.getField().asterToPoint(getAster()).x - range[0].length/2);
				pt.y = target1.y - (getPlayer().game.getField().asterToPoint(getAster()).y - range.length/2);
				
				for(int i = 0;i < defaultRange.length;i++){
					for(int j = 0;j < defaultRange[0].length;j++){
						if(defaultRange[i][j] == 1){
							if(i != pt.y && j != pt.x){
								range[i][j] = 1;
							}
						}
					}
				}
			}
			return range;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		
		//スワップの場合もコマンドの場合も同じ
		swapSetPointAndNext(pt,target1,target2);
		return false;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return swapHasNext(target1,target2);
	}

	public void moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		swapMoveAstern(target1,target2);
	}

	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ジュピター";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "プロテクションシステム";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵ユニット1体とアステル1個を入れ替える";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 7;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		getAster().getField().swap(target1,target2);

	}

}
