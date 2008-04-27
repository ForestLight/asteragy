package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class Cursor /* implements PaintItem */{

	static final int CURSOR_CLEAR = 0; // カーソルの消去

	static final int CURSOR_1 = 1;

	private final CanvasControl canvas;

	Cursor(CanvasControl canvas) {
		this.canvas = canvas;
	}

	// カーソル情報

	private int x = 0;

	private int y = 0;

	private int type = 0;

	// 描画

	void setCursor(Point p, int cursorType) {
		// Point temp = point;
		x = p.x;
		y = p.y;
		type = cursorType;
		Main.game.getCanvas().repaint();
		/*
		 * final Graphics g = canvas.getScreen().getBufferGraphics(); final
		 * Field field = canvas.getField(); field.repaintAster(g, temp);
		 * field.repaintAster(g, p); g.dispose();
		 */
	}

	boolean isCursor(int x2, int y2) {
		return x == x2 && y == y2;
	}

	void paint(Graphics g) {
		switch (type) {
		// case CURSOR_CLEAR:
		// break;
		case CURSOR_1:
			g.setOrigin(canvas.getLeftMargin(), canvas.getTopMargin());
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.drawRect(x * CanvasControl.measure, y * CanvasControl.measure,
					CanvasControl.measure, CanvasControl.measure);
			g.setColor(Graphics.getColorOfName(Graphics.BLACK));
			break;
		}
	}

}
