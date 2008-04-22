package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Canvas;
import com.nttdocomo.ui.Graphics;

public class ShootingStar /*implements PaintItem*/ {

	private static final int color = Graphics.getColorOfRGB(0x00, 0x80, 0x8c);

	private final Canvas canvas;

	private ShootingStar parent;

	private ShootingStar child;

	private Point point;

	private Point start;

	private int time;

	public ShootingStar(Canvas canvas, ShootingStar parent) {
		this.canvas = canvas;
		this.parent = parent;
		child = null;
		newStar();
	}

	public void setParent(ShootingStar parent) {
		this.parent = parent;
	}

	public void setChild(ShootingStar child) {
		this.child = child;
	}

	private void newStar() {
		if (parent != null) {
			if ((Game.random.nextInt() >>> 1) % 2 == 0) {
				if (child != null)
					child.setParent(parent);
				parent.setChild(child);
				return;
			}
		}
		point = new Point((Game.random.nextInt() >>> 1) % canvas.getWidth(),
				(Game.random.nextInt() >>> 1) % canvas.getHeight());
		start = point.clone();
		time = (Game.random.nextInt() >>> 1) & 0x7;
		if (child == null) {
			if ((Game.random.nextInt() >>> 1) % 10 == 0) {
				child = new ShootingStar(canvas, this);
			}
		}
	}

	public void paint(Graphics g) {
		if (point == null)
			return;
		point.x -= 3;
		point.y += 3;
		time--;
		g.setColor(color);
		g.drawLine(start.x, start.y, point.x, point.y);
		if (child != null)
			child.paint(g);
		if (time < 0 || point.x < 0 || point.y > canvas.getHeight()) {
			newStar();
		}
	}

}
