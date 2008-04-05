package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandEarth extends Effect {

	private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandEarth(Field field, Point point) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.field = field;
		this.point = point;
		loadImage();
	}

	private void loadImage() {
		if (effect != null)
			return;

		try {
			// リソースから読み込み
			MediaImage m = MediaManager
					.getImage("resource:///earth_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		if (!isEffect)
			return;

		for (int i = 0; i < 10; i++) {

			g.lock();

			field.repaintAster(g, point);

			field.setOrignAster(g, point);

			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		for (int i = 10; i >= 0; i--) {

			g.lock();

			field.getGame().getCanvas().getBackImage().paintAsterBack(g, point);

			field.setOrignAster(g, point);

			g.drawImage(effect, 0, 0, 0, i * 18, 18, 18);

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
