package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Kazuto
 * 
 */
public final class CanvasControl extends Canvas {

	static final int measure = 18;

	public static final int f = 40;

	static final Image backgroundImage = createBackground();

	final Game game;

	final Cursor cursor = new Cursor(this);

	final CommonCommand commonCommand = new CommonCommand(this);

	final SunCommand sunCommand = new SunCommand(this);

	final Range range = new Range(this);

	final EffectAsterDisappearControl disappearControl = new EffectAsterDisappearControl();

	private final PreKeyProcesser pre;

	EffectTurnon turnOn = new EffectTurnon(this);

	private int topmargin;

	private int leftmargin;
	
	private boolean paintFlag;

	public CanvasControl(Game game) {
		this.game = game;
		pre = new PreKeyProcesser(this);
		pre.initKey(this);
		Display.setCurrent(this);
		setBackground(Graphics.getColorOfName(Graphics.BLACK));
		paintFlag = true;
		repaint();
	}

	Canvas getCanvas() {
		return this;
	}

	private void setFieldMargin() {
		final Field field = game.getField();
		topmargin = (getHeight() - field.Y * measure) / 2;
		leftmargin = (getWidth() - field.X * measure) / 2;
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
	
	public void setPaintFlag(boolean b){
		paintFlag = b;
	}

	public EffectAsterDisappearControl getDisappearControl() {
		return disappearControl;
	}

	private Command getCommand() {
		if (sunCommand.visible()) {
			System.out.println("return suncommand");
			return sunCommand;
		} else {
			System.out.println("return commoncommand");
			return commonCommand;
		}
	}

	private volatile EventProcesser eventProcesser;

	void setEventProcesser(EventProcesser e) {
		eventProcesser = e;
	}

	/**
	 * イベントプロセッサを削除する。
	 */
	void resetEventProcesser() {
		eventProcesser = null;
	}

	public void onTurnStart(Player player) {
		turnOn.player = player;
		System.out.println("onTurnStart");
		repaint();
		paintEffect(turnOn);
	}

	private void paintNowloading(Graphics g) {
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawRect(0, getHeight() / 4, getWidth(), getHeight() * 3 / 4);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawString("Now Loading...", 78, 126);
	}

	public void gameOver(Player winner) {
		Effect effect = new EffectGameOver(this, winner);
		paintEffect(effect);
	}

	private PaintString spaint;

	public void paintString(String string, boolean visible) {
		if (visible)
			spaint = new PaintString(this, string);
		else
			spaint = null;
		repaint();
	}

	private void paintString(Graphics g) {
		if (spaint != null)
			spaint.paint(g);
	}

	public void paint(Graphics g) {
		if(paintFlag){
			g.lock();
			if (game.initializing) {
				paintNowloading(g);
			} else {
				System.out.println("paintFlag "+ paintFlag);
				g.drawImage(backgroundImage, 0, 0);
				paintPlayerInfo(g);
				paintFieldSpace(g);
	
				paintString(g);
				System.out.println("end paint");
			}
			g.unlock(true); // trueにしたのは安全措置。
		}else{
			System.out.println("paintFlag false");
		}
	}

	/**
	 * フィールド領域の描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintFieldSpace(Graphics g) {
		System.out.println("paintFieldSpace");
		game.getField().paint(g);
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
		Player[] player = game.player;
		player[0].paint(g);
		player[1].paint(g);
	}

	/**
	 * 固定背景作成
	 * 
	 */
	private static Image createBackground() {
		final Image base = Game.loadImage("back");
		if (base == null)
			return null;
		// 背景画像作成
		Image background = Image.createImage(base.getWidth(), base.getHeight());
		// グラフィクス作成
		Graphics g = background.getGraphics();
		// 背景描画
		g.drawImage(base, 0, 0);
		// グラフィクス廃棄
		g.dispose();
		return background;
	}

	static void paintAsterBack(Graphics g, Point pt) {
		final int m = CanvasControl.measure;
		CanvasControl canvas = Main.game.getCanvas();
		g.setOrigin(0, 0);
		int x = canvas.getLeftMargin() + pt.x * m;
		int y = canvas.getTopMargin() + pt.y * m;
		g.drawImage(backgroundImage, x, y, x, y, m + 1, m + 1);
	}

	public void processEvent(int type, int param) {
		type = pre.processEvent(type, param);
		if (type == Display.KEY_PRESSED_EVENT && param == Display.KEY_9)
			Effect.setEffect();
		if (eventProcesser != null)
			eventProcesser.processEvent(type, param);
	}

	Image getScreen() {
		Image screen = Image.createImage(getWidth(), getHeight());
		Graphics g = screen.getGraphics();
		paint(g);
		g.dispose();
		return screen;
	}

	Image getScreen(Point location, Point size) {
		Image screen = Image.createImage(size.x, size.y);
		Graphics g = screen.getGraphics();
		g.drawImage(getScreen(), 0, 0, location.x, location.y,
				size.x, size.y);
		g.dispose();
		return screen;
	}

	void paintEffect(Effect effect) {
		if (!Effect.isEffect)
			return;
		Graphics g = getGraphics();
		effect.start(g);
		g.setOrigin(0, 0);
		paint(g);
		g.dispose();
	}
	

}
