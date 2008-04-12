package jp.ac.tokyo_ct.asteragy;

/**
 * @author Yusuke x, y‚ÌÀ•W‚ğ•Û‚·‚éƒNƒ‰ƒX
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

	public Point clone() {
		return new Point(x, y);
	}

	public Point add(Point point) {
		Point p = this.clone();
		p.x += point.x;
		p.y += point.y;
		return p;
	}

	/*
	 * (”ñ Javadoc)
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
	 * (”ñ Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return x ^ ~y;
	}

	/*
	 * (”ñ Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return x + " " + y;
	}
}
