package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18×18
	 */
	public static final int measure = 18;

	/**
	 * フィールド画像
	 */
	private Image fieldimage;

	/**
	 * プレイヤー情報領域高さ
	 */
	private static final int playerheight = 20;

	// プレイヤー情報座標
	private static final int spx = 10;

	private static final int spy = 16;

	private static final int namex = 40;

	private static final int namey = 16;

	private final Game game;

	public GameCanvas(Game g) {
		super();
		loadField();
		game = g;
//		int[][] r = { { 0, 1, 1 }, { 0, 1, 0 }, { 1, 1, 0 } };
//		Range.setRange(new Point(5, 5), r);
	}

	public void paint(Graphics g) {
		// ダブルバッファ開始
		g.lock();
		// クリア
		g.clearRect(0, 0, 240, 240);
		// 固定背景描画
		BackImage.paint(g);
		// フィールド描画
		paintField(g);
		// プレイヤー情報描画
		paintPlayerInfo(g);
		// ダブルバッファ終了
		g.unlock(false);
	}

	/**
	 * プレイヤー情報描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintPlayerInfo(Graphics g) {
		// 1P
		g.setOrigin(0, this.getHeight() - playerheight);
		paintPlayerInfo(g, 1);
		// 2P
		g.setOrigin(0, 0);
		paintPlayerInfo(g, 2);
	}

	/**
	 * プレイヤー情報描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 * @param playernumber
	 *            プレイヤー
	 */
	private void paintPlayerInfo(Graphics g, int playernumber) {
		Player player = null;
		if (playernumber == 1)
			player = game.getPlayer1();
		else if (playernumber == 2)
			player = game.getPlayer2();
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(player.getName(), namex, namey);
		g.drawString("" + player.getSP(), spx, spy);
	}

	/**
	 * フィールド描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 */
	public void paintField(Graphics g) {
		Aster[][] aster = game.getField().getField();
		// 原点座標位置計算
		g.setOrigin((240 - aster.length * GameCanvas.measure) / 2,
				(240 - aster[0].length * GameCanvas.measure) / 2);
		// フィールド，アステル描画
		for (int i = 0; i < aster.length; i++) {
			for (int j = 0; j < aster[0].length; j++) {
				if (aster[i][j] == null)
					continue;
				// フィールド
				g.drawImage(fieldimage, i * GameCanvas.measure, j
						* GameCanvas.measure);
				// アステル
				g.drawScaledImage(aster[i][j].getImage(), i * measure + 1, j
						* measure + 1, measure - 1, measure - 1, (measure - 1)
						* (aster[i][j].getColor() - 1), 0, measure - 1,
						measure - 1);
				// レンジ
				Range.paint(g, i, j);
			}
		}
		// カーソル描画
		Cursor.paintCursor(g);
		// コマンド描画
		Command.paintCommand(g);
		g.setOrigin(0, 0);
	}

	/**
	 * フィールド画像読み込み
	 */
	private void loadField() {
		// 読込先イメージ
		fieldimage = null;
		try {
			// リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///fieldimage.jpg");
			// メディアの使用開始
			m.use();
			// 読み込み
			fieldimage = m.getImage();
		} catch (Exception e) {
		}
		fieldimage = Image.createImage(measure + 1, measure + 1);
		Graphics g = fieldimage.getGraphics();
		// 臨時マス
		g.setColor(Graphics.getColorOfRGB(255, 243, 236));
		g.fillRect(0, 0, measure + 1, measure + 1);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawRect(0, 0, measure, measure);
		g.dispose();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see com.nttdocomo.ui.Canvas#processEvent(int, int)
	 */
	public void processEvent(int type, int param) {
		if (eventProcesser != null)
			eventProcesser.processEvent(type, param);
		else
			super.processEvent(type, param);
	}

	private volatile EventProcesser eventProcesser;

	public void setEventProcesser(EventProcesser e) {
		eventProcesser = e;
	}

	/**
	 * 現在のイベントプロセッサを取得する。
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
	 * @param e 削除するイベントプロセッサ
	 * @return 削除したらtrue、しなければfalse;
	 * 一致したら削除する。
	 */
	public boolean resetEventProcesser(EventProcesser e) {
		if (e == eventProcesser){
			eventProcesser = null;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 選択している位置を表すため、フィールド内の指定した位置を枠で囲む。
	 * 
	 * @param x
	 * @param y
	 * @param cursorType
	 *            カーソルの種
	 */
	public void drawCursor(int x, int y, int cursorType) {
		Cursor.setCursor(new Point(x, y), cursorType);
		repaint();
	}

	/**
	 * ターンが始まったときに呼ばれる。
	 * 
	 * @param player
	 */
	public void onTurnStart(Player player) {
	}

	public void drawCommandSelection(int cmd, Point pt) {
		Command.setCommand(cmd, pt);
		repaint();
	}
}
