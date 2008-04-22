package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class Cursor /*implements PaintItem*/ {

	public static final int CURSOR_CLEAR = 0; // カーソルの消去

	public static final int CURSOR_1 = 1;

	private final CanvasControl canvas;

	public Cursor(CanvasControl canvas) {
		this.canvas = canvas;
	}

	// カーソル情報

	private Point point;

	private int type;

	// 描画

	public void setCursor(Point p, int cursorType) {
//		Point temp = point;
		point = p;
		type = cursorType;
		Main.game.getCanvas().repaint();
/*
		final Graphics g = canvas.getScreen().getBufferGraphics();
		final Field field = canvas.getField();
		field.repaintAster(g, temp);
		field.repaintAster(g, p);
		g.dispose();
*/
	}

	public boolean isCursor(Point p) {
		if (point == null)
			return false;
		return p.equals(point);
	}

	public void paint(Graphics g) {
		switch (type) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.drawRect(point.x * CanvasControl.measure, point.y
					* CanvasControl.measure, CanvasControl.measure,
					CanvasControl.measure);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			break;
		}
	}

}
