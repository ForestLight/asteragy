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

	public AsterClass(Aster a, Player p) {
		aster = a;
		player = p;
		actionCount = getActionNum();
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

	/**
	 * 対応するアスターを設定する
	 * 
	 * @param a
	 *            アスター このメソッドは、Aster.setAsterClassから呼ばれるためにある。 これを直接呼び出さないこと。
	 */
	void setAster(Aster a) {
		aster = a;
	}

	private Aster aster;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	private Player player;

	public void setCommand(int cmd) {
		mode = cmd;
	}

	public int getCommand() {
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
	 * 
	 * @return 一つ目の対象選択中に呼ばれた場合true
	 */
	public abstract boolean moveAstern();

	/**
	 * @return クラス名
	 */
	public String getName() {
		return AsterClassData.className[getNumber() - 1];
	}

	/**
	 * @return 特殊コマンド名
	 */
	public String getCommandName() {
		return AsterClassData.commandName[getNumber() - 1];
	}

	/**
	 * @return 特殊コマンドの説明
	 */
	public String getExplain() {
		return AsterClassData.commandExplain[getNumber() - 1];
	}

	/**
	 * 
	 * @return クラス付与時のコスト
	 */
	public int getCost() {
		return AsterClassData.classCost[getNumber() - 1];
	}

	/**
	 * 
	 * @return 特殊コマンド使用時のコスト
	 */
	public int getCommandCost() {
		// switch (mode) {
		// case 0:
		// return 0;
		// case 1:
		// return AsterClassData.commandCost[getNumber()-1];
		// }
		// return 0;
		return AsterClassData.commandCost[getNumber() - 1];
	}
	
	private void logAction(int commandType, int[] args) {
		Action a = new Action(player);
		a.commandType = commandType;
		a.args = args;
		Main.game.logAction(a);
	}

	protected void logAction(int[] args) {
		logAction(1, args);
	}
	/**
	 * コマンドを実行
	 * 
	 */
	public void execute() {
		// TODO 自動生成されたメソッド・スタブ
		final Field field = getAster().getField();
		System.out.println("----AsterClass.execute()");
		switch (mode) {
		case 0:
			/*
			field.backupField();
			field.swap(target1, target2);
			// サン自滅判定（ダイアログは仮なので然るべき演出に置き換えておいてください）
			if (field.judgeSelfDestruction() == true) {
				Dialog d = new Dialog(Dialog.DIALOG_YESNO, "注意");
				d.setText("サンが消えます");
				if(d.show() == Dialog.BUTTON_NO){
					field.restoreField();
					incActionCount();
				}
			}
			*/
			// 競合してたのでとりあえずコメントアウト

			field.swap(target1, target2);
			logAction(0, new int[] {target1.x, target1.y, target2.x, target2.y});
			break;
		case 1:
			executeSpecialCommand();
			break;
		}
		// 行動可能回数を減らす
		decActionCount();
		// ターゲット初期化
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
	public void init() {
		// 行動回数リセット
		actionCount = getActionNum();
		// フラグ消去
		isProtected = false;

		aster.getField().repaintAster(aster.getPoint());
	}

	public int getActionNum() {
		return AsterClassData.actionNum[getNumber() - 1];
	}

	/**
	 * 行動可能回数増
	 */
	public void incActionCount() {
		actionCount++;
	}

	/**
	 * 行動可能回数減
	 */
	public void decActionCount() {
		actionCount--;
	}

	public void setActionCount(int i) {
		actionCount = i;
	}

	public int getActionCount() {
		return actionCount;
	}

	/**
	 * 行動可能回数
	 */
	private int actionCount;

	public void setProtectedFlag(boolean b) {
		isProtected = b;
	}

	public boolean getProtectedFlag() {
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
	protected int[][] swapGetRange(int[][] defaultRange) {
		int[][] range = new int[defaultRange.length][defaultRange[0].length];
		// 1個目の対象選択
		if (target1 == null) {

			for (int i = 0; i < defaultRange.length; i++) {
				for (int j = 0; j < defaultRange[0].length; j++) {
					// 上下左右に隣接レンジが無い孤立したレンジを除外
					// if (defaultRange[i + 1][j] + defaultRange[i - 1][j]
					// + defaultRange[i][j + 1] + defaultRange[i][j - 1] != 0) {
					range[i][j] = defaultRange[i][j];
					// }
				}
			}

		}
		// 2個目の対象選択
		else {
			range = new int[defaultRange.length][defaultRange[0].length];
			// target1の座標をレンジ内に修正したもの
			Point selftPoint = getAster().getPoint();
			Point pt = new Point();
			System.out.println("tx:" + target1.x + "ty:" + target1.y + "spx:"
					+ selftPoint.x + "spy:" + selftPoint.y);
			pt.x = target1.x - (selftPoint.x - range[0].length / 2);
			pt.y = target1.y - (selftPoint.y - range.length / 2);
			System.out.println("px:" + pt.x + "py:" + pt.y);

			for (int i = 0; i < range.length; i++) {
				for (int j = 0; j < range[0].length; j++) {
					// 1個目の対象の上下左右のマスで、元のレンジに含まれている場所のみ1
					if (defaultRange[i][j] == 1) {
						if (i == pt.y - 1 && j == pt.x ||
							i == pt.y + 1 && j == pt.x ||
							i == pt.y && j == pt.x + 1 ||
							i == pt.y && j == pt.x - 1) {
							range[i][j] = 1;
						}
					}
				}
			}
			// 1個目の対象の位置を移動可・選択不可
			range[pt.y][pt.x] = 0;
		}
		return range;
	}

	protected boolean swapMoveAstern() {
		// 1個目の対象選択中に呼ばれた場合
		if (target1 == null) {
			return true;
		}
		// 2個目の対象選択中に呼ばれた場合
		if (target2 == null) {
			target1 = null;
		}
		// 2個目の対象選択後に呼ばれた場合
		else {
			target2 = null;
		}
		return false;
	}

	protected boolean swapSetPointAndNext(Point pt) {
		if (target1 == null) {
			target1 = pt;
		} else {
			target2 = pt;
		}
		return true;
	}

	protected boolean swapHasNext() {
		boolean ret = target1 == null || target2 == null;
		System.out.println("swapHasNext return " + ret);
		return ret;
	}

	public abstract Image getImage();

	static Image loadImage(int n) {
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///aster_" + n
					+ ".gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}

	protected static boolean isYInFieldBound(Field f, int y) {
		return 0 <= y && y < f.getY();
	}

	protected static boolean isXInFieldBound(Field f, int x) {
		return 0 <= x && x < f.getX();
	}
}
