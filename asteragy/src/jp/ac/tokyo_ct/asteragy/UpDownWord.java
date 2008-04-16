package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public class UpDownWord {

	private static final int frame = 15;

	private static final int late = 3;

	private static final int theta = SimpleMath.cycle / (2 * frame);

	private final UpDownWord next;

	private final int width;

	private final int height;

	private final int color;

	private final char word;

	private int time;

	public UpDownWord(String string, int height, int width, int color) {
		this.height = height;
		word = string.charAt(0);
		this.width = width / string.length();
		this.color = color;
		time = 0;
		if (string.length() > 1)
			next = new UpDownWord(string.substring(1), height, width
					- this.width, color);
		else
			next = null;
	}

	public boolean paint(Graphics g, int x) {
		// TODO 自動生成されたメソッド・スタブ
		// g.setColor(Graphics.getColorOfRGB(100, 100, 100));
		// g.drawChars(new char[] { word }, x - 1, -1 - height
		// * SimpleMath.sin(time * theta) / SimpleMath.divide, 0, 1);
		g.setColor(color);
		g.drawChars(new char[] { word }, x, -height
				* SimpleMath.sin(time * theta) / SimpleMath.divide, 0, 1);
		if (time > late) {
			if (next == null) {
				if (time >= frame)
					return false;
			} else {
				if (time < frame)
					next.paint(g, width + x);
				else
					return next.paint(g, width + x);
			}
		}
		time++;
		return true;
	}
}
