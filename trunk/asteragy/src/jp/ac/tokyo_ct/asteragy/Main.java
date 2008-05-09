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
		System.out.println(IApplication.getCurrentApp().getSourceURL());
		InitializeAppli init = new InitializeAppli();
		init.start();
		final Game game = new Game();
		title = new Title();
		while (true) {
			final Option op = title.start();
			game.start(op);
			System.out.println("Game over");
		}
	}

	public void resume() {
		title.resume();
	}

	static Title title;
}
