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

		Title t = new Title();
		while (true) {
			Option op = t.start();
			game.start(op.gameType);
			System.out.println("Game over");
		}
	}
	
	public static final Game game = new Game();
}
