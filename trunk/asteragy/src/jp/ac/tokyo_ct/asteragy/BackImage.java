package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class BackImage implements PaintItem {

	private Image backimage;

	private final CanvasControl canvas;

	public BackImage(CanvasControl canvas) {
		this.canvas = canvas;
		createBackGround();
	}

	/**
	 * 固定背景作成
	 * 
	 */
	private void createBackGround() {
		// 背景画像作成
		backimage = Image.createImage(canvas.getWidth(), canvas.getHeight());
		// グラフィクス作成
		Graphics g = backimage.getGraphics();
		// 背景描画
		paintBackGround(g);
		// paintPlayerBack(g);
		// グラフィクス廃棄
		g.dispose();
	}

	/**
	 * 背景描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintBackGround(Graphics g) {
		// 読込先イメージ
		Image back = null;
		try {
			// 背景画像リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///back.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			back = m.getImage();
		} catch (Exception e) {
		}
		// 描画
		if (back != null)
			g.drawImage(back, 0, 0);
	}

	/*
	 * private void paintPlayerBack(Graphics g) { Image back = null; try { //
	 * 背景画像リソースから読み込み MediaImage m =
	 * MediaManager.getImage("resource:///player_back.gif"); // メディアの使用開始
	 * m.use(); // 読み込み back = m.getImage(); } catch (Exception e) { } if (back !=
	 * null) g.drawImage(back, 0, 0); }
	 */

	/**
	 * 描画
	 * 
	 * @param g
	 *            描画先グラフィクス
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
