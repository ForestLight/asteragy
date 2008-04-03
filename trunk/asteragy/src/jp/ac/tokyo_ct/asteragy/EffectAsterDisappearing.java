package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectAsterDisappearing extends Effect implements PaintAsterItem {

	private static final int frame = 10;

	private static Image image;

	private final Aster parent;

	private PaintAsterItem paint;

	private final Point location;

	private int time;

	public EffectAsterDisappearing(Aster parent) {
		// System.out.println("EffectAsterDisappearing
		// Constract:"+parent.getPaint().toString());
		synchronized (parent) {
			this.paint = parent.getPaint();
			this.parent = parent;
			this.location = parent.getPoint();
		}
		parent.getField().getGame().getCanvas().getDisappearControl().Add(this);
		time = 0;
		loadImage();
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

	synchronized public boolean increaseTime() {
		time++;
		return time >= frame;
	}

	synchronized public void endEffect() {
		parent.setPaint(paint);
		repaint();
	}

	synchronized public void repaint() {
		parent.getField().repaintAster(location);
	}

	/*
	 * public void run() { while (time < frame) { try { Thread.sleep(1000 /
	 * CanvasControl.f); } catch (InterruptedException e) { // TODO 自動生成された
	 * catch ブロック // e.printStackTrace(); } finally { time++; synchronized
	 * (parent) { parent.getField().repaintAster(location); } } }
	 * 
	 * parent.setPaint(paint);
	 * 
	 * parent.getField().repaintAster(location); }
	 */

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

	public int getHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public int getWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public void resetSize() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void setSize(int width, int height) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void start() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
