package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class Range {

	private Point aster;

	private int[][] range;

	private boolean visible;

	private CanvasControl canvas;

	public Range(CanvasControl canvas) {
		this.canvas = canvas;
		visible = true;
	}

	public void setVisible(boolean v) {
		visible = v;
	}

	public void setRange(Point point, int[][] r) {
		aster = point;
		range = r;
		canvas.game.getField().repaintField();
	}

	public void paint(Graphics g, Point point) {
		if (!visible || aster == null || range == null)
			return;
		final int rx = point.x - aster.x + (range[0].length / 2);
		final int ry = point.y - aster.y + (range.length / 2);
		final int m = CanvasControl.measure;
		if (rx < 0 || rx >= range[0].length || ry < 0 || ry >= range.length
				|| range[rx][ry] == -1) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 80));
			g.fillRect(0, 0, m, m);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		} else if (range[ry][rx] == 0) {
			g.setColor(Graphics.getColorOfRGB(0, 0, 0, 50));
			g.fillRect(0, 0, m, m);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		} else if (range[ry][rx] == 1) {
			g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
			g.drawRect(0, 0, m, m);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		}
	}
}
