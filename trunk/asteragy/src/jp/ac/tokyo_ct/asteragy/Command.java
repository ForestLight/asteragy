package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

public class Command {

	private static int command;

	private static Point point;

	public static void setCommand(int cmd, Point pt) {
		command = cmd;
		point = pt;
	}

	public static void paintCommand(Graphics g) {
		if (command < 0 || point == null)
			return;
		String[] commands = new String[] { "スワップ", "コマンド" };
		for (int i = 0; i < 2; i++) {
			g
					.setColor(Graphics.getColorOfRGB(i == command ? 255 : 0,
							128, 128));
			g.fillRect(point.x * GameCanvas.measure, point.y
					* GameCanvas.measure + 14 * i, 48, 14);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			g.drawString(commands[i], point.x * GameCanvas.measure, point.y
					* GameCanvas.measure + 14 * i + 12);
		}
	}

}
