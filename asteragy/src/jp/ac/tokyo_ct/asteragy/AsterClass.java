/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;
import com.nttdocomo.ui.*;
/**
 * @author Yusuke
 * 
 */
public abstract class AsterClass {

	public AsterClass(Aster a,Player p) {
		aster = a;
		player = p;
	}

	/**
	 * クラスの対応する番号を返す
	 * 
	 * @return クラス固有の番号
	 */
	public abstract int getNumber();

	public Aster getAster() {
		return aster;
	}

	void setAster(Aster a) {
		aster = a;
	}

	private Aster aster;

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player p){
		player = p;
	}

	private Player player;

	public void setCommand(int cmd){
		mode = cmd;
	}
	public int getCommand(){
		return mode;
	}
	protected int mode = 0;
	protected Point target1 = null;
	protected Point target2 = null;
	/**
	 * 現在の選択範囲を返す
	 * 
	 * @return 現在の選択範囲
	 */
	public abstract int[][] getRange();

	/**
	 * @return 範囲に問題がなければtrue、そうでなければfalse
	 */
	public abstract boolean setPointAndNext(Point pt);

	public abstract boolean hasNext();

	/**
	 * 1つ前の選択に戻る。
	 */
	public abstract void moveAstern();

	/**
	 * @return クラス名
	 */
	public abstract String getName();
	
	/**
	 * @return 特殊コマンド名
	 */
	public abstract String getCommandName();

	/**
	 * @return 特殊コマンドの説明
	 */
	public abstract String getExplain();

	/**
	 * 
	 * @return クラス付与時のコスト
	 */
	public abstract int getCost();

	/**
	 * 
	 * @return 特殊コマンド使用時のコスト
	 */
	public abstract int getCommandCost();
	
	/**
	 * コマンドを実行
	 *
	 */
	public void execute() {
		// TODO 自動生成されたメソッド・スタブ
		switch(mode){
		case 0:
			getAster().getField().swap(target1,target2);
			break;
		case 1:
			executeSpecialCommand();
			break;
		}
		//行動可能回数を減らす
		decActionCount();
		//ターゲット初期化
		target1 = null;
		target2 = null;
	}
	/**
	 * 特殊コマンドを実行
	 *
	 */
	public abstract void executeSpecialCommand();
	/**
	 * 行動可能回数、フラグ初期化
	 */
	public void init(){
		//行動回数リセット
		actionCount = getActionNum();
		//フラグ消去
		isProtected = false;
	}
	public abstract int getActionNum();
	/**
	 * 行動可能回数増
	 */
	public void incActionCount(){
		actionCount++;
	}
	/**
	 * 行動可能回数減
	 */
	public void decActionCount(){
		actionCount--;
	}
	public void setActionCount(int i){
		actionCount = i;
	}
	public int getActionCount(){
		return actionCount;
	}
	
	/**
	 * 行動可能回数
	 */
	private int actionCount;

	public void setProtectedFlag(boolean b){
		isProtected = b;
	}
	public boolean getProtectedFlag(){
		return isProtected;
	}
	/**
	 * 対象不可フラグ
	 */
	private boolean isProtected;
	
	/**
	 * 現在の選択範囲を返す (スワップ用)
	 * 
	 * @return 現在の選択範囲
	 */
	protected int[][] swapGetRange(int[][] defaultRange,Point target1){
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		//1個目の対象選択
		if(target1 == null){
			for(int i = 0;i < defaultRange.length;i++){
				for(int j = 0;j < defaultRange[0].length;j++){
					//上下左右に隣接レンジが無い孤立したレンジを除外
					if(defaultRange[i+1][j]+defaultRange[i-1][j]+defaultRange[i][j+1]+defaultRange[i][j-1] != 0){
						range[i][j] = defaultRange[i][j];
					}
				}
			}
		//2個目の対象選択
		}else{
			//target1の座標をレンジ内に修正したもの
			Point pt = new Point();
			pt.x = target1.x - (getPlayer().game.getField().asterToPoint(getAster()).x - range[0].length/2);
			pt.y = target1.y - (getPlayer().game.getField().asterToPoint(getAster()).y - range.length/2);

			for(int i = 0;i < range.length;i++){
				for(int j = 0;j < range.length;j++){
					//1個目の対象の上下左右のマスで、元のレンジに含まれている場所のみ1
					if(i == pt.y-1 && j == pt.x && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y+1 && j == pt.x && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y && j == pt.x+1 && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}
					if(i == pt.y && j == pt.x-1 && defaultRange[j][i] == 1){
						range[j][i] = 1;
						continue;
					}else{
						//1個目の対象の上下左右以外移動不可
						range[j][i] = -1;
					}
				}
			}
			//1個目の対象の位置を移動可・選択不可
			range[pt.y][pt.x] = 0;
		}
		return range;
	}
	protected void swapMoveAstern(Point target1,Point target2){
		//2個目の対象選択中に呼ばれた場合
		if(target2 == null){
			target1 = null;
		}
		//2個目の対象選択後に呼ばれた場合
		else{
			target2 = null;
		}
	}
	protected boolean swapSetPointAndNext(Point pt,Point target1,Point target2){
		if(target1 == null){
			target1 = pt;
		}else{
			target2 = pt;
		}
		return true;
	}
	protected boolean swapHasNext(Point target1,Point target2){
		if(target1 != null && target2 != null) return false;
		return true;
	}
	
	public abstract Image getImage();

	static Image loadImage(int n){
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///aster_" + n +".gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}
	
}
