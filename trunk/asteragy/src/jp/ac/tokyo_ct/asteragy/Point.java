package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke x, y�̍��W��ێ�����N���X
 */
public final class Point {
	public int x;

	public int y;

	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object rhs) {
		if (this == rhs)
			return true;
		try {
			return equals((Point) rhs);
		} catch (ClassCastException e) {
			return false;
		}
	}

	public boolean equals(Point rhs) {
		return x == rhs.x && y == rhs.y;
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return x ^ ~y;
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return x + " " + y;
	}
}
