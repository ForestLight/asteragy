package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandJupiter implements Effect {

	private static Image effect;

	private final Field field;

	private final Point point;

	private Point lefttop;

	private Point rightbottom;

	public EffectCommandJupiter(Field field, Point point) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.field = field;
		this.point = point;
		setLocation();
		loadImage();
	}

	private void setLocation() {
		lefttop = point.clone();
		rightbottom = point.clone();

		lefttop.x -= 1;
		lefttop.y -= 6;
		rightbottom.x += 2;
	}

	private void loadImage() {
		if (effect != null)
			return;

		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager
					.getImage("resource:///jupiter_effect.gif");
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

		final int height = 10;

		for (int i = 0; i < effect.getHeight(); i++) {
			g.lock();

			field.repaintAsterRect(g, lefttop, rightbottom);
			field.setOrignAster(g, point, (GameCanvas.measure - effect
					.getWidth()) / 2, GameCanvas.measure / 2
					- effect.getHeight());

			g.drawImage(effect, 0, -height, 0, 0, effect.getWidth(), i);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		for (int i = 0; i < height; i++) {

			g.lock();

			field.repaintAsterRect(g, lefttop, rightbottom);
			field.setOrignAster(g, point, (GameCanvas.measure - effect
					.getWidth()) / 2, GameCanvas.measure / 2
					- effect.getHeight());

			g.drawImage(effect, 0, i - height);

			g.unlock(true);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		for (int i = 0; i < height; i++) {

			g.lock();

			field.repaintAsterRect(g, lefttop, rightbottom);
			field.setOrignAster(g, point, (GameCanvas.measure - effect
					.getWidth()) / 2, GameCanvas.measure / 2
					- effect.getHeight());

			g.drawImage(effect, 0, i, 0, 0, effect.getWidth(), effect
					.getHeight()
					- i);

			g.unlock(true);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000 / CanvasControl.f * 20);
		} catch (InterruptedException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}
}
