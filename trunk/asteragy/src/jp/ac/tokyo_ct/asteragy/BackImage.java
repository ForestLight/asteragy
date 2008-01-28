package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class BackImage implements PaintItem {

	private static Image backimage;

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
			MediaImage m = MediaManager.getImage("resource:///back.jpg");
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

	/**
	 * 描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	public void paint(Graphics g) {
		System.out.println("paintBackImage");
		g.drawImage(backimage, 0, 0);
	}

	public void paintFieldBack(Graphics g) {
		g.drawImage(backimage, canvas.getLeftMargin(), canvas.getTopMargin(),
				canvas.getLeftMargin(), canvas.getTopMargin(), canvas
						.fieldWidth() + 1, canvas.fieldHeight() + 1);
	}

	public void paintAsterBack(Graphics g, Point point) {
		int x = canvas.getLeftMargin() + point.x * GameCanvas.measure;
		int y = canvas.getTopMargin() + point.y * GameCanvas.measure;
		g.drawImage(backimage, x, y, x, y, GameCanvas.measure + 1,
				GameCanvas.measure + 1);
	}

	public void paintPlayerBakc(Graphics g, int player) {
		int y = 0;
		if (player == 1) {
			y = canvas.getHeight() - GameCanvas.playerheight;
		}
		g.drawImage(backimage, 0, y, 0, y, canvas.getWidth(),
				GameCanvas.playerheight);
	}

}
