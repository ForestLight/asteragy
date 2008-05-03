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
/*
	void outputToStream(OutputStream os) throws IOException {
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
*/
	
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
		int len = args.length;
		if (len > 0) {
			ac.target1 = new Point();
			if (len > 2) {
				ac.target2 = new Point();
			}
		}
		switch (len) { // ‚±‚Ìswitch‚Í‚í‚´‚Æbreak”²‚«‚É‚µ‚Ä‚¢‚é
		case 4:
			ac.target2.y = args[3];
		case 3:
			ac.target2.x = args[2];
			if (ac instanceof SunClass) {
				((SunClass)ac).asterClassSelect = args[2];
			}
		case 2:
			ac.target1.y = args[1];
		case 1:
			ac.target1.x = args[0];
		}
		ac.setCommand(commandType);
		if(commandType == 1){
			final Player p = ac.getPlayer();
			if (ac instanceof SunClass) {
				p.addAP(-AsterClass.classCost[args[2] + 1]);
			} else {
				p.addAP(-ac.getCommandCost());
			}
		}
		ac.execute(deleteList);
		
		//if (commandType == 1) {
			//ac.executeSpecialCommand();
		//	ac.execute(deleteList);
		//} else {
		//	ac.execute(deleteList);
		//}
	}
	
	String deleteList; 

	static final int MAX_PARAM_SIZE = 4;
/*
	static Action readFromStream(Game game, InputStream is) throws IOException {
		final Action a = new Action();
		final Field field = game.getField();
		int x = HTTPPlayer.readIntChar(is);
		if (x < 0 || x >= field.X)
			return null;
		int y = HTTPPlayer.readIntChar(is);
		if (y < 0 || y >= field.Y)
			return null;
		a.aster = field.field[y][x];
		int n = HTTPPlayer.readIntChar(is);
		if (n != 0 && n != 1)
			return null;
		a.commandType = n;

		byte[] b = new byte[MAX_PARAM_SIZE];
		int len = is.read(b);
		System.out.println("Action.readFromStream: " + (char)b[0] + (char)b[1] + (char)b[2] + (char)b[3]);
		if (len < 0)
			len = 0;
		a.args = new int[len];
		for (int i = 0; i < len; i++) {
			a.args[i] = HTTPPlayer.parseIntChar(b[i]);
			System.out.println(i + " " + (int)b[i] + " " + a.args[i]);
			if (a.args[i] < 0)
				return null;
		}
		return a;
	}
*/
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