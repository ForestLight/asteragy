package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandVenus implements Effect {

	private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandVenus(Field field, Point point) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.field = field;
		this.point = point;
		loadImage();
	}

	private void loadImage() {
		if (effect != null)
			return;

		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager
					.getImage("resource:///venus_effect.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Graphics g = field.getGame().getCanvas().getGraphics();

		final BackImage back = field.getGame().getCanvas().getBackImage();

		final int w = GameCanvas.measure / 2;
		final int h = 8;

		final double theta = Math.PI * 2 / 12;

		for (int i = 0; i < 100; i++) {

			g.lock();

			back.paintAsterBack(g, point);

			if (i % 12 <= 6)
				field.repaintAster(g, point);

			field.setOrignAster(g, point, w, w);

			g.drawImage(effect, (int) (w * Math.cos(theta * (i + 1))),
					(int) (h * Math.sin(theta * (i + 1))));

			if (i % 12 >= 7)
				field.repaintAster(g, point);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}

		}

	}

}
