package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke x, yの座標を保持するクラス
 */
final class Point {
	int x;

	int y;

	Point() {
		x = 0;
		y = 0;
	}

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	Point clone() {
		return new Point(x, y);
	}

	Point add(Point point) {
		Point p = this.clone();
		p.x += point.x;
		p.y += point.y;
		return p;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/*
	 * boolean equals(Object rhs) { if (this == rhs) return true; try { return
	 * equals((Point) rhs); } catch (ClassCastException e) { return false; } }
	 */
	boolean equals(Point rhs) {
		return x == rhs.x && y == rhs.y;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	// int hashCode() {
	// return x ^ ~y;
	// }
	/*
	 * (非 Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return x + " " + y;
	}
}
