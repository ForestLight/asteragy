package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public final class EffectAsterDisappearing extends Effect implements PaintAsterItem {

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

	synchronized public void endEffect(Graphics g) {
		parent.setPaint(paint);
		repaint(g);
	}

	synchronized public void repaint(Graphics g) {
		parent.getField().repaintAster(g, location);
	}

	/*
	 * public void run() { while (time < frame) { try { Thread.sleep(1000 /
	 * CanvasControl.f); } catch (InterruptedException e) { // TODO Ž©“®¶¬‚³‚ê‚½
	 * catch ƒuƒƒbƒN // e.printStackTrace(); } finally { time++; synchronized
	 * (parent) { parent.getField().repaintAster(location); } } }
	 * 
	 * parent.setPaint(paint);
	 * 
	 * parent.getField().repaintAster(location); }
	 */

	private static void loadImage() {
		image = Game.loadImage("disappear.gif");
	}

	public int getHeight() {
		return 0;
	}

	public int getWidth() {
		return 0;
	}

	public void resetSize() {

	}

	public void setSize(int width, int height) {

	}

	public void start(Graphics g) {
		paint(g);
	}

}
