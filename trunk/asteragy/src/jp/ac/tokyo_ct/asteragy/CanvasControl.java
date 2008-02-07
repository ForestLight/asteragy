package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class CanvasControl {

	public static final int f = 40;

	private final Game game;

	private final GameCanvas canvas;

	private final BackImage back;

	private final Cursor cursor;

	private final CommonCommand commoncommand;

	private final SunCommand suncommand;

	private final Range range;

	private int topmargin;

	private int leftmargin;

	public CanvasControl(Game game) {
		this.game = game;
		canvas = new GameCanvas(this);
		back = new BackImage(this);
		cursor = new Cursor(this);
		range = new Range(this);

		commoncommand = new CommonCommand(this);
		suncommand = new SunCommand(this);
		Display.setCurrent(canvas);
	}

	public Graphics getGraphics() {
		return canvas.getGraphics();
	}

	private void setFieldMargin() {
		final Field field = game.getField();
		topmargin = (getHeight() - field.getY() * GameCanvas.measure) / 2;
		leftmargin = (getWidth() - field.getX() * GameCanvas.measure) / 2;
	}

	public int getTopMargin() {
		if (topmargin <= 0)
			setFieldMargin();
		return topmargin;
	}

	public int getLeftMargin() {
		if (leftmargin <= 0)
			setFieldMargin();
		return leftmargin;
	}

	public BackImage getBackImage() {
		return back;
	}

	public Range getRange() {
		return range;
	}

	public Field getField() {
		return game.getField();
	}

	public PaintItem[] getPlayers() {
		return game.getPlayers();
	}

	public void repaint() {
		canvas.repaint();
	}

	/**
	 * カーソルを取得
	 * 
	 * @return カーソル
	 */
	public Cursor getCursor() {
		return cursor;
	}

	public CommonCommand getCommonCommand() {
		return commoncommand;
	}

	public SunCommand getSunCommand() {
		return suncommand;
	}

	public PaintItem getCommand() {
		if (suncommand.visible()) {
			System.out.println("return suncommand");
			return suncommand;
		}
		System.out.println("return commoncommand");
		return commoncommand;
	}

	/**
	 * 高さを取得
	 * 
	 * @return 高さ
	 */
	public int getHeight() {
		return canvas.getHeight();
	}

	public int fieldHeight() {
		return game.getField().getY() * GameCanvas.measure;
	}

	/**
	 * 幅を取得
	 * 
	 * @return 幅
	 */
	public int getWidth() {
		return canvas.getWidth();
	}

	public int fieldWidth() {
		return game.getField().getX() * GameCanvas.measure;
	}

	private volatile EventProcesser eventProcesser;

	public void setEventProcesser(EventProcesser e) {
		eventProcesser = e;
	}

	/**
	 * 現在のイベントプロセッサを取得する。
	 * 
	 * @return 設定されているイベントプロセッサ。なければnull。
	 */
	public EventProcesser getEventProcesser() {
		return eventProcesser;
	}

	/**
	 * イベントプロセッサを削除する。
	 */
	public void resetEventProcesser() {
		eventProcesser = null;
	}

	/**
	 * イベントプロセッサを削除する。
	 * 
	 * @param e
	 *            削除するイベントプロセッサ
	 * @return 削除したらtrue、しなければfalse; 一致したら削除する。
	 */
	public boolean removeEventProcesser(EventProcesser e) {
		if (e == eventProcesser) {
			eventProcesser = null;
			return true;
		} else {
			return false;
		}
	}

	public void setCurrent() {
		Display.setCurrent(canvas);
	}

	public void onTurnStart(Player player) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("onTurnStart");
		game.getPlayer1().repaint();
		game.getPlayer2().repaint();

		Graphics g = getGraphics();

		// 背景In
		int x = getWidth();
		int y = getHeight() / 4;
		while (x > 0) {
			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(false);

			x -= 24;

			try {
				Thread.sleep(1000 / f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 文字In
		String string = player.getName() + "のターン";
		int stringwidth = Font.getDefaultFont().getBBoxWidth(string);
		int wx = getWidth();
		int wy = getHeight() / 2 + Font.getDefaultFont().getHeight() / 2;
		while (wx > getWidth() / 2 - stringwidth / 2) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(false);

			wx -= 24;

			try {
				Thread.sleep(1000 / f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 文字Out
		while (wx * -1 < stringwidth) {

			g.lock();

			g.drawImage(player.getTurnOnBack(), x, y);
			g.drawString(string, wx, wy);

			g.unlock(false);

			wx -= 24;

			try {
				Thread.sleep(1000 / f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 背景Out
		while (x * -1 < getWidth()) {
			g.lock();

			canvas.repaint(x + getWidth(), y, getWidth() - x, getHeight() / 2);
			g.drawImage(player.getTurnOnBack(), x, y);

			g.unlock(false);

			x -= 24;

			try {
				Thread.sleep(1000 / f);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

	}

	public boolean isInit() {
		return game.isInit();
	}

	public void paintNowloading(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.drawRect(0, getHeight() / 4, getWidth(), getHeight() * 3 / 4);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		g.drawString("Now Loading...", 78, 126);

	}

	private PaintString spaint;

	public void paintString(String string, boolean visible) {
		if (visible)
			spaint = new PaintString(this, string);
		else
			spaint = null;
		paintString(getGraphics());
	}

	private void paintString(Graphics g) {
		if (spaint != null)
			spaint.paint(g);
	}

	public void paintOver(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		paintString(g);
	}
}
