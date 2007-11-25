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
	
	/**
	 * 現在の選択範囲を返す
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

}
