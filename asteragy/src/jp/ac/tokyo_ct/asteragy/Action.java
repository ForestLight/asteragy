package jp.ac.tokyo_ct.asteragy;

import java.io.*;

/**
 * @author Yusuke
 * 
 */
final class Action {

	Aster aster;

	int commandType;

	int[] args;

	public void outputToStream(OutputStream os) throws IOException {
		try {
			Point pt = aster.getPoint();
			HTTPPlayer.writeIntChar(os, pt.x);
			HTTPPlayer.writeIntChar(os, pt.y);
			HTTPPlayer.writeIntChar(os, commandType);
			for (int i = 0; i < args.length; i++) {
				HTTPPlayer.writeIntChar(os, args[i]);
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IOException(e.getMessage());
		}
	}

	public void run() {
		final AsterClass ac = aster.getAsterClass();
		int len = args.length;
		if (len > 0) {
			if (ac.target1 == null) {
				ac.target1 = new Point();
			}
			if (len > 2) {
				if (ac.target2 == null) {
					ac.target2 = new Point();
				}
			}
		}
		switch (len) { // ‚±‚Ìswitch‚Í‚í‚´‚Æbreak”²‚«‚É‚µ‚Ä‚¢‚é
		case 4:
			ac.target2.y = args[3];
		case 3:
			ac.target2.x = args[2];
		case 2:
			ac.target1.y = args[1];
		case 1:
			ac.target1.x = args[0];
		}
		if (commandType == 1) {
			ac.executeSpecialCommand();
		} else {
			ac.execute();
		}
	}

	static final int MAX_PARAM_SIZE = 4;

	static Action readFromStream(Game game, InputStream is) throws IOException {
		Action a = new Action();
		final Field field = game.getField();
		int x = HTTPPlayer.readIntChar(is);
		if (x < 0 || x >= field.X)
			return null;
		int y = HTTPPlayer.readIntChar(is);
		if (y < 0 || y >= field.Y)
			return null;
		a.aster = game.getField().at(y, x);
		int n = HTTPPlayer.readIntChar(is);
		if (n != 0 && n != 1)
			return null;
		a.commandType = n;

		byte[] b = new byte[MAX_PARAM_SIZE];
		int len = is.read(b);
		if (len < 0)
			len = 0;
		a.args = new int[len];
		for (int i = 0; i < len; i++) {
			a.args[i] = HTTPPlayer.parseIntChar(b[i]);
			if (a.args[i] < 0)
				return null;
		}
		return a;
	}
}