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
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 5;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			return swapGetRange(defaultRange,target1);
		case 1:
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			return swapSetPointAndNext(pt,target1,target2);
		case 1:
		}
		
		return false;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			return swapHasNext(target1,target2);
		case 1:
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

	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "アース";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "イージス";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "レンジ内の味方ユニットは、次のターン対象にならない";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 6;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
		Point me = getAster().getField().asterToPoint(getAster());
		Point pt = new Point();
		for(int i = 0;i < defaultRange.length;i++){
			for(int j = 0;j < defaultRange[0].length;j++){
				//レンジ内であり
				if(defaultRange[i][j] == 1){
					pt.x = me.x-defaultRange.length+j;
					pt.y = me.y-defaultRange[0].length+i;
					
					//フィールドの外にはみ出してたら処理しない
					if(pt.x < 0 || pt.x >= getAster().getField().getField()[0].length) continue;
					if(pt.y < 0 || pt.y >= getAster().getField().getField().length) continue;
					
					//クラス持ちであり
					if(getAster().getField().getAster(pt).getAsterClass() != null){
						//このユニットと同一のプレイヤーが所持しているのなら
						if(getAster().getField().getAster(pt).getAsterClass().getPlayer() == this.getPlayer()){
							//対象不可フラグを建てる
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
