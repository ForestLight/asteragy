package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18×18
	 */
	public static final int measure = 18;
	
	private final CanvasControl canvas;

	public GameCanvas(CanvasControl canvas) {
		super();
		this.canvas = canvas;
	}

	public void paint(Graphics g) {
		System.out.println("repaint()");
		// ダブルバッファ開始
		g.lock();

		// Now Loading...
		if (canvas.isInit()) {
			canvas.paintNowloading(g);
			g.unlock(false);
			return;
		}
		canvas.getScreen().paint(g);

		// ダブルバッファ終了
		g.unlock(false);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.nttdocomo.ui.Canvas#processEvent(int, int)
	 */
	public void processEvent(int type, int param) {
		final EventProcesser eventProcesser = canvas.getEventProcesser();
		if (type == Display.KEY_PRESSED_EVENT && param == Display.KEY_9)
			Effect.setEffect();
		if (eventProcesser != null)
			eventProcesser.processEvent(type, param);
		else
			super.processEvent(type, param);
	}

}
