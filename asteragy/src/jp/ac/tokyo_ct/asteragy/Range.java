package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Range {

	private static Point aster;

	private static int[][] range;

	private static boolean visible;

	public static void setVisible(boolean v) {
		visible = v;
	}

	public static void setRange(Point point, int[][] r) {
		aster = point;
		range = r;
	}

	public static void paint(Graphics g, int x, int y) {
		if (visible = false || aster == null)
			return;
		int rx = x - aster.x + (range[0].length / 2);
		int ry = y - aster.y + (range.length / 2);
		if (rx < 0 || rx >= range[0].length || ry < 0 || ry >= range.length) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 100));
			g.fillRect(x * GameCanvas.measure, y * GameCanvas.measure,
					GameCanvas.measure, GameCanvas.measure);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		} else if (range[rx][ry] == 1) {
			g.setColor(Graphics.getColorOfName(Graphics.AQUA));
			g.drawRect(x * GameCanvas.measure, y * GameCanvas.measure,
					GameCanvas.measure, GameCanvas.measure);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		}
	}

}
