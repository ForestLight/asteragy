package jp.ac.tokyo_ct.asteragy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Option {
	public int fieldXSize;
	public int fieldYSize;
	public int numOfColors;
	public int gameType;
	public int AP_Pointer; //initialAPへのインデックス
	public int connection;
	public static final int[] initialAP = {0, 10, 20, 30, 50, 100, 999};

	public Option(int XSize, int YSize, int color, int ap, int cn) {
		fieldXSize = XSize;
		fieldYSize = YSize;
		numOfColors = color;
		gameType = -1;
		AP_Pointer = ap;
		connection = cn;
	}

	public Option() {
		fieldXSize = 9;
		fieldYSize = 9;
		numOfColors = 5;
		gameType = -1;
		AP_Pointer = 2;
		connection = 4;		
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
/*
	void outputToStream(OutputStream os) throws IOException {
		HTTPPlayer.writeIntChar(os, fieldXSize);
		HTTPPlayer.writeIntChar(os, fieldYSize);
		HTTPPlayer.writeIntChar(os, numOfColors);
		HTTPPlayer.writeIntChar(os, gameType);
		HTTPPlayer.writeIntChar(os, AP_Pointer);
		HTTPPlayer.writeIntChar(os, connection);
	}
*/
	void inputFromStream(InputStream is) throws IOException {
		fieldXSize = HTTPPlayer.readIntChar(is);
		fieldYSize = HTTPPlayer.readIntChar(is);
		numOfColors = HTTPPlayer.readIntChar(is);
		gameType = HTTPPlayer.readIntChar(is);
		AP_Pointer = HTTPPlayer.readIntChar(is);
		connection = HTTPPlayer.readIntChar(is);
	}
}
