package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18×18
	 */
	private final int measure = 18;

	/**
	 * アステルの種類
	 */
	private final int asterkind = 11;

	/**
	 * プレイヤー情報領域高さ
	 */
	private final int playerheight = 20;

	// プレイヤー情報座標
	private final int spx = 10;

	private final int spy = 16;

	private final int namex = 40;

	private final int namey = 16;

	/**
	 * 固定背景画像
	 */
	private Image background;

	/**
	 * フィールド画像
	 */
	private Image fieldimage;

	/**
	 * カーソル
	 */
	private int cursor;

	private int cursorx;

	private int cursory;

	/**
	 * 各種アステル画像
	 */
	private Image[] asterimage;

	private final Game game;

	public GameCanvas(Game g) {
		super();
		createBackGround();
		loadAsterImage();
		loadField();
		game = g;
	}

	public void paint(Graphics g) {
		// ダブルバッファ開始
		g.lock();
		// クリア
		g.clearRect(0, 0, 240, 240);
		// 固定背景描画
		g.drawImage(background, 0, 0);
		// フィールド描画
		paintField(g);
		// プレイヤー情報描画
		paintPlayerInfo(g);
		// ダブルバッファ終了
		g.unlock(false);
	}

	/**
	 * カーソル描画
	 *
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintCursor(Graphics g) {
		switch (cursor) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(cursorx * measure, cursory * measure, measure, measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

	/**
	 * フィールド描画
	 *
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintField(Graphics g) {

		Aster[][] aster = game.getField().getField();
		// 原点座標位置計算
		g.setOrigin((this.getHeight() - aster.length * measure) / 2, (this
				.getWidth() - aster[0].length * measure) / 2);
		// フィールド，アステル描画
		for (int i = 0; i < aster.length; i++) {
			for (int j = 0; j < aster[0].length; j++) {
				if (aster[i][j] == null)
					continue;
				// フィールド
				g.drawImage(fieldimage, i * measure, j * measure);
				// アステル
				Aster a = aster[i][j];
				g.drawScaledImage(asterimage[a.getNumber()], i * measure
						+ 1, j * measure + 1, measure - 1, measure - 1,
						(measure - 1) * (a.getColor() - 1), 0, measure - 1,
						measure - 1);
			}
		}
		paintCursor(g);
		g.setOrigin(0, 0);
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

	/**
	 * アステル画像読み込み
	 *
	 */
	private void loadAsterImage() {
		// 読込先イメージ
		asterimage = new Image[asterkind];
		for (int i = 0; i < asterimage.length; i++) {
			try {
				// リソースから読み込み
				MediaImage m = MediaManager.getImage("resource:///aster_" + i
						+ ".gif");
				// メディアの使用開始
				m.use();
				// 読み込み
				asterimage[i] = m.getImage();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 固定背景作成
	 *
	 */
	private void createBackGround() {
		// 背景画像作成
		background = Image.createImage(this.getWidth(), this.getHeight());
		// グラフィクス作成
		Graphics g = background.getGraphics();
		// 背景描画
		paintBackGround(g);
		// グラフィクス廃棄
		g.dispose();
	}

	/**
	 * 背景描画
	 *
	 * @param g
	 *            描画先グラフィクス
	 */
	private void paintBackGround(Graphics g) {
		// 読込先イメージ
		Image back = null;
		try {
			// 背景画像リソースから読み込み
			MediaImage m = MediaManager.getImage("resource:///back.jpg");
			// メディアの使用開始
			m.use();
			// 読み込み
			back = m.getImage();
		} catch (Exception e) {
		}
		// 描画
		if (back != null)
			g.drawImage(back, 0, 0);
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

	public EventProcesser getEventProcesser() {
		return eventProcesser;
	}

	public void resetEventProcesser() {
		eventProcesser = null;
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
		cursorx = x;
		cursory = y;
		cursor = cursorType;
		this.repaint();
	}

	/**
	 * ターンが始まったときに呼ばれる。
	 * @param player
	 */
	public void onTurnStart(Player player) {
	}

	public static final int CURSOR_CLEAR = 0; //カーソルの消去
	public static final int CURSOR_1 = 1;
	
	public void drawCommandSelection(int cmd, Point pt) {
	}
}
