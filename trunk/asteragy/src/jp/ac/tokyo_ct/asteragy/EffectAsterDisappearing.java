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
			//�Ȃ������킩��Ȃ������̒����Ăяo�����B�B
			//�Ƃ肠�����B
			paint = new AsterPaint();
			paint.setClass(null);
		}
	}

	public static void setDisappearingAsterNumber(int num) {
		count = 0;
		number = num;
	}

	public void setClass(AsterClass aster) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.setClass(aster);
	}

	public void setColor(int color) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.setColor(color);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
				// TODO �����������ꂽ catch �u���b�N
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
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///disappear.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			image = m.getImage();
		} catch (Exception e) {
		}
	}

}
