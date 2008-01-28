package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Cursor implements PaintItem {

	public static final int CURSOR_CLEAR = 0; // �J�[�\���̏���

	public static final int CURSOR_1 = 1;

	private final CanvasControl canvas;

	public Cursor(CanvasControl canvas) {
		this.canvas = canvas;
	}

	// �J�[�\�����

	private Point point;

	private int type;

	// �`��

	public void setCursor(Point p, int cursorType) {
		Point temp = point;
		point = p;
		type = cursorType;
		canvas.getField().repaintAster(temp);
		canvas.getField().repaintAster(p);
	}

	public boolean isCursor(Point p) {
		if (point == null)
			return false;
		return point.x == p.x && point.y == p.y;
	}

	public void paint(Graphics g) {
		switch (type) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			System.out.println("paintCursor " + point.x * GameCanvas.measure
					+ ":" + point.y * GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(point.x * GameCanvas.measure, point.y
					* GameCanvas.measure, GameCanvas.measure,
					GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

}
