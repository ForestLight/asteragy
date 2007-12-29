package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Command {

	private static int command;

	private static Point point;
	
	private static AsterClass asterClass;

	private static Image commandImage;

	private static final int height = Font.getDefaultFont().getHeight();

	public static void setCommand(int cmd, Point pt) {
		command = cmd;
		point = pt;
	}
	
	public static void setAsterClass(AsterClass ac){
		asterClass = ac;
	}

	public static void paintCommand(Graphics g) {
		if (command < 0 || point == null)
			return;
		g.drawImage(getCommandImage(), GameCanvas.measure * point.x,
				GameCanvas.measure * point.y);
		g.setColor(Graphics.getColorOfRGB(255, 128, 196, 100));
		System.out.println("command = " + command);
		g.fillRect(GameCanvas.measure * point.x, GameCanvas.measure * point.y
				+ command * height, height * 4 + 2, height);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		
		PaintCommandName(g);
	}
	/**
	 * コマンド名とコスト描画(テスト用に仮)
	 * @param g
	 */
	public static void PaintCommandName(Graphics g){
		if(command < 0 || point == null) return;
		g.setColor(Graphics.getColorOfRGB(255,128,255));
		if(command == 0){
			g.drawString("スワップ 0",55,215);
		}else{
		g.drawString(asterClass.getCommandName() + " " + asterClass.getCommandCost(),55,215);	
		}
	}

	private static Image getCommandImage() {
		if (commandImage == null)
			loadCommandImage();
		return commandImage;
	}

	private static void loadCommandImage() {
		commandImage = Image.createImage(height * 4 + 2, height * 2 +1);
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
