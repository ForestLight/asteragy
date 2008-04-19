package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class SunCommand extends Command {
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
		g.setOrigin(0, top);
		g.setColor(back);
		g
				.fillRect(6, height * (command + 1), canvas.getWidth() - 12,
						height + 4);
		g.setColor(line);
		g.drawLine(5, height * (command + 1), canvas.getWidth() - 6, height
				* (command + 1));
		g.drawLine(5, height * (command + 1) + 1, canvas.getWidth() - 6, height
				* (command + 1) + 1);
		g.drawLine(5, height * (command + 2) + 4, canvas.getWidth() - 6, height
				* (command + 2) + 4);
		g.drawLine(5, height * (command + 2) + 5, canvas.getWidth() - 6, height
				* (command + 2) + 5);
		g.setColor(word);
		g.drawString(AsterClass.commandExplain[command + 1], 8, height
				* (command + 2) + 2);
		g.setOrigin(0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.BLUE));
		g.drawString("クラス " + AsterClass.classCost[command + 1], 75, 224 + Font
				.getDefaultFont().getAscent());
		g.drawString("コマンド " + AsterClass.commandCost[command + 1], 155,
				224 + Font.getDefaultFont().getAscent());
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 4, height
				* CLASSNUM + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(back);
		g.fillRect(0, 0, height * 4 + 4, height * CLASSNUM + 2);
		g.setColor(line);
		g.drawRect(0, -1, height * 4 + 3, height * CLASSNUM + 4);
		g.drawRect(1, -1, height * 4 + 1, height * CLASSNUM + 4);
		g.setColor(word);
		for (int i = 1; i < CLASSNUM + 1; i++) {
			g.drawString(AsterClass.className[i], 1, height * i - 1);
		}
		setImage(commandImage);
	}
}
