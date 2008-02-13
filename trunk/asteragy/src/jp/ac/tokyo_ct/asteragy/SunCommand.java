package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class SunCommand extends Command {
	final static int CLASSNUM = 10;

	public SunCommand(CanvasControl canvas) {
		super(canvas);
		command = -1;
		loadImage();
	}

	public void paint(Graphics g) {
		if (command < 0 || point == null)
			return;
		super.paint(g);
		paintClassData(g);
	}

	public boolean visible() {
		return command > -1;
	}

	private void paintClassData(Graphics g) {
		int top = GameCanvas.playerheight
				+ (canvas.getHeight() - commandImage.getHeight() - GameCanvas.playerheight * 2)
				* point.y / canvas.getField().getY();
		g.setOrigin(0, top);
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.fillRect(6, height * (command + 1), canvas.getWidth() - 12, 14);
		g.setColor(Graphics.getColorOfRGB(0, 0, 255));
		g.drawString(AsterClass.commandExplain[command], 10, height
				* (command + 2));
		g.setOrigin(0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 128, 255));
		g.drawString("クラス " + AsterClass.classCost[command], 75, 235);
		g.drawString("コマンド " + AsterClass.commandCost[command], 155, 235);
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 2, height
				* CLASSNUM + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(Graphics.getColorOfRGB(196, 196, 255));
		g.fillRect(0, 0, height * 4 + 2, height * CLASSNUM + 2);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 1; i < CLASSNUM + 1; i++) {
			g.drawString(AsterClass.className[i], 1, height * i - 1);
		}
		setImage(commandImage);
	}
}
