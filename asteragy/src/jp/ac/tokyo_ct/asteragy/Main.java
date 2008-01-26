package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * プログラムを開始するクラス
 * 
 * @author Ichinohe
 * 
 */
public class Main extends IApplication {

	/**
	 * プログラムを開始するエントリポイント
	 */
	public void start() {
		// GameCanvas c = new GameCanvas();
		// Display.setCurrent(c);

		// 将来的には、ここでタイトル画面を出すようにしたい。
		Title t = new Title();
		Game g;
		while(true){
			t.start();
	
			g = new Game();
			g.start();
			System.out.println("Game over");
		}
	}
}
