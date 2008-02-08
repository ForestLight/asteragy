package jp.ac.tokyo_ct.asteragy;

public class Option {
	public int fieldXSize;
	public int fieldYSize;
	public int numOfColors;
	public int gameType;

	public Option(int XSize, int YSize, int color) {
		fieldXSize = XSize;
		fieldYSize = YSize;
		numOfColors = color;
		gameType = -1;
	}

	public Option() {
		fieldXSize = 9;
		fieldYSize = 9;
		numOfColors = 5;
		gameType = -1;
	}
}
