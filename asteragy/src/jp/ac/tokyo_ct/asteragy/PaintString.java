package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class PaintString implements PaintItem {

	final private String string;

	final private CanvasControl canvas;

	public PaintString(CanvasControl canvas, String string) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.canvas = canvas;
		this.string = string;
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.fillRect(10, canvas.getHeight() / 3, canvas.getWidth() - 20, canvas
				.getHeight() / 3);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		g.drawString(string, (canvas.getWidth() - Font.getDefaultFont()
				.stringWidth(string)) / 2, (canvas.getHeight() + Font
				.getDefaultFont().getHeight()) / 2);
	}

}
