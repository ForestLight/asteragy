package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandNeptune extends Effect {

	private static Image effect;

	private final Field field;

	private final Point point;

	public EffectCommandNeptune(Field field, Point point) {
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
					.getImage("resource:///neptune_effect.gif");
			// メディアの使用開始
			m.use();
			// 読み込み
			effect = m.getImage();
		} catch (Exception e) {
		}

	}

	public void start(Graphics g) {
		if(!isEffect)
			return;
		// TODO 自動生成されたメソッド・スタブ

		field.setOrignAster(g, point, 0, GameCanvas.measure / 2);

		for (int i = 0; i < 30; i++) {
			g.lock();

			g.drawImage(effect, 0, -1 * effect.getHeight(), 0, 0, effect
					.getWidth(), effect.getHeight() / 10 * i);

			g.unlock(true);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		g.setOrigin(0, 0);
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.fillRect(0, 0, field.getGame().getCanvas().getWidth(), field
				.getGame().getCanvas().getHeight());

		try {
			Thread.sleep(300 / CanvasControl.f);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		field.getGame().getCanvas().repaint();
	}
}
