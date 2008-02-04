package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class PlutoClass extends AsterClass {
	private static int[][] defaultRange = {{ 1, 1, 0, 1, 1 },{ 1, 0, 1, 0, 1 }, 
			{ 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 },{ 1, 1, 0, 1, 1 } };

	private static Image asterImage;

	public PlutoClass(Aster a, Player p) {
		super(a, p);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return 11;
	}

	public int[][] getRange() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			return null;
		}
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			return false;
		}
		return false;
	}

	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			return false;
		}
		return false;
	}

	public boolean moveAstern() {
		// TODO 自動生成されたメソッド・スタブ
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return false;
		}
		return false;
	}

	public void executeSpecialCommand() {
		final Field field = getAster().getField();
		Point me = field.asterToPoint(getAster());
		Point pt = new Point();
		for (int i = 0; i < defaultRange.length; i++) {
			for (int j = 0; j < defaultRange[0].length; j++) {
				// レンジ内であり
				if (defaultRange[i][j] == 1) {
					// 自身ではない部分を破壊
					if (i != defaultRange.length / 2
							|| j != defaultRange[0].length / 2) {
						pt.x = me.x - defaultRange.length / 2 + j;
						pt.y = me.y - defaultRange[0].length / 2 + i;

						// フィールドの外にはみ出してたら処理しない
						final Aster[][] f = field.getField();
						if (pt.x < 0 || pt.x >= f[0].length)
							continue;
						if (pt.y < 0 || pt.y >= f.length)
							continue;

						// 破壊対象にdeleteFlag
						field.setDeleteFlag(pt);
					}
				}
			}
		}
		// サン自滅判定（ダイアログは仮なので然るべき演出に置き換えておいてください）
		if (field.judgeSelfDestruction() == true) {
			Dialog d = new Dialog(Dialog.DIALOG_YESNO, "注意");
			d.setText("サンが消えます");
			if(d.show() == Dialog.BUTTON_NO){
				field.removeDeleteFlagAll();
				incActionCount();
				getPlayer().addSP(getCommandCost());
				return;
			}
		}
		else {
			field.deleteAll();
		}
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(11);
		}
		return asterImage;
	}
}
