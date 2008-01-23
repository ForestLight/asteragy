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
		super.paint(g);
	}

	public boolean visible() {
		return command > -1;
	}

	private void loadImage() {
		Image commandImage = Image.createImage(height * 4 + 2, height
				* CLASSNUM + 1);
		Graphics g = commandImage.getGraphics();
		g.setColor(Graphics.getColorOfRGB(196, 196, 255));
		g.fillRect(0, 0, height * 4 + 2, height
				* CLASSNUM + 2);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		for (int i = 1; i < CLASSNUM+1; i++) {
			g.drawString(AsterClassData.className[i], 1, height * i - 1);
		}
		setImage(commandImage);
	}
	}
