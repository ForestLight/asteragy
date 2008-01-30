package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class AsterPaint implements PaintAsterItem {

	private int color;

	private AsterClass aster;

	public AsterPaint() {
	}

	public void setClass(AsterClass aster) {
		this.aster = aster;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void paint(Graphics g) {
		final int m = GameCanvas.measure - 1;
		g.drawScaledImage(getImage(), 1, 1, m, m, m * (color - 1), 0, m, m);
		// 行動済みユニットを識別
		if (aster != null && aster.getActionCount() == 0) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 100));
			g.fillRect(1, 1, GameCanvas.measure, GameCanvas.measure);
		}
	}

	private Image getImage() {
		if (asterImage == null) {
			loadImage();
			return asterImage;
		}
		if (aster == null)
			return asterImage;
		return aster.getImage();
	}

	private static void loadImage() {
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///aster_0.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			asterImage = m.getImage();
		} catch (Exception e) {
		}
	}

	private static Image asterImage;

}
