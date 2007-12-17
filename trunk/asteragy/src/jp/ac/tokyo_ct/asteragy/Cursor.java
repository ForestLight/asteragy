package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Cursor {

	public static final int CURSOR_CLEAR = 0; // �J�[�\���̏���

	public static final int CURSOR_1 = 1;

	// �������ߖ�̂��ߐF�Xstatic�ɂ��Ă݂��B

	// �J�[�\�����

	private static Point point;

	private static int type;

	// �`��

	public static void setCursor(Point p, int cursorType) {
		point = p;
		type = cursorType;
	}

	public static void paintCursor(Graphics g) {
		switch (type) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(point.x * GameCanvas.measure, point.y
					* GameCanvas.measure, GameCanvas.measure,
					GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

}
