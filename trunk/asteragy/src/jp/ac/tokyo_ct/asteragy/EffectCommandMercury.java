package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectCommandMercury extends Effect implements PaintAsterItem {

	private static Image effect;

	private final Field field;

	private final Point point;

	private final Aster aster;

	private final PaintAsterItem paint;

	private int time;

	public EffectCommandMercury(Field field, Point point) {
		this.field = field;
		this.point = point;
		aster = field.getAster(point);
		paint = aster.getPaint();
		loadImage();
	}

	private void loadImage() {

		if (effect != null)
			return;
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager
					.getImage("resource:///mercury_effect.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start(Graphics g) {
		if (!isEffect)
			return;
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		aster.setPaint(this);

		for (time = 0; time < 170 - GameCanvas.measure; time += 3) {
			field.repaintAster(g, point);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		aster.setPaint(paint);
		field.repaintAster(g, point);
	}

	public int getHeight() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return paint.getHeight();
	}

	public int getWidth() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return paint.getWidth();
	}

	public void resetSize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.resetSize();
	}

	public void setClass(AsterClass aster) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.setClass(aster);
	}

	public void setColor(int color) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.setColor(color);
	}

	public void setSize(int width, int height) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paint.setSize(width, height);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		System.out.println("Mercury Effect");
		paint.paint(g);
		g.drawImage(effect, 0, 0, 0, time, GameCanvas.measure,
				GameCanvas.measure);
	}

}
