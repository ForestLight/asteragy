package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectCommandMars implements Effect, PaintAsterItem {

	private static Image effect;

	private final Field field;

	private final Point point;

	private final Point aster;

	private final PaintAsterItem paint;

	private Point lefttop;

	private Point rightbottom;

	private int time;

	public EffectCommandMars(Field field, AsterClass cls, Point point) {
		// TODO 自動生成されたコンストラクター・スタブ
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

	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = field.getGame().getCanvas().getGraphics();

		field.setOrignAster(g, aster);

		for (time = 0; time < 17; time++) {

			g.lock();
			field.repaintAsterRect(lefttop, rightbottom);

			g.drawImage(effect, time * (point.x - aster.x), time
					* (point.y - aster.y), 0, (GameCanvas.measure - 1) * time,
					GameCanvas.measure - 1, GameCanvas.measure - 1);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		time--;

		field.getAster(point).setPaint(this);
		field.repaintAster(point);

		try {
			Thread.sleep(1000 / CanvasControl.f * 10);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		field.getAster(point).setPaint(paint);
		field.repaintAster(point);

	}

	public int getHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return paint.getHeight();
	}

	public int getWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return paint.getWidth();
	}

	public void resetSize() {
		// TODO 自動生成されたメソッド・スタブ
		paint.resetSize();
	}

	public void setClass(AsterClass aster) {
		// TODO 自動生成されたメソッド・スタブ
		paint.setClass(aster);
	}

	public void setColor(int color) {
		// TODO 自動生成されたメソッド・スタブ
		paint.setColor(color);
	}

	public void setSize(int width, int height) {
		// TODO 自動生成されたメソッド・スタブ
		paint.setSize(width, height);
	}

	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.drawImage(effect, 0, 0, 0, (GameCanvas.measure - 1) * time,
				GameCanvas.measure - 1, GameCanvas.measure - 1);
	}

}
