package jp.ac.tokyo_ct.asteragy;

/**
 * @author Okubo
 */
class Field {

	private Aster[][] field;

	private int X, Y;

	private int countAster;

	public Field() {
		super();
	}

	/**
	 * 指示された行動を行う
	 * 
	 * @param a
	 * @return
	 */
	public boolean act(Action a) {
		// 行動を起こす
		// ...

		GameCanvas c = Game.getInstance().getCanvas();
		// cへフィールドの描画を依頼
		return false;
	}

	// フィールドのサイズ
	public void setFieldSize(int x, int y) {
		X = x;
		Y = y;
	}

	// フィールドの初期化
	public void setAster() {
		field = new Aster[Y][X];
		for (int i = 0; i < Y; i++) {
			for (int j = 0; j < X; j++) {
				field[i][j] = new Aster();
				// 消滅判定
				while (judge(j, i) == true) {
					field[i][j].setDeleteFlag(true);
					field[i][j].delete();
				}
			}
		}
	}

	// アステルが3個つながっているかの判定をするだけ
	public boolean judge(int x, int y) {
		int AsterColor = field[y][x].getColor();

		countAster = 0;

		judgeMain(x, y - 1, 1, AsterColor);
		judgeMain(x - 1, y, 2, AsterColor);
		judgeMain(x, y + 1, 3, AsterColor);
		judgeMain(x + 1, y, 4, AsterColor);

		if (countAster < 3) {
			countAster = 0;
			return false; // つながったアステルが3個未満ならfalseを返す
		} else {
			countAster = 0;
			return true; // 3個以上ならtrue
		}
	}

	// つながっている同色アステルをカウント
	private void judgeMain(int x,int y,int back,int AsterColor){
		
		if(x<0 || y<0 || x>X || y>Y || countAster==3){
			return;	
		}
		System.out.println(""+x+","+y);
		if(field[y][x].getColor() == AsterColor){
			System.out.println(""+countAster);
			countAster++;
			/*
			 * 現在注目している座標を後戻りさせないように再帰 back 1 下 （以前に注目していた座標のある方向） 2 右 3 上 4 左
			 * 
			 * つながった同色アステルを3個見つけるだけなら判定する位置は被らないよね…?
			 */
			if(back!=1) judgeMain(x,y+1,3,AsterColor);
			if(back!=2) judgeMain(x+1,y,4,AsterColor);
			if(back!=3) judgeMain(x,y-1,1,AsterColor);
			if(back!=4) judgeMain(x-1,y,2,AsterColor);
			
			// かなりめんどくさいことしてる気がする 動くのかも心配 修正希望
		}
	}

	// つながった同色アステル全てにdeleteFlag立てる 再帰使い魔くり…
	public void setDeleteFlag(int x, int y, int AsterColor) {
		if (field[y][x].getColor() == AsterColor
				&& field[y][x].getDeleteFlag() == false) {
			field[y][x].setDeleteFlag(true);
			setDeleteFlag(x, y - 1, AsterColor);
			setDeleteFlag(x - 1, y, AsterColor);
			setDeleteFlag(x, y + 1, AsterColor);
			setDeleteFlag(x + 1, y, AsterColor);
		}
	}

	// フラグが立ってるアステルをdeleteして、再び削除判定 これも再帰
	public void delete(int x, int y) {
		if (field[y][x].getDeleteFlag() == true) {
			field[y][x].delete();
			delete(x, y - 1);
			delete(x - 1, y);
			delete(x, y + 1);
			delete(x + 1, y);

			while (judge(x, y) == true) {
				field[y][x].setDeleteFlag(true);
				field[y][x].delete();
			}
		}
	}

	// swap
	public void swap(int x1, int y1, int x2, int y2) {
		Aster tmp = new Aster();

		tmp = field[y1][x1];
		field[y1][x1] = field[y2][x2];
		field[y2][x2] = tmp;
	}

	public Aster[][] getField() {
		return field;
	}

}
