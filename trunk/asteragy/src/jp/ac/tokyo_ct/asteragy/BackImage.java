package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class BackImage {

	private static Image backimage;

	/**
	 * 固定背景作成
	 * 
	 */
	private static void createBackGround() {
		// 背景画像作成
		backimage = Image.createImage(240, 240);
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
	private static void paintBackGround(Graphics g) {
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
	 * 背景読み込み
	 * 
	 * @return 背景
	 */
	private static Image backImage() {
		if (backimage == null)
			createBackGround();
		return backimage;
	}

	/**
	 * 描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	public static void paint(Graphics g) {
		g.drawImage(backImage(), 0, 0);
	}

}
