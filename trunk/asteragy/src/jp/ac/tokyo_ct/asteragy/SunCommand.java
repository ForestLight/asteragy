package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class SunCommand extends Command {

	public SunCommand(CanvasControl canvas) {
		super(canvas);
		loadImage();
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public boolean visible() {
		return command >= 0;
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 2, height
				* (AsterClassData.className.length - 1) + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(Graphics.getColorOfRGB(196, 196, 255));
		g.fillRect(0, 0, height * 4 + 2, height
				* (AsterClassData.className.length - 1) + 2);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 1; i < AsterClassData.className.length; i++) {
			g.drawString(AsterClassData.className[i], 1, height * i - 1);
		}
		setImage(commandImage);
	}

}
