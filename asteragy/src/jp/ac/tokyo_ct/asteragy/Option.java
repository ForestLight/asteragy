package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.io.InputStream;

//import java.io.OutputStream;

final class Option {
	int fieldXSize;

	int fieldYSize;

	int numOfColors;

	int gameType;

	int AP_Pointer; // initialAPへのインデックス

	int connection;

	int asterPower;

	static final int[] initialAP = { 0, 10, 20, 30, 50, 100, 999 };

	Option(int XSize, int YSize, int color, int ap, int cn) {
		fieldXSize = XSize;
		fieldYSize = YSize;
		numOfColors = color;
		gameType = -1;
		AP_Pointer = ap;
		connection = cn;
		asterPower = initialAP[AP_Pointer];
	}

	Option() {
		fieldXSize = 9;
		fieldYSize = 9;
		numOfColors = 5;
		gameType = -1;
		AP_Pointer = 2;
		connection = 4;
		asterPower = initialAP[AP_Pointer];
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(6);
		buf.append(fieldXSize);
		buf.append(fieldYSize);
		buf.append(numOfColors);
		buf.append(gameType);
		buf.append(AP_Pointer);
		buf.append(connection);
		return buf.toString();
	}

	void inputFromStream(InputStream is) throws IOException {
		fieldXSize = HTTPPlayer.readIntChar(is);
		fieldYSize = HTTPPlayer.readIntChar(is);
		numOfColors = HTTPPlayer.readIntChar(is);
		gameType = HTTPPlayer.readIntChar(is);
		AP_Pointer = HTTPPlayer.readIntChar(is);
		connection = HTTPPlayer.readIntChar(is);
		asterPower = initialAP[AP_Pointer];
	}
}
