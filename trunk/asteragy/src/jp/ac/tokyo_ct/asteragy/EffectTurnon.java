package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Font;
import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public class EffectTurnon extends Effect {

	final private CanvasControl canvas;

	Player player;

	public EffectTurnon(CanvasControl canvas) {
		this.canvas = canvas;
	}

	public void start(Graphics g) {
		final Image back = Player.turnOnBack;
		final int x = (canvas.getWidth() - back.getWidth()) / 2;
		final int y = (canvas.getHeight() - back.getHeight()) / 2;
		final String string = player.toString().concat("ÇÃÉ^Å[Éì");

		final int wx = 30;
		final int late = 3 - string.length() / 10;

		UpDownWord word = new UpDownWord(string, 15, Font.getDefaultFont()
				.stringWidth(string), Graphics.getColorOfName(Graphics.BLACK));
		UpDownWord shadow = new UpDownWord(string, 7, Font.getDefaultFont()
				.stringWidth(string), Graphics.getColorOfRGB(100, 100, 100));

		word.setLate(late);
		shadow.setLate(late);

		final int wy = (canvas.getHeight() + Font.getDefaultFont().getHeight()) / 2;

		// int late = 3;

		boolean end = false;

		do {
			g.unlock(true);
			Game.sleep(late * 100 / CanvasControl.f);

			g.lock();
			g.setOrigin(0, 0);
			g.drawImage(back, x, y);

			g.setOrigin(0, wy);

			end |= !shadow.paint(g, wx);
			end |= !word.paint(g, wx);
		} while (!end);
		g.unlock(true);

		Game.sleep(100 / CanvasControl.f);
	}
}
