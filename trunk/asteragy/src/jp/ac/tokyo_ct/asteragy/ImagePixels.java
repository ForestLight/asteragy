package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public class ImagePixels implements Enumeration {

	private final int width;

	private final int[] pixels;

	private int position;

	public ImagePixels(Image winner) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.width = winner.getWidth();
		Graphics g = winner.getGraphics();
		pixels = g.getRGBPixels(0, 0, width, winner.getHeight(), null, 0);
		g.dispose();
		position = 0;
	}

	public int getWidth() {
		return width;
	}

	public boolean isLeftPixels() {
		if (position != 0)
			return position % width == 0;
		else
			return false;
	}

	public boolean hasMoreElements() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return position < pixels.length;
	}

	public Object nextElement() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Integer i = new Integer(pixels[position++]);
		return i;
	}
}
