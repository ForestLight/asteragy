package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * プログラムを開始するクラス
 * 
 * @author Ichinohe
 * 
 */
public final class Main extends IApplication {

	/**
	 * プログラムを開始するエントリポイント
	 */
	public void start() {
		// GameCanvas c = new GameCanvas();
		// Display.setCurrent(c);

		Title t = new Title();
		while (true) {
			t.start();
			game.start();
			System.out.println("Game over");
		}
	}
	
	public static final Game game = new Game();
}
