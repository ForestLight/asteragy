package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Cursor implements PaintItem {

	public static final int CURSOR_CLEAR = 0; // �J�[�\���̏���

	public static final int CURSOR_1 = 1;

	private final CanvasControl canvas;
	
	public Cursor(CanvasControl canvas){
		this.canvas = canvas;
	}

	// �J�[�\�����

	private Point point;

	private int type;

	// �`��

	public void setCursor(Point p, int cursorType) {
		point = p;
		type = cursorType;
		canvas.repaint();
	}

	public void paint(Graphics g) {
		switch (type) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			System.out.println("paintCursor");
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(point.x * GameCanvas.measure, point.y
					* GameCanvas.measure, GameCanvas.measure,
					GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

}
