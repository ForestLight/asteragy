package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class Command implements PaintItem {

	protected int command;

	protected Point point;

	protected Image commandImage;

	protected static final int height = Font.getDefaultFont().getHeight();

	protected static final int back = Graphics.getColorOfRGB(0xd5, 0xe6, 0xd5);

	protected static final int line = Graphics.getColorOfRGB(0x55, 0x1a, 0x8b);

	protected static final int word = Graphics.getColorOfRGB(0x5e, 0x40, 0x2c);

	protected final CanvasControl canvas;

	protected int top;

	public Command(CanvasControl canvas) {
		this.canvas = canvas;
	}

	public final void setCommand(int cmd, Point pt) {
		command = cmd;
		point = pt;
		canvas.repaint();
	}

	public void paint(Graphics g) {
		if (commandImage == null)
			return;
		if (point == null)
			return;
		System.out.println("paintCommand : " + command);
		g.setOrigin(0, 0);
		// g.setClip(0, Player.playerheight, canvas.getWidth(),
		// canvas.getHeight()
		// - Player.playerheight * 2);
		setPosition(g);
		g.drawImage(commandImage, 0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 128, 196, 100));
		System.out.println("command = " + command);
		g.fillRect(1, command * height, height * 4 + 2, height);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		g.clearClip();
	}

	private final void setPosition(Graphics g) {
		final int m = GameCanvas.measure;
		final int topMargin = canvas.getTopMargin();
		final int leftMargin = canvas.getLeftMargin();
		final int imageHeight = commandImage.getHeight();
		final int imageWidth = commandImage.getWidth();
		top = topMargin + m * (point.y + 1);
		int left = leftMargin + m * (point.x + 1);
		if (top >= canvas.getHeight() - imageHeight - Player.playerheight)
			top -= imageHeight;
		if (left >= canvas.getWidth() - imageWidth)
			left -= imageWidth + m;
		g.setOrigin(left, top);
		System.out.println("top:" + top + " left:" + left + "y:" + point.y);
	}

	protected final void setImage(Image image) {
		commandImage = image;
	}

}
