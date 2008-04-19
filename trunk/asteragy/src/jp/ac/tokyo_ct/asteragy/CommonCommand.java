package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public final class CommonCommand extends Command {

	private AsterClass asterClass;

	public CommonCommand(CanvasControl canvas) {
		super(canvas);
		loadImage();
	}

	public void setAsterClass(AsterClass ac) {
		asterClass = ac;
	}

	public void paint(Graphics g) {
		if (command < 0 || point == null)
			return;
		super.paint(g);
		System.out.println("paintCommonCommand");
		PaintCommandName(g);
	}

	/**
	 * コマンド名とコスト描画(テスト用に仮)
	 * 
	 * @param g
	 */
	private void PaintCommandName(Graphics g) {
		if (command < 0 || point == null || asterClass == null)
			return;
		g.setOrigin(0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 128, 255));
		if (command == 0) {
			g.setColor(Graphics.getColorOfName(Graphics.BLUE));
			g.drawString("スワップ 0", 85, 235);
		} else {
			g.setOrigin(0, top);
			g.setColor(back);
			g.fillRect(6, height * 2, canvas.getWidth() - 12, height + 4);
			g.setColor(line);
			g.drawLine(5, height * 2, canvas.getWidth() - 6, height * 2);
			g
					.drawLine(5, height * 2 + 1, canvas.getWidth() - 6,
							height * 2 + 1);
			g
					.drawLine(5, height * 3 + 4, canvas.getWidth() - 6,
							height * 3 + 4);
			g
					.drawLine(5, height * 3 + 5, canvas.getWidth() - 6,
							height * 3 + 5);
			g.setColor(word);
			g.drawString(AsterClass.commandExplain[asterClass.getNumber() - 1],
					10, height * (command + 2) + 2);
			g.setOrigin(0, 0);
			g.setColor(Graphics.getColorOfName(Graphics.BLUE));
			g.drawString(asterClass.getCommandName() + " "
					+ AsterClass.commandCost[asterClass.getNumber() - 1], 85,
					235);
		}
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 4, height * 2 + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(back);
		g.fillRect(0, 0, height * 4 + 4, height * 2 + 2);
		g.setColor(line);
		g.drawRect(0, -1, height * 4 + 3, height * 2 + 4);
		g.drawRect(1, -1, height * 4 + 1, height * 2 + 4);
		g.setColor(word);
		for (int i = 0; i < 2; i++) {
			g.drawString(commands[i], 2, height * (i + 1) - 1);
		}
		setImage(commandImage);
	}

	private static final String[] commands = { "スワップ", "コマンド" };

}
