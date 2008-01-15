package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class CanvasControl {

	private final Game game;

	private final GameCanvas canvas;

	private final BackImage back;

	private final Cursor cursor;

	private final CommonCommand commoncommand;

	private final SunCommand suncommand;

	private int topmargin;

	private int leftmargin;

	private boolean lockrepaint;

	public CanvasControl(Game game) {
		lockrepaint = false;
		this.game = game;
		canvas = new GameCanvas(this);
		back = new BackImage(this);
		cursor = new Cursor(this);
		commoncommand = new CommonCommand(this);
		suncommand = new SunCommand(this);
		Display.setCurrent(canvas);
	}

	private void setFieldMargin() {
		topmargin = (getHeight() - game.getField().getY() * GameCanvas.measure) / 2;
		leftmargin = (getWidth() - game.getField().getX() * GameCanvas.measure) / 2;
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

	public PaintItem getField() {
		return game.getField();
	}

	public PaintItem getPlayer1() {
		return game.getPlayer1();
	}

	public PaintItem getPalyer2() {
		return game.getPlayer2();
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

	/**
	 * 幅を取得
	 * 
	 * @return 幅
	 */
	public int getWidth() {
		return canvas.getWidth();
	}

	/**
	 * 再描画
	 * 
	 */
	public void repaint() {
		if (lockrepaint)
			return;
		lockrepaint = true;
		lockrepaint = false;
		synchronized(canvas){
			canvas.repaint();
		}
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

	}

}
