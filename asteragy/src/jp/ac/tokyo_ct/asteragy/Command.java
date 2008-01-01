package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Command implements PaintItem {

	protected int command;

	protected Point point;

	private static Image commandImage;

	protected static final int height = Font.getDefaultFont().getHeight();

	protected final CanvasControl canvas;

	public Command(CanvasControl canvas) {
		this.canvas = canvas;
	}

	public void setCommand(int cmd, Point pt) {
		command = cmd;
		point = pt;
		canvas.repaint();
	}

	public void paint(Graphics g) {
		if (command < 0 || point == null)
			return;
		System.out.println("paintCommand");
		g.drawImage(commandImage, GameCanvas.measure * point.x,
				GameCanvas.measure * point.y);
		g.setColor(Graphics.getColorOfRGB(255, 128, 196, 100));
		System.out.println("command = " + command);
		g.fillRect(GameCanvas.measure * point.x, GameCanvas.measure * point.y
				+ command * height, height * 4 + 2, height);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
	}

	protected void setImage(Image image) {
		commandImage = image;
	}

}
