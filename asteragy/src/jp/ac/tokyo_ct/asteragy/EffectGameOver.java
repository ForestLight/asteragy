package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

class EffectGameOver extends Effect {

	private static final int frame = 15;

	private final CanvasControl canvas;

	private final Player winner;

	EffectGameOver(CanvasControl canvas, Player winner) {
		this.canvas = canvas;
		this.winner = winner;
	}

	void start(Graphics g) {
		Image back = canvas.getScreen();
		Graphics b = back.getGraphics();
		b.setColor(Graphics.getColorOfName(Graphics.BLACK));
		b.fillRect(0, canvas.getHeight() / 4, canvas.getWidth(), canvas
				.getHeight() / 2);
		b.setColor(Graphics.getColorOfName(Graphics.WHITE));
		b.drawRect(-1, canvas.getHeight() / 4 - 1, canvas.getWidth() + 1,
				canvas.getHeight() / 2);
		b.dispose();

		String win = winner.toString() + "ÇÃèüóò!!";
		final int height = Font.getDefaultFont().getHeight();
		Image winner = Image.createImage(Font.getDefaultFont()
				.getBBoxWidth(win), height + 1);
		Graphics w = winner.getGraphics();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		w.drawString(win, 0, height);
		w.dispose();

		Point l = new Point((canvas.getWidth() - winner.getWidth() * 3) / 2,
				(canvas.getHeight() - height * 3) / 2);

		Point start = new Point(0, 0);
		StarWord paint = new StarWord(new ImagePixels(winner), 3, start);
		for (int i = 0; i < frame; i++) {
			g.lock();
			g.setOrigin(0, 0);
			g.drawImage(back, 0, 0);

			g.setOrigin(l.x, l.y);
			paint.paint(g);

			g.unlock(true);
			Game.sleep(300 / CanvasControl.f);
		}

		Game.sleep(500);
	}

}
