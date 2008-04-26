package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class Cursor /* implements PaintItem */{

	static final int CURSOR_CLEAR = 0; // �J�[�\���̏���

	static final int CURSOR_1 = 1;

	private final CanvasControl canvas;

	Cursor(CanvasControl canvas) {
		this.canvas = canvas;
	}

	// �J�[�\�����

	private Point point = new Point();

	private int type;

	// �`��

	void setCursor(Point p, int cursorType) {
		// Point temp = point;
		point = p;
		type = cursorType;
		Main.game.getCanvas().repaint();
		/*
		 * final Graphics g = canvas.getScreen().getBufferGraphics(); final
		 * Field field = canvas.getField(); field.repaintAster(g, temp);
		 * field.repaintAster(g, p); g.dispose();
		 */
	}

	boolean isCursor(int x, int y) {
		return point.x == x && point.y == y;
	}

	public void paint(Graphics g) {
		switch (type) {
		// case CURSOR_CLEAR:
		// break;
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