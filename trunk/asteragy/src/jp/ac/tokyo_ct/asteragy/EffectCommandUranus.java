package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandUranus extends Effect {

	// private static final int number = 3;

	// private static Image effect;

	private final Field field;

	private final Point target1;

	private final Point target2;

	private Point direct1;

	private Point direct2;

	// private boolean end;

	// private Graphics g;

	public EffectCommandUranus(Field field, Point target1, Point target2) {
		this.field = field;
		this.target1 = target1;
		this.target2 = target2;
		setDirect();
		// loadImage();
	}

	private void setDirect() {
		direct1 = new Point();
		direct2 = new Point();
		direct1.x = target1.x - target2.x;
		direct1.y = target1.y - target2.y;
		// if (direct1.x != 0)
		// direct1.x /= Math.abs(direct1.x);
		// if (direct1.y != 0)
		// direct1.y /= Math.abs(direct1.y);
		direct2.x = direct1.x * -1;
		direct2.y = direct1.y * -1;
	}

	/*
	 * private void loadImage() {
	 * 
	 * if (effect != null) return; try { // リソースから読み込み MediaImage m =
	 * MediaManager .getImage("resource:///uranus_effect.gif"); // メディアの使用開始
	 * m.use(); // 読み込み effect = m.getImage(); } catch (Exception e) { } }
	 */

	public void start(Graphics g) {
		if (!isEffect)
			return;

		Point size = new Point(GameCanvas.measure - 1, GameCanvas.measure - 1);
		Image t1 = field.getScreen().getScreen(field.getAsterLocation(target1),
				size);
		Image t2 = field.getScreen().getScreen(field.getAsterLocation(target2),
				size);

		for (int i = 0; i < 10; i++) {
			filt(t1, direct1);
			filt(t2, direct2);
			// ave(t1);
			// ave(t2);
			g.lock();
			field.setOrignAster(g, target1);
			g.drawImage(t1, 0, 0);
			field.setOrignAster(g, target2);
			g.drawImage(t2, 0, 0);
			g.unlock(true);

			Game.sleep(400 / CanvasControl.f);

		}
	}

	private void filt(Image image, Point dir) {
		Graphics g = image.getGraphics();
		final int height = image.getHeight();
		final int width = image.getWidth();
		int[] pixsel = new int[width * height];
		int[] con = g.getRGBPixels(0, 0, width, height, null, 0);
		int[] check = new int[] { width * (dir.y - 1) + dir.x - 1,
				width * (dir.y - 1) + dir.x, width * (dir.y - 1) + dir.x + 1,
				width * dir.y + dir.x - 1, width * dir.y + dir.x,
				width * dir.y + dir.x + 1, width * (dir.y + 1) + dir.x - 1,
				width * (dir.y) + dir.x, width * (dir.y + 1) + dir.x + 1 };
		for (int i = 0; i < pixsel.length; i++) {
			int c = 0;
			int[] rgb = new int[3];
			for (int j = 0; j < 3; j++) {
				rgb[j] = 0;
			}
			for (int j = 0; j < check.length; j++) {
				if (i + check[j] < 0 || i + check[j] >= con.length
						|| i % width + (j % 3 - 1) + dir.x < 0
						|| i % width + (j % 3 - 1) + dir.x >= width) {
					continue;
				}
				rgb[0] += (con[i + check[j]] >>> 16) & 0xff;
				rgb[1] += (con[i + check[j]] >>> 8) & 0xff;
				rgb[2] += con[i + check[j]] & 0xff;
				c++;
			}
			for (int j = 0; j < 3; j++) {
				if (c != 0)
					rgb[j] /= c;
			}
			pixsel[i] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
		}
		g.setRGBPixels(0, 0, width, height, pixsel, 0);
	}
	/*
	 * private void ave(Image image) { Graphics g = image.getGraphics(); final
	 * int height = image.getHeight(); final int width = image.getWidth(); int[]
	 * pixsel = new int[width * height]; int[] con = g.getRGBPixels(0, 0, width,
	 * height, null, 0); int[] check = new int[] { -1, 0, 1, -width - 1, -width,
	 * -width + 1, width - 1, width, width + 1 }; int[] n = new int[] {
	 * (con[(height / 2) * width + width / 2] >>> 16) & 0xff, (con[(height / 2) *
	 * width + width / 2] >>> 8) & 0xff, con[(height / 2) * width + width / 2] &
	 * 0xff }; System.out.println(n[0] + "," + n[1] + "," + n[2]); for (int i =
	 * 0; i < pixsel.length; i++) { int c = 0; int[] rgb = new int[3]; for (int
	 * j = 0; j < 3; j++) { rgb[j] = 0; } for (int j = 0; j < check.length; j++) {
	 * if (i + check[j] < 0 || i + check[j] >= con.length) { for (int k = 0; k <
	 * 3; k++) { rgb[k] += n[k]; } continue; } rgb[0] += (con[i + check[j]] >>>
	 * 16) & 0xff; rgb[1] += (con[i + check[j]] >>> 8) & 0xff; rgb[2] += con[i +
	 * check[j]] & 0xff; c++; } for (int j = 0; j < 3; j++) { rgb[j] /= c; }
	 * pixsel[i] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]; }
	 * g.setRGBPixels(0, 0, width, height, pixsel, 0); }
	 */
	/*
	 * public void start(Graphics g) { if (!isEffect) return; // TODO
	 * 自動生成されたメソッド・スタブ this.g = g;
	 * 
	 * Thread over = new Thread(this); over.start();
	 * 
	 * for (int i = 0; i < GameCanvas.measure / number + 2; i++) {
	 * 
	 * g.lock();
	 * 
	 * spritTarget1(g, i); spritTarget2(g, i);
	 * 
	 * g.unlock(true);
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f * 10); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); } }
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f * 30); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); }
	 * 
	 * end = true; }
	 * 
	 * private void spritTarget1(Graphics g, int height) { final CanvasControl
	 * canvas = field.getGame().getCanvas();
	 * 
	 * PaintAsterItem paint = field.getAster(target1).getPaint();
	 * 
	 * canvas.getBackImage().paintAsterBack(g, target2);
	 * 
	 * field.setOrignAster(g, target2);
	 * 
	 * for (int i = 0; i < number; i++) { g.setClip(0, i * (GameCanvas.measure /
	 * number + 1), GameCanvas.measure - 1, height); paint.paint(g);
	 * g.clearClip(); } }
	 * 
	 * private void spritTarget2(Graphics g, int height) { final CanvasControl
	 * canvas = field.getGame().getCanvas();
	 * 
	 * PaintItem paint = field.getAster(target2).getPaint();
	 * 
	 * canvas.getBackImage().paintAsterBack(g, target1);
	 * 
	 * field.setOrignAster(g, target1);
	 * 
	 * for (int i = 0; i < number; i++) { g.setClip(0, i * (GameCanvas.measure /
	 * number + 1), GameCanvas.measure - 1, height); paint.paint(g);
	 * g.clearClip(); } }
	 * 
	 * public void run() { animationOver(); }
	 * 
	 * private void animationOver() {
	 * 
	 * Point over1 = target1.clone(); Point over2 = target2.clone();
	 * 
	 * over1.y--; over2.y--;
	 * 
	 * final int height = 10;
	 * 
	 * int i = 0;
	 * 
	 * end = false;
	 * 
	 * while (!end) { synchronized (g) { g.lock(); field.repaintAster(g, over1);
	 * field.repaintAster(g, over2);
	 * 
	 * field.setOrignAster(g, target1, 0, -1 * height); g.drawImage(effect, 0,
	 * 0, 0, i * height, GameCanvas.measure - 1, height); field.setOrignAster(g,
	 * target2, 0, -1 * height); g.drawImage(effect, 0, 0, 0, i * height,
	 * GameCanvas.measure - 1, height);
	 * 
	 * g.unlock(true); }
	 * 
	 * if (i < 9) i++; else i = 0;
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } } }
	 */
}
