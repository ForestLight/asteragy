package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Cursor {
	
	public static final int CURSOR_CLEAR = 0; // カーソルの消去

	public static final int CURSOR_1 = 1;

	
	//メモリ節約のため色々staticにしてみた。

	// カーソル情報

	private static int nx;

	private static int ny;

	private static int type;

	// 描画

	public static void setCursor(int x, int y, int cursorType) {
		nx = x;
		ny = y;
		type = cursorType;
	}

	public static void paintCursor(Graphics g) {
		switch (type) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(nx * GameCanvas.measure, ny * GameCanvas.measure,
					GameCanvas.measure, GameCanvas.measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

}
