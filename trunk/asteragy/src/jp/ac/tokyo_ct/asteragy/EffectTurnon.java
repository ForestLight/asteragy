package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Font;
import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public class EffectTurnon extends Effect {

	final private CanvasControl canvas;

	final private Player player;

	public EffectTurnon(CanvasControl canvas, Player player) {
		this.canvas = canvas;
		this.player = player;
	}

	public void start(Graphics g) {
		if (!isEffect)
			return;
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		Image back = canvas.getScreen().getScreen(0, 0, canvas.getWidth(),
				canvas.getHeight());

		// �w�iIn
		int x = canvas.getWidth();
		int y = canvas.getHeight() / 4;
		while (x > 0) {
			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(true);

			x -= 24;

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		// ����In
		String string = player.getName() + "�̃^�[��";
		int stringwidth = Font.getDefaultFont().getBBoxWidth(string);
		int wx = canvas.getWidth();
		int wy = canvas.getHeight() / 2 + Font.getDefaultFont().getHeight() / 2;
		while (wx > canvas.getWidth() / 2 - stringwidth / 2) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(true);

			wx -= 24;

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		// ����Out
		while (-wx < stringwidth) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(true);

			wx -= 24;

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		// �w�iOut
		while (-x < canvas.getWidth()) {
			g.lock();
			g.drawImage(back, 0, 0);
			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(true);

			x -= 24;

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}
	}

}
