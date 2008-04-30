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
		//InitializeAppli init = new InitializeAppli();
		//init.start();
		title = new Title();
		final Game game = new Game();
		while (true) {
			Option op = title.start();
			game.start(op);
			System.out.println("Game over");
		}
	}

	public void resume() {
		title.resume();
	}

	static Title title;

	static Game game;
}
