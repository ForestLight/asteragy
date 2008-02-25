package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class EffectCommandPluto implements Effect, Runnable {

	private static final int effect_number = 2;

	private static Image[] effects;

	private final Field field;

	private final Point aster;

	private Point lefttop;

	private Point rightbottom;

	private boolean end;

	private boolean circle;

	public EffectCommandPluto(Field field, Point aster) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.field = field;
		this.aster = aster;
		loadImage();
		setBounds();
	}

	private void setBounds() {
		lefttop = aster.clone();
		rightbottom = aster.clone();

		lefttop.x -= 5;
		lefttop.y -= 5;
		rightbottom.x += 7;
		rightbottom.y += 7;
	}

	private void loadImage() {

		if (effects != null)
			return;

		effects = new Image[effect_number];

		for (int i = 0; i < effects.length; i++) {
			try {
				// リソースから読み込み
				MediaImage m = MediaManager.getImage("resource:///pluto_effect"
						+ (i + 1) + ".gif");
				// メディアの使用開始
				m.use();
				// 読み込み
				effects[i] = m.getImage();
			} catch (Exception e) {
			}
		}

	}

	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		Thread back = new Thread(this);
		back.start();

		try {
			Thread.sleep(1000 / CanvasControl.f * 10);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Graphics g = field.getGame().getCanvas().getGraphics();

		int[] matrix = new int[6];

		double theta = Math.PI * 2 / 30;

		matrix[2] = 0;
		matrix[5] = 0;

		int x = effects[1].getWidth() / 2;
		int y = effects[1].getHeight() / 2;
		int height = (int) (y * Math.sin(theta));

		circle = false;

		for (int i = 0; i < 30; i++) {

			matrix[0] = (int) (4096 * Math.cos(theta * i));
			matrix[1] = (int) (4096 * Math.sin(theta * i));
			matrix[3] = (int) (-4096 * Math.sin(theta * i));
			matrix[4] = (int) (4096 * Math.cos(theta * i));

			field.setOrignAster(g, aster, GameCanvas.measure / 2,
					GameCanvas.measure / 2);

			g.lock();

			g.drawImage(effects[1], matrix, x, y, x, height);

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		circle = true;

		try {
			Thread.sleep(1000 / CanvasControl.f * 100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		end = true;
		g.setOrigin(0, 0);

		for (int i = 0; i < 10; i++) {

			g.lock();

			g.setColor(Graphics.getColorOfRGB(255, 255, 255, i * 25));
			g.fillRect(0, 0, field.getGame().getCanvas().getWidth(), field
					.getGame().getCanvas().getHeight());

			g.unlock(true);

			try {
				Thread.sleep(1000 / CanvasControl.f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		field.getGame().getCanvas().getBackImage().paint(g);
		for (int i = 0; i < 2; i++) {
			field.getGame().getPlayers()[i].repaint();
		}

	}

	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		Graphics g = field.getGame().getCanvas().getGraphics();

		end = false;

		double theta = Math.PI * 2 / 30;

		int[] matrix = new int[6];

		final int x = 50;
		final int eh = effects[0].getHeight() / 2;
		final int ew = -effects[0].getWidth() / 2;
		final double angle = Math.PI / 2;

		int w = -effects[1].getWidth() / 2;
		int h = -effects[1].getHeight() / 2;

		int time = 0;

		while (!end) {

			g.lock();
			field.repaintAsterRect(g, lefttop, rightbottom);
			// field.getGame().getCanvas().getBackImage().paintAsterBackRect(g,
			// lefttop, rightbottom);

			field.setOrignAster(g, aster, GameCanvas.measure / 2,
					GameCanvas.measure / 2);

			for (int i = 0; i < 4; i++) {

				matrix[0] = (int) (4096 * (Math.cos(theta * time)));
				matrix[1] = (int) (-4096 * (Math.sin(theta * time)));
				matrix[2] = (int) (4096 * (ew * Math.cos(theta * time) + eh
						* Math.sin(theta * time) + x
						* Math.cos(theta * time + angle * i)));
				matrix[3] = (int) (4096 * (Math.sin(theta * time)));
				matrix[4] = (int) (4096 * (Math.cos(theta * time)));
				matrix[5] = (int) (4096 * (ew * Math.cos(theta * time) - eh
						* Math.sin(theta * time) + x
						* Math.sin(theta * time + angle * i)));

				g.drawImage(effects[0], matrix);

			}

			if (circle)
				g.drawImage(effects[1], w, h);

			// field.repaintAsterRectNoBack(lefttop, rightbottom);

			g.unlock(false);

			try {
				Thread.sleep(300 / CanvasControl.f);
			} catch (InterruptedException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}

			if (time < 30)
				time++;
			else
				time = 0;

			// field.getGame().getCanvas().repaint();

		}
		field.repaintAsterRect(g, lefttop, rightbottom);

	}
}
