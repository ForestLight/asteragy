/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke
 * 
 */
public abstract class AsterClass {

	public AsterClass(Aster a) {
		aster = a;
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

	private Player player;

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
	 * @return 特殊コマンドの名称
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
	 * コマンド種別の設定
	 * @param cmd
	 */
	public abstract void setCommand(int cmd);

	/**
	 * 行動した回数
	 */
	protected int actionCount;

	/**
	 * 対象不可フラグ
	 */
	protected boolean isProtected;

}
