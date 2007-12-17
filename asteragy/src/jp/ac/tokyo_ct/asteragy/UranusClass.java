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
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 9;
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

			// 1個目
			if(target1 == null){
				for(int i = 0;i < defaultRange.length;i++){
					if(pt.y+i < 0 || pt.y + i >= getAster().getField().getField().length) continue;
					for(int j = 0;j < defaultRange[0].length;j++){
						if(pt.x+j<0 || pt.x + j >= getAster().getField().getField()[0].length) continue;
				
						if(defaultRange[i][j] == 1){
							// レンジ内で自身以外なら選択可
							if(getAster().getField().getField()[pt.y+i][pt.x+j] != getAster()) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
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
			// 2個目
			else{
				for(int i = 0;i < defaultRange.length;i++){
					if(pt.y+i < 0 || pt.y + i >= getAster().getField().getField().length) continue;
					for(int j = 0;j < defaultRange[0].length;j++){
						if(pt.x+j<0 || pt.x + j >= getAster().getField().getField()[0].length) continue;
				
						if(defaultRange[i][j] == 1){
							// レンジ内で自身と1個目の場所以外なら選択可
							if(getAster().getField().getField()[pt.y+i][pt.x+j] != getAster()
									&& getAster().getField().asterToPoint(getAster()) != target1) {
								range[i][j] = 1;
							}
							// 自身なら移動のみ可
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
		// TODO 自動生成されたメソッド・スタブ

		// スワップの場合もコマンドの場合も同じ
		return swapSetPointAndNext(pt, target1, target2);
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return swapHasNext(target1, target2);
	}

	public void moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		swapMoveAstern(target1, target2);
	}

	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ウラヌス";
	}

	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ロングレンジスワップ";
	}

	public String getExplain() {
		// TODO 自動生成されたメソッド・スタブ
		return "アステル2個（自分除く）を入れ替える";
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 10;
	}

	public int getCommandCost() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public void executeSpecialCommand() {
		// TODO 自動生成されたメソッド・スタブ
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
