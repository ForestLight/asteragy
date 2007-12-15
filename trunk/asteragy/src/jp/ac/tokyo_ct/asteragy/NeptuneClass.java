package jp.ac.tokyo_ct.asteragy;
import com.nttdocomo.ui.*;

public class NeptuneClass extends AsterClass {
	private static int[][] defaultRange = {
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 1, 1, 1, 1, 1, 1, 1 },
		{ 0, 0, 1, 1, 1, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 0, 0, 1, 0, 0, 0 }
	};
	private static Image asterImage;
	public NeptuneClass(Aster a,Player p){
		super(a,p);
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 10;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			return swapGetRange(defaultRange,target1);
		case 1:
			return defaultRange;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
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

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "スターライトストリーム";
	}
	public void executeSpecialCommand(){
		//ターゲットと自分をswap
		getAster().getField().swap(target1,getAster().getField().asterToPoint(getAster()));
	}

	public String getName(){
		return "ネプチューン";
	}
	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "対象のアステル1個と\nこのユニットの場所を交換";
	}

	public int getCost() {
		return 11;
	}

	public int getCommandCost() {
		return 2;
	}
	
	public Image getImage(){
		if(asterImage == null){
			asterImage = loadImage(10);
		}
		return asterImage;
	}
	public int getActionNum(){
		return 1;
	}
}
