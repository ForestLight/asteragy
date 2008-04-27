package jp.ac.tokyo_ct.asteragy;

final class SimpleMath {

	static final int cycle = 360;

	static final int divide = 4096;

	private static final int[] value = { 0, 71, 142, 214, 285, 356, 428, 499,
			570, 640, 711, 781, 851, 921, 990, 1060, 1129, 1197, 1265, 1333,
			1400, 1467, 1534, 1600, 1665, 1731, 1795, 1859, 1922, 1985, 2047,
			2109, 2170, 2230, 2290, 2349, 2407, 2465, 2521, 2577, 2632, 2687,
			2740, 2793, 2845, 2896, 2946, 2995, 3043, 3091, 3137, 3183, 3227,
			3271, 3313, 3355, 3395, 3435, 3473, 3510, 3547, 3582, 3616, 3649,
			3681, 3712, 3741, 3770, 3797, 3823, 3848, 3872, 3895, 3917, 3937,
			3956, 3974, 3991, 4006, 4020, 4033, 4045, 4056, 4065, 4073, 4080,
			4086, 4090, 4093, 4095, 4096 };

	/**
	 * 擬似サイン
	 * 
	 * @param theta
	 *            度数法
	 * @return sin(theta)*4096
	 */
	static int sin(int theta) {
		int x;
		int minus;
		theta = (theta % cycle + cycle) % cycle;
		if (theta >= cycle / 2) {
			minus = -1;
		} else {
			minus = 1;
		}
		theta %= cycle / 2;
		if (theta > cycle / 4) {
			theta %= cycle / 4;
			x = value[cycle / 4 - theta] * minus;
		} else {
			x = value[theta] * minus;
		}
		return x;
	}

	/**
	 * 擬似コサイン
	 * 
	 * @param theta
	 *            度数法
	 * @return sin(theta)*4096
	 */
	static int cos(int theta) {
		return sin(theta + cycle / 4);
	}
}
