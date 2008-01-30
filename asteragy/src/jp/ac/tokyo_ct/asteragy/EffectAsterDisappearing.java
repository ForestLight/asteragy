package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectAsterDisappearing extends Thread implements PaintAsterItem {

	private static final int frame = 10;

	private static Image image;

	private final Aster parent;

	private PaintAsterItem paint;

	private final Point location;

	private int time;

	public EffectAsterDisappearing(Aster parent) {
		this.parent = parent;
		this.location = parent.getPoint();
		setPaint();
		time = 0;
		loadImage();
	}

	private void setPaint() {
		paint = parent.getPaint();
	}

	public void setClass(AsterClass aster) {
		paint.setClass(aster);
	}

	public void setColor(int color) {
		paint.setColor(color);
	}

	public void paint(Graphics g) {
		g.drawImage(image, 1, 1, 0, time * (GameCanvas.measure - 1),
				GameCanvas.measure - 1, GameCanvas.measure - 1);
	}

	public void run() {
		while (time < frame) {
			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			time++;

			parent.getField().repaintAster(location);
		}

		parent.setPaint(paint);

		parent.getField().repaintAster(location);
	}

	private static void loadImage() {
		if (image == null) {
			try {
				// リソースから読み込み
				MediaImage m = MediaManager
						.getImage("resource:///disappear.gif");
				// メディアの使用開始
				m.use();
				// 読み込み
				image = m.getImage();
			} catch (Exception e) {
			}
		}
	}

}
