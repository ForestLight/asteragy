package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandMoon extends Effect {

	// private static Image effect;

	private final Field field;

	private final Point aster;

	private final Point point;

	public EffectCommandMoon(Field f, Point me, Point pt) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		field = f;
		aster = me;
		point = pt;
		// loadImage();
	}

	/*
	 * private void loadImage() { if (effect != null) return;
	 * 
	 * try { // ���\�[�X����ǂݍ��� MediaImage m =
	 * MediaManager.getImage("resource:///moon_effect.gif"); // ���f�B�A�̎g�p�J�n
	 * m.use(); // �ǂݍ��� effect = m.getImage(); } catch (Exception e) { } }
	 */

	public void start() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		Graphics g = field.getGame().getCanvas().getGraphics();

		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 17; i >= 0; i--) {
			field.setOrignAster(g, aster);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();
			field.setOrignAster(g, point);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}

		for (int i = 0; i >= -17; i--) {
			field.setOrignAster(g, aster);
			g.setClip(0, 0, GameCanvas.measure, GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(255, 255, 255));
			g.fillArc(0, 0, 17, 17, 0, 360);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			g.fillArc(i, 0, 17, 17, 0, 360);
			g.clearClip();

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}
	}

}
