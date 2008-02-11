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
	 * �J�[�\�����擾
	 * 
	 * @return �J�[�\��
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
	 * �������擾
	 * 
	 * @return ����
	 */
	public int getHeight() {
		return canvas.getHeight();
	}

	public int fieldHeight() {
		return game.getField().getY() * GameCanvas.measure;
	}

	/**
	 * �����擾
	 * 
	 * @return ��
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
	 * ���݂̃C�x���g�v���Z�b�T���擾����B
	 * 
	 * @return �ݒ肳��Ă���C�x���g�v���Z�b�T�B�Ȃ����null�B
	 */
	public EventProcesser getEventProcesser() {
		return eventProcesser;
	}

	/**
	 * �C�x���g�v���Z�b�T���폜����B
	 */
	public void resetEventProcesser() {
		eventProcesser = null;
	}

	/**
	 * �C�x���g�v���Z�b�T���폜����B
	 * 
	 * @param e
	 *            �폜����C�x���g�v���Z�b�T
	 * @return �폜������true�A���Ȃ����false; ��v������폜����B
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		System.out.println("onTurnStart");
		game.getPlayer1().repaint();
		game.getPlayer2().repaint();

		EffectTurnon turnon = new EffectTurnon(this, player);
		turnon.start();

	}

	public boolean isInit() {
		return game.isInit();
	}

	public void paintNowloading(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		paintString(g);
	}

	public void repaint(int x, int y, int width, int height) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		canvas.repaint(x, y, width, height);
		
	}
}
