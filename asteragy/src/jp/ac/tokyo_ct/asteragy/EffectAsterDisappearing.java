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
	 * CanvasControl.f); } catch (InterruptedException e) { // TODO �����������ꂽ
	 * catch �u���b�N // e.printStackTrace(); } finally { time++; synchronized
	 * (parent) { parent.getField().repaintAster(location); } } }
	 * 
	 * parent.setPaint(paint);
	 * 
	 * parent.getField().repaintAster(location); }
	 */

	private static void loadImage() {
		if (image == null) {
			try {
				// ���\�[�X����ǂݍ���
				MediaImage m = MediaManager
						.getImage("resource:///disappear.gif");
				// ���f�B�A�̎g�p�J�n
				m.use();
				// �ǂݍ���
				image = m.getImage();
			} catch (Exception e) {
			}
		}
	}

	public int getHeight() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	public int getWidth() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return 0;
	}

	public void resetSize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void setSize(int width, int height) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	public void start() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
