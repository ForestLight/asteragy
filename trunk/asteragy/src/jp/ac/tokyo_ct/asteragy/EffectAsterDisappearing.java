package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectAsterDisappearing extends Thread implements PaintAsterItem {

	private static final int frame = 10;

	private static Image image;

	private static int number;

	private static int count;

	private Aster parent;

	private PaintAsterItem paint;

	private int time;

	public EffectAsterDisappearing(Aster parent) {
		this.parent = parent;
		setPaint();
		time = 0;
		loadImage();
	}
	
	private void setPaint(){
		paint = parent.getPaint();
		if(paint == null){
			//なぜだかわからないがこの中が呼び出される。。
			//とりあえず。
			paint = new AsterPaint();
			paint.setClass(null);
		}
	}

	public static void setDisappearingAsterNumber(int num) {
		count = 0;
		number = num;
	}

	public void setClass(AsterClass aster) {
		// TODO 自動生成されたメソッド・スタブ
		paint.setClass(aster);
	}

	public void setColor(int color) {
		// TODO 自動生成されたメソッド・スタブ
		paint.setColor(color);
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.drawScaledImage(image, 1, 1, GameCanvas.measure - 1,
				GameCanvas.measure - 1, 0, time * (GameCanvas.measure - 1),
				GameCanvas.measure - 1, GameCanvas.measure - 1);
	}

	public void run() {
		while (time < frame) {
			repaint();
			time++;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		parent.setPaint(paint);
		repaint();
	}

	private void repaint() {
		synchronized (this) {
			synchronized (parent) {
				count++;
				if (count % number == 0) {
					parent.getField().getCanvas().repaint();
				}
			}
		}
	}

	private static void loadImage() {
		if (image != null)
			return;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///disappear.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			image = m.getImage();
		} catch (Exception e) {
		}
	}

}
