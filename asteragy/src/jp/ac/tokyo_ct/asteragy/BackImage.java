package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class BackImage implements PaintItem {

	private Image backimage;

	private final CanvasControl canvas;

	public BackImage(CanvasControl canvas) {
		this.canvas = canvas;
		createBackGround();
	}

	/**
	 * ŒÅ’è”wŒiì¬
	 * 
	 */
	private void createBackGround() {
		// ”wŒi‰æ‘œì¬
		backimage = Image.createImage(canvas.getWidth(), canvas.getHeight());
		// ƒOƒ‰ƒtƒBƒNƒXì¬
		Graphics g = backimage.getGraphics();
		// ”wŒi•`‰æ
		paintBackGround(g);
		// paintPlayerBack(g);
		// ƒOƒ‰ƒtƒBƒNƒX”pŠü
		g.dispose();
	}

	/**
	 * ”wŒi•`‰æ
	 * 
	 * @param g
	 *            •`‰ææƒOƒ‰ƒtƒBƒNƒX
	 */
	private static void paintBackGround(Graphics g) {
		// •`‰æ
		if (backGround != null)
			g.drawImage(backGround, 0, 0);
	}

	static final Image backGround = Game.loadImage("back.gif");

	/*
	 * private void paintPlayerBack(Graphics g) { Image back = null; try { //
	 * ”wŒi‰æ‘œƒŠƒ\[ƒX‚©‚ç“Ç‚İ‚İ MediaImage m =
	 * MediaManager.getImage("resource:///player_back.gif"); // ƒƒfƒBƒA‚Ìg—pŠJn
	 * m.use(); // “Ç‚İ‚İ back = m.getImage(); } catch (Exception e) { } if (back !=
	 * null) g.drawImage(back, 0, 0); }
	 */

	/**
	 * •`‰æ
	 * 
	 * @param g
	 *            •`‰ææƒOƒ‰ƒtƒBƒNƒX
	 */
	public void paint(Graphics g) {
		System.out.println("paintBackImage");
		g.setOrigin(0, 0);
		g.drawImage(backimage, 0, 0);
	}

	public void paintFieldBack(Graphics g) {
		final int leftMargin = canvas.getLeftMargin();
		final int topMargin = canvas.getTopMargin();
		g.setOrigin(0, 0);
		g.drawImage(backimage, leftMargin, topMargin, leftMargin, topMargin,
				canvas.getField().getWidth(), canvas.getField().getHeight());
	}

	public void paintAsterBack(Graphics g, Point point) {
		final int m = GameCanvas.measure;
		g.setOrigin(0, 0);
		int x = canvas.getLeftMargin() + point.x * m;
		int y = canvas.getTopMargin() + point.y * m;
		g.drawImage(backimage, x, y, x, y, m + 1, m + 1);
		if (y < Player.playerheight) {
			paintPlayerBack(g, 2);
			canvas.getPlayers()[1].paint(g);
		}
		if (y > canvas.getHeight() - Player.playerheight) {
			paintPlayerBack(g, 1);
			canvas.getPlayers()[0].paint(g);
		}
	}

	public void paintAsterBackRect(Graphics g, Point lt, Point rb) {
		final int m = GameCanvas.measure;
		g.setOrigin(0, 0);
		int x = canvas.getLeftMargin() + lt.x * m;
		int y = canvas.getTopMargin() + lt.y * m;
		int width = (rb.x - lt.x + 1) * m;
		int height = (rb.y - lt.y + 1) * m;
		g.drawImage(backimage, x, y, x, y, width + 1, height + 1);
		if (y < Player.playerheight) {
			paintPlayerBack(g, 2);
			canvas.getPlayers()[1].paint(g);
		}
		if (y + height > canvas.getHeight() - Player.playerheight) {
			paintPlayerBack(g, 1);
			canvas.getPlayers()[0].paint(g);
		}
	}

	public void paintPlayerBack(Graphics g, int player) {
		int y = 0;
		if (player == 1) {
			y = canvas.getHeight() - Player.playerheight;
		}
		g.drawImage(backimage, 0, y, 0, y, canvas.getWidth(),
				Player.playerheight);
	}

}
