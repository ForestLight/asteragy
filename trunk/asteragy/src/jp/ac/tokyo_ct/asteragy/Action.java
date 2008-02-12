package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import com.nttdocomo.io.*;

/**
 * @author Yusuke
 * 
 */
final class Action {

	Aster aster;

	int commandType;

	int[] args;

	public void printToStream(OutputStream os) throws IOException {
		try {
			Point pt = aster.getPoint();
			printInt(os, pt.x);
			printInt(os, pt.y);
			printInt(os, commandType);
			for (int i = 0; i < args.length; i++) {
				printInt(os, args[i]);
			}
			os.write('\r');
			os.write('\n');
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
		switch (len) { //このswitchはわざとbreak抜きにしている
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

	/**
	 * 16進法で1文字となる値を出力
	 * 
	 * @param os
	 * @param x
	 * @throws IndexOutOfBoundsException
	 *             xが[0, 15]の範囲にないとき
	 */
	private void printInt(OutputStream os, int x)
			throws IndexOutOfBoundsException, IOException {
		if (x < 0 || x >= 16)
			throw new IndexOutOfBoundsException("Action.printInt");
		else if (x < 10)
			os.write(x + '0');
		else
			os.write(x + 'A');
	}
	
	public static final int MAX_PARAM_SIZE = 4;

	public static Action inputFromStream(Game game, InputStream is) throws IOException {
		Action a = new Action();		
		final Field field = game.getField();
		int x = readInt(is);
		if (x < 0 || x >= field.getX())
			return null;
		int y = readInt(is);
		if (y < 0 || y >= field.getY())
			return null;
		a.aster = game.getField().at(y, x);
		int n = readInt(is);
		if (n != 0 && n != 1)
			return null;
		a.commandType = n;
	
		byte[] b = new byte[MAX_PARAM_SIZE];
		int len = is.read(b);
		if (len < 0)
			len = 0;
		a.args = new int[len];
		for (int i = 0; i < len; i++) {
			a.args[i] = parseInt(b[i]);	
			if (a.args[i] < 0)
				return null;
		}
		return a;
	}
		
	private static int readInt(InputStream is) throws IOException {
		return parseInt(is.read());
	}

	private static int parseInt(int c) {
		if (c < 0)
			return -1;
		else if (c - '0' < 10)
			return c - '0';
		else if (c - 'A' < 6)
			return c - 'A' + 10;
		else
			return -1;
	}}