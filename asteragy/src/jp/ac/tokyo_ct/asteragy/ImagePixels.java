package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public class ImagePixels implements Enumeration {

	private final int width;

	private final int[] pixels;

	private int position;

	public ImagePixels(Image winner) {
		// TODO 自動生成されたコンストラクター・スタブ
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
		// TODO 自動生成されたメソッド・スタブ
		return position < pixels.length;
	}

	public Object nextElement() {
		// TODO 自動生成されたメソッド・スタブ
		Integer i = new Integer(pixels[position++]);
		return i;
	}
}
