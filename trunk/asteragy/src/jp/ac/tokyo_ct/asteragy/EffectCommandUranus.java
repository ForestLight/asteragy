package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandUranus extends Effect implements Runnable {

	private static final int number = 3;

	private static Image effect;

	private final Field field;

	private final Point target1;

	private final Point target2;

	private boolean end;

	public EffectCommandUranus(Field field, Point target1, Point target2) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.field = field;
		this.target1 = target1;
		this.target2 = target2;
		loadImage();
	}

	private void loadImage() {

		if (effect != null)
			return;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager
					.getImage("resource:///uranus_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start() {
		if(!isEffect)
			return;
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = field.getGame().getCanvas().getGraphics();

		Thread over = new Thread(this);
		over.start();

		for (int i = 0; i < GameCanvas.measure / number + 2; i++) {

			g.lock();

			spritTarget1(g, i);
			spritTarget2(g, i);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f * 10);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		
		

		try {
			Thread.sleep(1000 / CanvasControl.f * 30);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		end = true;

	}

	private void spritTarget1(Graphics g, int height) {
		final CanvasControl canvas = field.getGame().getCanvas();

		PaintAsterItem paint = field.getAster(target1).getPaint();

		canvas.getBackImage().paintAsterBack(g, target2);

		field.setOrignAster(g, target2);

		for (int i = 0; i < number; i++) {
			g.setClip(0, i * (GameCanvas.measure / number + 1),
					GameCanvas.measure - 1, height);
			paint.paint(g);
			g.clearClip();
		}
	}

	private void spritTarget2(Graphics g, int height) {
		final CanvasControl canvas = field.getGame().getCanvas();

		PaintItem paint = field.getAster(target2).getPaint();

		canvas.getBackImage().paintAsterBack(g, target1);

		field.setOrignAster(g, target1);

		for (int i = 0; i < number; i++) {
			g.setClip(0, i * (GameCanvas.measure / number + 1),
					GameCanvas.measure - 1, height);
			paint.paint(g);
			g.clearClip();
		}

	}

	public void run() {
		animationOver();
	}

	private void animationOver() {
		Graphics g = field.getGame().getCanvas().getGraphics();

		Point over1 = target1.clone();
		Point over2 = target2.clone();

		over1.y--;
		over2.y--;

		final int height = 10;

		int i = 0;

		end = false;

		while (!end) {

			g.lock();
			field.repaintAster(over1);
			field.repaintAster(over2);

			field.setOrignAster(g, target1, 0, -1 * height);
			g.drawImage(effect, 0, 0, 0, i * height, GameCanvas.measure - 1,
					height);
			field.setOrignAster(g, target2, 0, -1 * height);
			g.drawImage(effect, 0, 0, 0, i * height, GameCanvas.measure - 1,
					height);

			g.unlock(true);

			if (i < 9)
				i++;
			else
				i = 0;

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
