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
		// TODO 自動生成されたメソッド・スタブ

		// Image back = canvas.getScreen().getScreen(0, 0, canvas.getWidth(),
		// canvas.getHeight());
		/*
		 * // 背景In int x = canvas.getWidth(); int y = canvas.getHeight() / 4;
		 * while (x > 0) { g.lock();
		 * 
		 * g.drawImage(player.getTurnOnBack(), x, y);
		 * 
		 * g.unlock(true);
		 * 
		 * x -= 24;
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } }
		 */

		final Image back = player.getTurnOnBack();
		final int x = (canvas.getWidth() - back.getWidth()) / 2;
		final int y = (canvas.getHeight() - back.getHeight()) / 2;
		final String string = "" + player.getName() + "のターン";

		final int wx = 30;

		UpDownWord word = new UpDownWord(string, 15, Font.getDefaultFont()
				.stringWidth(string), Graphics.getColorOfRGB(0, 0, 0));
		UpDownWord shadow = new UpDownWord(string, 7, Font.getDefaultFont()
				.stringWidth(string), Graphics.getColorOfRGB(100, 100, 100));

		final int wy = (canvas.getHeight() + Font.getDefaultFont().getHeight()) / 2;

		// int late = 3;

		boolean end = false;

		do {
			g.unlock(true);
			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			g.lock();
			g.setOrigin(0, 0);
			g.drawImage(back, x, y);

			g.setOrigin(0, wy);

			end |= !shadow.paint(g, wx);
			end |= !word.paint(g, wx);
		} while (!end);
		g.unlock(true);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		/*
		 * // 文字In String string = player.getName() + "のターン"; int stringwidth =
		 * Font.getDefaultFont().getBBoxWidth(string); int wx =
		 * canvas.getWidth(); int wy = canvas.getHeight() / 2 +
		 * Font.getDefaultFont().getHeight() / 2; while (wx > canvas.getWidth() /
		 * 2 - stringwidth / 2) {
		 * 
		 * g.lock();
		 * 
		 * g.drawImage(player.getTurnOnBack(), x, y); g.drawString(string, wx,
		 * wy);
		 * 
		 * g.unlock(true);
		 * 
		 * wx -= 24;
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } }
		 * 
		 * try { Thread.sleep(100); } catch (InterruptedException e) { // TODO
		 * 自動生成された catch ブロック e.printStackTrace(); } // 文字Out while (-wx <
		 * stringwidth) {
		 * 
		 * g.lock();
		 * 
		 * g.drawImage(player.getTurnOnBack(), x, y); g.drawString(string, wx,
		 * wy);
		 * 
		 * g.unlock(true);
		 * 
		 * wx -= 24;
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } } // 背景Out while (-x < canvas.getWidth()) {
		 * g.lock(); g.drawImage(back, 0, 0);
		 * g.drawImage(player.getTurnOnBack(), x, y);
		 * 
		 * g.unlock(true);
		 * 
		 * x -= 24;
		 * 
		 * try { Thread.sleep(300 / CanvasControl.f); } catch
		 * (InterruptedException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } }
		 */
	}
}
