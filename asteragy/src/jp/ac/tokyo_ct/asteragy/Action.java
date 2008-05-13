package jp.ac.tokyo_ct.asteragy;

//import java.io.*;

/**
 * @author Yusuke
 * 
 */
final class Action {

	Aster aster;

	int commandType;

	int[] args;

	public String toString() {
		final Point pt = aster.getPoint();
		final StringBuffer buf = new StringBuffer(8);
		buf.append(pt.x);
		buf.append(pt.y);
		buf.append(commandType);
		for (int i = 0; i < args.length; i++) {
			buf.append(args[i]);
		}
		return buf.toString();
	}

	void run() {
		final AsterClass ac = aster.getAsterClass();
		if (commandType == 0) {
			ac.executeSwap(deleteList, args);
		} else {
			final Player p = ac.getPlayer();
			if (ac.getNumber() == 1) {
				p.addAP(-AsterClass.classCost[args[2] + 1]);
			} else {
				p.addAP(-ac.getCommandCost());
			}
			ac.executeSpecialCommand(deleteList, args);
		}
	}

	String deleteList;

	static final int MAX_PARAM_SIZE = 4;

	static Action readFromString(Game game, String s) {
		final Action a = new Action();
		final Field field = game.getField();
		int x = HTTPPlayer.parseIntChar(s.charAt(0));
		if (!field.isXInFieldBound(x))
			return null;
		int y = HTTPPlayer.parseIntChar(s.charAt(1));
		if (!field.isYInFieldBound(y))
			return null;
		a.aster = field.field[y][x];
		int n = HTTPPlayer.parseIntChar(s.charAt(2));
		if (n != 0 && n != 1)
			return null;
		a.commandType = n;

		int len = s.length() - 3;
		if (len < 0)
			len = 0;
		a.args = new int[len];
		for (int i = 0; i < len; i++) {
			a.args[i] = HTTPPlayer.parseIntChar(s.charAt(i + 3));
			if (a.args[i] < 0)
				return null;
		}
		return a;
	}
}