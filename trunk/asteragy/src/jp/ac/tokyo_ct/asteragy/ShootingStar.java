package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Canvas;
import com.nttdocomo.ui.Graphics;

final class ShootingStar {

	private static final int color = Graphics.getColorOfRGB(0x00, 0x80, 0x8c);

	private final Canvas canvas;

	private ShootingStar parent;

	private ShootingStar child;

	private Point point;

	private Point start;

	private int time;

	ShootingStar(Canvas canvas, ShootingStar parent) {
		this.canvas = canvas;
		this.parent = parent;
		child = null;
		newStar();
	}

	void setParent(ShootingStar parent) {
		this.parent = parent;
	}

	void setChild(ShootingStar child) {
		this.child = child;
	}

	void newStar() {
		if (parent != null) {
			if (Game.rand(4) >>> 1 == 0) {
				if (child != null)
					child.setParent(parent);
				parent.setChild(child);
				return;
			}
		}
		point = new Point(Game.rand(canvas.getWidth()), 
				Game.rand(canvas.getHeight()));
		start = point.clone();
		time = Game.rand(8);
		if (child == null) {
			if (Game.rand(10) == 0) {
				child = new ShootingStar(canvas, this);
			}
		}
	}

	void paint(Graphics g) {
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
