package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaManager;

public class EffectCommandMercury extends Effect implements PaintAsterItem {

	private static Image effect;

	private final Field field;

	private final Point point;

	private final Aster aster;

	private final PaintAsterItem paint;

	private int time;

	public EffectCommandMercury(Field field, Point point) {
		this.field = field;
		this.point = point;
		aster = field.getAster(point);
		paint = aster.getPaint();
		loadImage();
	}

	private void loadImage() {

		if (effect != null)
			return;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager
					.getImage("resource:///mercury_effect.gif");
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
		// TODO 自動生成されたメソッド・スタブ

		aster.setPaint(this);

		for (time = 0; time < 170 - GameCanvas.measure; time += 3) {
			field.repaintAster(g, point);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		aster.setPaint(paint);
		field.repaintAster(g, point);
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
		System.out.println("Mercury Effect");
		paint.paint(g);
		g.drawImage(effect, 0, 0, 0, time, GameCanvas.measure,
				GameCanvas.measure);
	}

}
