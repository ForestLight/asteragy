package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Kazuto
 * 
 */
public class CanvasControl {

	public static final int f = 40;

	private final Game game;

	private final GameCanvas canvas;

	private final BackImage back;

	private final Cursor cursor;

	private final CommonCommand commoncommand;

	private final SunCommand suncommand;

	private final Range range;

	private final EffectAsterDisappearControl disappearcontrol;

	private final Screen screen;

	private int topmargin;

	private int leftmargin;

	public CanvasControl(Game game) {
		this.game = game;
		canvas = new GameCanvas(this);
		back = new BackImage(this);
		cursor = new Cursor(this);
		range = new Range(this);
		disappearcontrol = new EffectAsterDisappearControl(this);
		screen = new Screen(canvas);

		commoncommand = new CommonCommand(this);
		suncommand = new SunCommand(this);
		Display.setCurrent(canvas);
		canvas.repaint();
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

	public EffectAsterDisappearControl getDisappearControl() {
		return disappearcontrol;
	}

	public Screen getScreen() {
		return screen;
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

	private PaintItem getCommand() {
		if (suncommand.visible()) {
			System.out.println("return suncommand");
			return suncommand;
		}
		System.out.println("return commoncommand");
		return commoncommand;
	}

	public boolean isInit() {
		return game.isInit();
	}

	/**
	 * 高さを取得
	 * 
	 * @return 高さ
	 */
	public int getHeight() {
		return canvas.getHeight();
	}

	/**
	 * 幅を取得
	 * 
	 * @return 幅
	 */
	public int getWidth() {
		return canvas.getWidth();
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

	public void onTurnStart(Player player) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("onTurnStart");
		game.getPlayer1().repaint();
		game.getPlayer2().repaint();

		Effect turnon = new EffectTurnon(this, player);
		screen.paintEffect(turnon);

	}

	public void paintNowloading(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		g.setColor(Graphics.getColorOfRGB(255, 255, 255));
		g.drawRect(0, getHeight() / 4, getWidth(), getHeight() * 3 / 4);
		g.setColor(Graphics.getColorOfRGB(0, 0, 0));
		g.drawString("Now Loading...", 78, 126);

	}

	public void repaint() {
		Graphics g = screen.getGraphics();
		paint(g);
	}

	public void gameOver() {
	}

	private PaintString spaint;

	public void paintString(String string, boolean visible) {
		if (visible)
			spaint = new PaintString(this, string);
		else
			spaint = null;
		paintString(screen.getGraphics());
	}

	private void paintString(Graphics g) {
		if (spaint != null)
			spaint.paint(g);
	}

	private void paintOver(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		paintString(g);
	}

	private void paint(Graphics g) {
		// 描画
		paintBackGround(g);
		paintFieldSpace(g);
		paintPlayerInfo(g);

		paintOver(g);
	}

	/**
	 * 背景描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintBackGround(Graphics g) {
		System.out.println("paintBackGround");
		back.paint(g);
	}

	/**
	 * フィールド領域の描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintFieldSpace(Graphics g) {
		System.out.println("paintFieldSpace");

		getField().paint(g);

		cursor.paint(g);

		getCommand().paint(g);
	}

	/**
	 * プレイヤー情報描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintPlayerInfo(Graphics g) {
		System.out.println("paintPlayerInfo");
		PaintItem[] players = getPlayers();
		for (int i = 0; i < players.length; i++) {
			players[i].paint(g);
		}
	}

}
