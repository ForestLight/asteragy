package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandSaturn extends Effect {

	private static Image effect;

	private final Field field;

	private final Point aster;

	private final Aster[] queue;

	private final Point[] location;

	public EffectCommandSaturn(Field field, AsterClass cls, Aster[] queue) {
		this.field = field;
		this.aster = field.asterToPoint(cls.getAster());
		this.queue = queue;

		location = new Point[queue.length];
		location[0] = field.asterToPoint(queue[0]);

		for (int i = 1, j = 0; j < 16; j++) {
			if (queue[i] == null)
				break;
			if (location[i] == null)
				location[i] = location[i - 1].clone();

			if (j < 4)
				location[i].x++;
			else if (j < 8)
				location[i].y++;
			else if (j < 12)
				location[i].x--;
			else
				location[i].y--;

			if (field.isXInFieldBound(location[i].x)
					&& field.isYInFieldBound(location[i].y)) {
				i++;
			}
		}
		loadImage();
	}

	private void loadImage() {

		if (effect != null)
			return;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager
					.getImage("resource:///saturn_effect.gif");
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

		int[] matrix = new int[6];

		double theta = Math.PI * 2 / 8;

		double r = ((GameCanvas.measure - 1) * 3 / 2) * Math.cos(theta);

		int x = (int) (effect.getWidth() / 2 + r);
		int y = effect.getHeight() / 2;
		int width = (int) ((effect.getWidth()) / 2 + r);
		int height = (int) ((effect.getHeight() / 2) * Math.sin(theta));

		field.setOrignAster(g, aster, GameCanvas.measure / 2,
				GameCanvas.measure / 2);

		for (int i = 0; i < 9; i++) {
			matrix[2] = (int) (4096 * r * Math.cos(theta * i));
			matrix[5] = (int) (-4096 * r * Math.sin(theta * i));
			matrix[0] = (int) (4096 * Math.cos(theta * i));
			matrix[1] = (int) (4096 * Math.sin(theta * i));
			matrix[3] = (int) (-4096 * Math.sin(theta * i));
			matrix[4] = (int) (4096 * Math.cos(theta * i));

			g.lock();
			g.drawImage(effect, matrix, x, y, width, height);
			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		field.repaintAsterRect(g, new Point(aster.x - 1, aster.y - 1),
				new Point(aster.x + 1, aster.y + 1));

		for (int i = 0; i < GameCanvas.measure - 1; i++) {

			g.lock();

			field.getGame().getCanvas().getBackImage().paintAsterBack(g,
					location[0]);
			for (int j = 0; j < location.length; j++) {
				if (location[j] == null)
					break;

				if (location[j + 1] != null) {
					field.getGame().getCanvas().getBackImage().paintAsterBack(
							g, location[j + 1]);

					field.setOrignAster(g, location[j], i
							* (location[j + 1].x - location[j].x), i
							* (location[j + 1].y - location[j].y));
				} else {
					field.setOrignAster(g, location[j], i
							* (location[0].x - location[j].x), i
							* (location[0].y - location[j].y));
				}

				queue[j].getPaint().paint(g);

			}

			field.setOrignAster(g, aster, GameCanvas.measure / 2,
					GameCanvas.measure / 2);
			g
					.drawImage(effect, -effect.getWidth() / 2, -effect
							.getHeight() / 2);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

	}
}
