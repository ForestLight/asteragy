package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandStar implements Effect {

	private static Image effect;

	private final Point point;

	private final Point to;

	private final Field field;

	// 0:左 2:右 1:上 3:下
	private final int direction;

	public EffectCommandStar(Field field, AsterClass cls, Point a, Point b) {
		this.field = field;
		if (field.asterToPoint(cls.getAster()).equals(a)) {
			point = a;
			to = b;
		} else {
			point = b;
			to = a;
		}
		if (point.x > to.x)
			direction = 0;
		else if (point.x < to.x)
			direction = 2;
		else if (point.y > to.y)
			direction = 1;
		else
			direction = 3;
		loadImage();
	}

	private void loadImage() {
		/*
		 * effect = Image.createImage(GameCanvas.measure, GameCanvas.measure);
		 * 
		 * Graphics g = effect.getGraphics();
		 * 
		 * g.setColor(Graphics.getColorOfRGB(0, 0, 0)); g.fillRect(0, 0,
		 * GameCanvas.measure, GameCanvas.measure);
		 * g.setColor(Graphics.getColorOfRGB(255, 255, 128)); g.drawRect(0, 0,
		 * GameCanvas.measure - 1, GameCanvas.measure - 1);
		 * 
		 * g.dispose();
		 */

		if (effect != null)
			return;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///star_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	final double theta = Math.PI * 2 / 30;

	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		final Graphics g = field.getGame().getCanvas().getGraphics();

		field.setOrignAster(g, point);

		int[] matrix = new int[6];
		final int define = 4096;

		for (int i = 0; i < 30; i++) {

			matrix[0] = (int) (define * (Math.cos(theta * i)));
			matrix[1] = (int) (define * (-Math.sin(theta * i)));
			matrix[2] = (int) (define * (Math.cos(theta * i)
					* (-effect.getWidth() / 2) - Math.sin(theta * i)
					* (-effect.getHeight() / 2) + effect.getWidth() / 2 + 1));
			matrix[3] = (int) (define * (Math.sin(theta * i)));
			matrix[4] = (int) (define * (Math.cos(theta * i)));
			matrix[5] = (int) (define * (Math.sin(theta * i)
					* (-effect.getWidth() / 2) + Math.cos(theta * i)
					* (-effect.getHeight() / 2) + effect.getWidth() / 2 + 1));

			field.getGame().getCanvas().getBackImage().paintAsterBack(g, to);
			g.lock();
			field.setOrignAster(g, to);

			g.drawImage(effect, matrix);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		PaintAsterItem paint = field.getAster(point).getPaint();

		int ax = 0;
		int ay = 0;

		for (int i = 0; i < GameCanvas.measure; i++) {
			field.getGame().getCanvas().getBackImage().paintAsterBack(g, to);
			field.getGame().getCanvas().getBackImage().paintAsterBack(g, point);
			g.lock();

			field.setOrignAster(g, point, ax, ay);
			paint.paint(g);

			g.unlock(true);

			if (direction % 2 == 0) {
				ax += (direction - 1);
			} else {
				ay += (direction - 2);
			}

			try {
				Thread.sleep(150 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
