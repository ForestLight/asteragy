package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Font;
import com.nttdocomo.ui.Graphics;

public class EffectTurnon implements Effect {
	
	final private CanvasControl canvas;
	
	final private Player player;
	
	public EffectTurnon(CanvasControl canvas, Player player){
		this.canvas = canvas;
		this.player = player;
	}

	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = canvas.getGraphics();

		// 背景In
		int x = canvas.getWidth();
		int y = canvas.getHeight() / 4;
		while (x > 0) {
			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(false);

			x -= 24;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 文字In
		String string = player.getName() + "のターン";
		int stringwidth = Font.getDefaultFont().getBBoxWidth(string);
		int wx = canvas.getWidth();
		int wy = canvas.getHeight() / 2 + Font.getDefaultFont().getHeight() / 2;
		while (wx > canvas.getWidth() / 2 - stringwidth / 2) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(false);

			wx -= 24;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 文字Out
		while (wx * -1 < stringwidth) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(false);

			wx -= 24;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 背景Out
		while (x * -1 < canvas.getWidth()) {
			g.lock();

			canvas.repaint(x + canvas.getWidth(), y, canvas.getWidth() - x, canvas.getHeight() / 2);
			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(false);

			x -= 24;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
	
	

}
