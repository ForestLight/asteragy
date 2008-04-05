package jp.ac.tokyo_ct.asteragy;

public class Option {
	public int fieldXSize;
	public int fieldYSize;
	public int numOfColors;
	public int gameType;
	public int[] initialAP = {0, 10, 20, 30, 50, 100, 999};
	public int AP_Pointer;
	public int connection;

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
}
