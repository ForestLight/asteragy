package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class AsterPaint implements PaintAsterItem {

	private int color;

	private AsterClass aster;

	private int height = GameCanvas.measure - 1;

	private int width = GameCanvas.measure - 1;

	public AsterPaint() {
	}

	public void setClass(AsterClass aster) {
		this.aster = aster;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void resetSize() {
		setSize(GameCanvas.measure - 1, GameCanvas.measure - 1);
	}

	public void paint(Graphics g) {
		final int m = GameCanvas.measure - 1;

		// �v���C���[2�̃��j�b�g�͔��]
		if (aster != null && aster.getPlayer().isPlayer2()) {
			g.setFlipMode(Graphics.FLIP_VERTICAL);
		} else {
			g.setFlipMode(Graphics.FLIP_NONE);
		}
		
		g.drawScaledImage(getImage(), 1, 1, width, height, m * (color - 1), 0, m, m);
		// �s���ς݃��j�b�g������
		if (aster != null && aster.getActionCount() == 0) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 100));
			g.fillRect(1, 1, GameCanvas.measure, GameCanvas.measure);
		}
	}

	private Image getImage() {
		if (asterImage == null) {
			loadImage();
			return asterImage;
		}
		if (aster == null)
			return asterImage;
		return aster.getImage();
	}

	private static void loadImage() {
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///aster_0.gif");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			asterImage = m.getImage();
		} catch (Exception e) {
		}
	}

	private static Image asterImage;

}
