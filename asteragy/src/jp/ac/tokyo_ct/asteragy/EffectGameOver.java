package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectGameOver extends Effect {

	private static final int frame = 15;

	private final CanvasControl canvas;

	private final Player winner;

	public EffectGameOver(CanvasControl canvas, Player winner) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.canvas = canvas;
		this.winner = winner;
	}

	public void start(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if (!isEffect)
			return;
		Image back = canvas.getScreen(new Point(0, 0), new Point(canvas
				.getWidth(), canvas.getHeight()));
		Graphics b = back.getGraphics();
		b.setColor(Graphics.getColorOfRGB(0, 0, 0));
		b.fillRect(0, canvas.getHeight() / 4, canvas.getWidth(), canvas
				.getHeight() / 2);
		b.setColor(Graphics.getColorOfRGB(255, 255, 255));
		b.drawRect(-1, canvas.getHeight() / 4 - 1, canvas.getWidth() + 1,
				canvas.getHeight() / 2);
		b.dispose();

		String win = winner.toString() + "�̏���!!";
		final int height = Font.getDefaultFont().getHeight();
		Image winner = Image.createImage(Font.getDefaultFont()
				.getBBoxWidth(win), height + 1);
		Graphics w = winner.getGraphics();
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		w.drawString(win, 0, height);
		w.dispose();
		ImagePixels pixels = new ImagePixels(winner);

		Point l = new Point((canvas.getWidth() - winner.getWidth() * 3) / 2,
				(canvas.getHeight() - height * 3) / 2);

		Point start = new Point(0, 0);
		StarWord paint = new StarWord(pixels, 3, start);
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
