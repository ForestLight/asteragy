package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * �v���O�������J�n����N���X
 * 
 * @author Ichinohe
 * 
 */
public final class Main extends IApplication {

	/**
	 * �v���O�������J�n����G���g���|�C���g
	 */
	public void start() {
		// GameCanvas c = new GameCanvas();
		// Display.setCurrent(c);
//		final Title t = new Title();
		while (true) {
			Option op = title.start();
			game.start(op);
			System.out.println("Game over");
		}
	}

	public void resume() {
		title.resume();
	}

	private static final Title title = new Title();

	static final Game game = new Game();
}
