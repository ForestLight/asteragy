package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class EffectCommandPluto extends Effect {

	private static final int frame = 6;

	private static final Image effect = Game.loadImage("pluto_effect.gif");

	private Field field;
	
	private final Point aster;

	private Point size;

	private Point location;

	// private boolean end;

	// private boolean circle;

	// private Graphics g;

	public EffectCommandPluto(Field field, Point aster) {
		this.field = field;
		this.aster = aster;
		setBounds();
	}

	private void setBounds() {
		size = new Point(effect.getWidth(), effect.getHeight());
		location = new Point(field.getGame().getCanvas().getLeftMargin(), field
				.getGame().getCanvas().getTopMargin());
		location.x += aster.x * GameCanvas.measure + GameCanvas.measure / 2;
		location.y += aster.y * GameCanvas.measure + GameCanvas.measure / 2;
		location.x -= size.x / 2;
		location.y -= size.y / 2;
		System.out.println("size(" + size.x + "," + size.y + ")");
		System.out.println("location(" + location.x + "," + location.y + ")");
	}

	public void start(Graphics g) {
		if (!isEffect)
			return;

		// 背景取得
		Image back = field.getScreen().getScreen(location, size);

		int r = 0;
		int l = 0;

		g.setOrigin(location.x, location.y);
		// g.clipRect(0, 0, size.x, size.y);

		while (l < size.x) {
			g.lock();
			g.drawImage(effect, r, 0, r, 0, frame, size.y);
			g.drawImage(back, l, 0, l, 0, frame, size.y);
			g.unlock(true);

			if (r > 9)
				l += frame;

			if (r <= size.x)
				r += frame;

			Game.sleep(300 / CanvasControl.f);
		}

		// g.clearClip();

		g.drawImage(effect, 0, 0);

		Game.sleep(1000 / CanvasControl.f);

		g.setOrigin(0, 0);

		for (int i = 0; i < 10; i++) {

			g.lock();

			g.setColor(Graphics.getColorOfRGB(255, 255, 255, i * 25));
			g.fillRect(0, 0, field.getGame().getCanvas().getWidth(), field
					.getGame().getCanvas().getHeight());

			g.unlock(true);

			Game.sleep(1000 / CanvasControl.f);
		}

		field.getGame().getCanvas().repaint();
		field.getScreen().flipScreen();
	}
	
	/*
	 * public void start(Graphics g) { if (!isEffect) return;
	 * 
	 * this.g = g; // TODO 自動生成されたメソッド・スタブ Thread back = new Thread(this);
	 * back.start();
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f * 10); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); }
	 * 
	 * int[] matrix = new int[6];
	 * 
	 * double theta = Math.PI * 2 / 30;
	 * 
	 * matrix[2] = 0; matrix[5] = 0;
	 * 
	 * int x = effects[1].getWidth() / 2; int y = effects[1].getHeight() / 2;
	 * int height = (int) (y * Math.sin(theta));
	 * 
	 * circle = false;
	 * 
	 * for (int i = 0; i < 30; i++) {
	 * 
	 * matrix[0] = (int) (4096 * Math.cos(theta * i)); matrix[1] = (int) (4096 *
	 * Math.sin(theta * i)); matrix[3] = (int) (-4096 * Math.sin(theta * i));
	 * matrix[4] = (int) (4096 * Math.cos(theta * i));
	 * 
	 * synchronized (g) { field.setOrignAster(g, aster, GameCanvas.measure / 2,
	 * GameCanvas.measure / 2);
	 * 
	 * g.lock();
	 * 
	 * g.drawImage(effects[1], matrix, x, y, x, height);
	 * 
	 * g.unlock(true); } try { Thread.sleep(1000 / CanvasControl.f); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); } }
	 * 
	 * circle = true;
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f * 100); } catch
	 * (InterruptedException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); }
	 * 
	 * end = true; g.setOrigin(0, 0);
	 * 
	 * for (int i = 0; i < 10; i++) {
	 * 
	 * g.lock();
	 * 
	 * g.setColor(Graphics.getColorOfRGB(255, 255, 255, i * 25)); g.fillRect(0,
	 * 0, field.getGame().getCanvas().getWidth(), field
	 * .getGame().getCanvas().getHeight());
	 * 
	 * g.unlock(true);
	 * 
	 * try { Thread.sleep(1000 / CanvasControl.f); } catch (InterruptedException
	 * e) { // TODO 自動生成された catch ブロック e.printStackTrace(); } }
	 * 
	 * field.getGame().getCanvas().getBackImage().paint(g); for (int i = 0; i <
	 * 2; i++) { field.getGame().getPlayers()[i].repaint(); } }
	 * 
	 * public void run() { // TODO 自動生成されたメソッド・スタブ
	 * 
	 * end = false;
	 * 
	 * double theta = Math.PI * 2 / 30;
	 * 
	 * int[] matrix = new int[6];
	 * 
	 * final int x = 50; final int eh = effects[0].getHeight() / 2; final int ew =
	 * -effects[0].getWidth() / 2; final double angle = Math.PI / 2;
	 * 
	 * int w = -effects[1].getWidth() / 2; int h = -effects[1].getHeight() / 2;
	 * 
	 * int time = 0;
	 * 
	 * while (!end) { synchronized (g) {
	 * 
	 * g.lock(); field.repaintAsterRect(g, lefttop, rightbottom); //
	 * field.getGame().getCanvas().getBackImage().paintAsterBackRect(g, //
	 * lefttop, rightbottom);
	 * 
	 * field.setOrignAster(g, aster, GameCanvas.measure / 2, GameCanvas.measure /
	 * 2);
	 * 
	 * for (int i = 0; i < 4; i++) {
	 * 
	 * matrix[0] = (int) (4096 * (Math.cos(theta * time))); matrix[1] = (int)
	 * (-4096 * (Math.sin(theta * time))); matrix[2] = (int) (4096 * (ew *
	 * Math.cos(theta * time) + eh Math.sin(theta * time) + x Math.cos(theta *
	 * time + angle * i))); matrix[3] = (int) (4096 * (Math.sin(theta * time)));
	 * matrix[4] = (int) (4096 * (Math.cos(theta * time))); matrix[5] = (int)
	 * (4096 * (ew * Math.cos(theta * time) - eh Math.sin(theta * time) + x
	 * Math.sin(theta * time + angle * i)));
	 * 
	 * g.drawImage(effects[0], matrix); g.unlock(false); } }
	 * 
	 * synchronized (g) { if (circle) g.drawImage(effects[1], w, h); } //
	 * field.repaintAsterRectNoBack(lefttop, rightbottom);
	 * 
	 * try { Thread.sleep(300 / CanvasControl.f); } catch (InterruptedException
	 * e1) { // TODO 自動生成された catch ブロック e1.printStackTrace(); }
	 * 
	 * if (time < 30) time++; else time = 0; //
	 * field.getGame().getCanvas().repaint(); } field.repaintAsterRect(g,
	 * lefttop, rightbottom); }
	 */
}
