package jp.ac.tokyo_ct.asteragy;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import com.nttdocomo.ui.Graphics;

public class Thunder implements PaintItem {

	private static final int MAXTHUNDER = 5;

	private static final int MINSPACE = 5;

	private static final int MAXWIDTH = 10;

	private static final int BACKCOLOR = Graphics.getColorOfRGB(255, 255, 240);

	private static final int FORECOLOR = Graphics.getColorOfRGB(255, 255, 200);
	
	private final Random random;

	private final Point begin;

	private final Point end;

	private Vector[] thunder;

	public Thunder(Point begin, Point end) {
		random = new Random();
		this.begin = begin;
		this.end = end;
		initializeLocation();
	}

	private void initializeLocation() {
		thunder = new Vector[(random.nextInt() >>> 1) % MAXTHUNDER + 1];
		int lenght = (int) Math.sqrt((begin.x - end.x) * (begin.x - end.x)
				+ (begin.y - end.y) * (begin.y - end.y));
		for (int i = 0; i < thunder.length; i++) {
			thunder[i] = new Vector(lenght / MINSPACE);
			createThunder(thunder[i], lenght);
		}
	}

	private void createThunder(Vector point, int lenght) {
		int size = (random.nextInt() >>> 1) % (lenght / MINSPACE) + 1;
		Point interval = new Point(Math.abs(begin.x - end.x) / (size + 1), Math
				.abs(begin.y - end.y)
				/ (size + 1));
		for (int i = 0; i < size; i++) {
			Point k = new Point(begin.x + interval.x * (i + 1), begin.y
					+ interval.y * (i + 1));
			movePoint(k, interval.y);
			point.addElement(k);
		}
	}

	private void movePoint(Point point, int interval) {
		point.y += (random.nextInt() >>> 1) % interval - (interval / 2);
		point.x += (random.nextInt() >>> 1) % MAXWIDTH - (MAXWIDTH / 2);
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		Enumeration[] enumer = new Enumeration[thunder.length];
		Point[] before = new Point[thunder.length];
		boolean not = true;
		while (not) {
			not = false;
			g.lock();
			for (int i = 0; i < thunder.length; i++) {
				if (enumer[i] == null || before[i] == null) {
					enumer[i] = thunder[i].elements();
					before[i] = begin;
				}
				if (enumer[i].hasMoreElements()) {
					Point p = (Point) enumer[i].nextElement();
					g.setColor(BACKCOLOR);
					g.drawLine(before[i].x - 1, before[i].y, p.x - 1, p.y);
					g.drawLine(before[i].x, before[i].y, p.x, p.y);
					g.drawLine(before[i].x + 1, before[i].y, p.x + 1, p.y);
					g.setColor(FORECOLOR);
					g.drawLine(before[i].x, before[i].y, p.x, p.y);
					before[i] = p;
					if (!enumer[i].hasMoreElements()) {
						g.setColor(BACKCOLOR);
						g.drawLine(before[i].x - 1, before[i].y, end.x - 1,
								end.y);
						g.drawLine(before[i].x, before[i].y, end.x, end.y);
						g.drawLine(before[i].x + 1, before[i].y, end.x + 1,
								end.y);
						g.setColor(FORECOLOR);
						g.drawLine(before[i].x, before[i].y, end.x, end.y);
					}
					not = true;
				}
			}
			g.unlock(true);
			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

}
