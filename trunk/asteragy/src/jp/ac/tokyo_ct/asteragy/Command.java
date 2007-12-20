package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Command {

	private static int command;

	private static Point point;

	private static Image commandImage;

	private static final int height = Font.getDefaultFont().getHeight();

	public static void setCommand(int cmd, Point pt) {
		command = cmd;
		point = pt;
	}

	public static void paintCommand(Graphics g) {
		if (command < 0 || point == null)
			return;
		g.drawImage(getCommandImage(), GameCanvas.measure * point.x,
				GameCanvas.measure * point.y);
		g.setColor(Graphics.getColorOfRGB(196, 255, 196, 100));
		g.fillRect(GameCanvas.measure * point.x, GameCanvas.measure * point.y
				+ command * height, height * 4 + 2, height);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
	}

	private static Image getCommandImage() {
		if (commandImage == null)
			loadCommandImage();
		return commandImage;
	}

	private static void loadCommandImage() {
		commandImage = Image.createImage(height * 4 + 2, height * 2 + 2);
		Graphics g = commandImage.getGraphics();
		g.setColor(Graphics.getColorOfRGB(196, 196, 255));
		g.fillRect(0, 0, height * 4 + 2, height * 2 + 2);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 0; i < 2; i++) {
			g.drawString(commands[i], 1, height * (i + 1) - 1);
		}
	}
	
	private static String[] commands = { "スワップ", "コマンド" };

}
