package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public class CommonCommand extends Command {

	private AsterClass asterClass;

	public CommonCommand(CanvasControl canvas) {
		super(canvas);
		loadImage();
	}

	public void setAsterClass(AsterClass ac) {
		asterClass = ac;
		canvas.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("paintCommonCommand");
		PaintCommandName(g);
	}

	/**
	 * �R�}���h���ƃR�X�g�`��(�e�X�g�p�ɉ�)
	 * 
	 * @param g
	 */
	private void PaintCommandName(Graphics g) {
		if (command < 0 || point == null)
			return;
		g.setColor(Graphics.getColorOfRGB(255, 128, 255));
		if (command == 0) {
			g.drawString("�X���b�v 0", 85, 245);
		} else {
			g.drawString(asterClass.getCommandName() + " "
					+ asterClass.getCommandCost(), 85, 245);
		}
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 2, height * 2 + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(Graphics.getColorOfRGB(196, 196, 255));
		g.fillRect(0, 0, height * 4 + 2, height * 2 + 2);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 0; i < 2; i++) {
			g.drawString(commands[i], 1, height * (i + 1) - 1);
		}
		setImage(commandImage);
	}

	private static final String[] commands = { "�X���b�v", "�R�}���h" };

}
