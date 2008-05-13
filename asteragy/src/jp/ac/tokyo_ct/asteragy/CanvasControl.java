package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Kazuto
 * 
 */
final class CanvasControl extends Canvas {

	static final int measure = 18;

	static final int f = 40;

	static final Image backgroundImage = Game.loadImage("back");

	final Game game;

	final Cursor cursor = new Cursor(this);

	final CommonCommand commonCommand = new CommonCommand(this);

	final SunCommand sunCommand = new SunCommand(this);

	final Range range = new Range();

	final Field field;

	final EffectAsterDisappearControl disappearControl = new EffectAsterDisappearControl();

	private final PreKeyProcesser pre;

	EffectTurnon turnOn = new EffectTurnon(this);

	private int topmargin;

	private int leftmargin;

	private boolean paintFlag;

	CanvasControl(Game game) {
		this.game = game;
		this.field = game.getField();
		pre = new PreKeyProcesser(this);
		pre.initKey(this);
		Display.setCurrent(this);
		setBackground(Graphics.getColorOfName(Graphics.BLACK));
		paintFlag = true;
		repaint();
	}

	private void setFieldMargin() {
		topmargin = (Display.getHeight() - field.Y * measure) / 2;
		leftmargin = (Display.getWidth() - field.X * measure) / 2;
	}

	int getTopMargin() {
		if (topmargin <= 0)
			setFieldMargin();
		return topmargin;
	}

	int getLeftMargin() {
		if (leftmargin <= 0)
			setFieldMargin();
		return leftmargin;
	}

	void setPaintFlag(boolean b) {
		paintFlag = b;
	}

	volatile EventProcesser eventProcesser;

	void onTurnStart(Player player) {
		turnOn.player = player;
		Game.println("onTurnStart");
		repaint();
		paintEffect(turnOn);
	}

	private void paintNowloading(Graphics g) {
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawRect(0, getHeight() / 4, getWidth(), getHeight() * 3 / 4);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("Now Loading...", 78, 126);
	}

	void gameOver(Player winner) {
		paintEffect(new EffectGameOver(this, winner));
	}

	private PaintString spaint;

	void paintString(String string, boolean visible) {
		spaint = visible ? new PaintString(this, string) : null;
		repaint();
	}

	public void paint(Graphics g) {
		if (paintFlag) {
			g.lock();
			if (game.initializing) {
				paintNowloading(g);
			} else {
				g.drawImage(backgroundImage, 0, 0);
				game.player[0].paint(g);
				game.player[1].paint(g);
				game.getField().paint(g);
				cursor.paint(g);
				if (sunCommand.visible()) {
					sunCommand.paint(g);
				} else {
					commonCommand.paint(g);
				}
				if (spaint != null)
					spaint.paint(g);
			}
			g.unlock(true); // true‚É‚µ‚½‚Ì‚ÍˆÀ‘S‘[’uB
		} else {
			// Game.println("paintFlag false");
		}
	}

	void paintAsterBack(Graphics g, Point pt) {
		final int m = CanvasControl.measure;
		CanvasControl canvas = game.getCanvas();
		g.setOrigin(0, 0);
		int x = canvas.getLeftMargin() + pt.x * m;
		int y = canvas.getTopMargin() + pt.y * m;
		g.drawImage(backgroundImage, x, y, x, y, m + 1, m + 1);
	}

	public void processEvent(int type, int param) {
		type = pre.processEvent(type, param);
		/*
		 * if (type == Display.KEY_PRESSED_EVENT && param == Display.KEY_9)
		 * Effect.setEffect();
		 */
		// if (type == Display.KEY_PRESSED_EVENT && param == Display.KEY_SOFT1 )
		// game.titleBack();
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
		g.drawImage(getScreen(), 0, 0, location.x, location.y, size.x, size.y);
		g.dispose();
		return screen;
	}

	void paintEffect(Effect effect) {
		if (!Effect.isEffect)
			return;
		Graphics g = getGraphics();
		effect.start(g, this);
		g.setOrigin(0, 0);
		paint(g);
		g.dispose();
	}

}
