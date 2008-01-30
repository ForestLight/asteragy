package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Command implements PaintItem {

	protected int command;

	protected Point point;

	private Image commandImage;

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
		System.out.println("paintCommand ; " + command);
		if (command < 0 || point == null)
			return;
		System.out.println("paintCommand");
		setPosition(g);
		g.drawImage(commandImage, 0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 128, 196, 100));
		System.out.println("command = " + command);
		g.fillRect(0, command * height, height * 4 + 2, height);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
	}

	private void setPosition(Graphics g) {
		final int m = GameCanvas.measure;
		final int topMargin = canvas.getTopMargin();
		final int leftMargin = canvas.getLeftMargin();
		final int imageHeight = commandImage.getHeight();
		final int imageWidth = commandImage.getWidth();
		int top = topMargin + m * (point.y + 1);
		int left = leftMargin + m * (point.x + 1);
		if (top >= canvas.getHeight() - imageHeight - topMargin)
			top -= imageHeight + m;
		if (left >= canvas.getWidth() - imageWidth - leftMargin)
			left -= imageWidth + m;
		g.setOrigin(left, top);
		System.out.println("top:" + top + " left:" + left);
	}

	protected void setImage(Image image) {
		commandImage = image;
	}

}
