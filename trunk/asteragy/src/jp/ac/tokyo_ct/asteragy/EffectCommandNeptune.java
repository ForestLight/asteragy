package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandNeptune extends Effect {

	private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandNeptune(Field field, Point point) {
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
					.getImage("resource:///neptune_effect.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start(Graphics g) {
		if(!isEffect)
			return;
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		field.setOrignAster(g, point, 0, GameCanvas.measure / 2);

		for (int i = 0; i < 30; i++) {
			g.lock();

			g.drawImage(effect, 0, -1 * effect.getHeight(), 0, 0, effect
					.getWidth(), effect.getHeight() / 10 * i);

			g.unlock(true);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		g.setOrigin(0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.fillRect(0, 0, field.getGame().getCanvas().getWidth(), field
				.getGame().getCanvas().getHeight());

		try {
			Thread.sleep(300 / CanvasControl.f);
		} catch (InterruptedException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		field.getGame().getCanvas().repaint();
	}
}
