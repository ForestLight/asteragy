package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectCommandMars extends Effect implements PaintAsterItem {

	private static Image effect;

	private final Field field;

	private final Point point;

	private final Point aster;

	private final PaintAsterItem paint;

	private Point lefttop;

	private Point rightbottom;

	private int time;

	public EffectCommandMars(Field field, AsterClass cls, Point point) {
		this.field = field;
		this.point = point;
		this.aster = field.asterToPoint(cls.getAster());
		paint = field.getAster(point).getPaint();
		loadImage();
		setRect();
	}

	private void setRect() {
		int lx = aster.x;
		int ty = aster.x;
		int rx = aster.y;
		int by = aster.y;
		if (point.x < aster.x) {
			lx = point.x;
			rx = aster.x;
		} else {
			lx = aster.x;
			rx = point.x;
		}

		if (point.y < aster.y) {
			ty = point.y;
			by = aster.y;
		} else {
			ty = aster.y;
			by = point.y;
		}

		lefttop = new Point(lx, ty);
		rightbottom = new Point(rx, by);
	}

	private void loadImage() {
		if (effect != null)
			return;

		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///mars_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start(Graphics g) {
		if (!isEffect)
			return;

		for (time = 0; time < 17; time++) {

			g.lock();
			field.repaintAsterRect(g, lefttop, rightbottom);
			field.setOrignAster(g, aster);

			g.drawImage(effect, time * (point.x - aster.x), time
					* (point.y - aster.y), 0, (GameCanvas.measure - 1) * time,
					GameCanvas.measure - 1, GameCanvas.measure - 1);

			g.unlock(true);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		time--;

		field.getAster(point).setPaint(this);
		field.repaintAster(g, point);

		try {
			Thread.sleep(1000 / CanvasControl.f * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		field.getAster(point).setPaint(paint);
		field.repaintAster(g, point);

	}

	public int getHeight() {
		return paint.getHeight();
	}

	public int getWidth() {
		return paint.getWidth();
	}

	public void resetSize() {
		paint.resetSize();
	}

	public void setClass(AsterClass aster) {
		paint.setClass(aster);
	}

	public void setColor(int color) {
		paint.setColor(color);
	}

	public void setSize(int width, int height) {
		paint.setSize(width, height);
	}

	public void paint(Graphics g) {
		g.drawImage(effect, 0, 0, 0, (GameCanvas.measure - 1) * time,
				GameCanvas.measure - 1, GameCanvas.measure - 1);
	}

}
