package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.Image;

public abstract class Player implements PaintItem {
	/**
	 * @param playerName
	 *            プレイヤーの名前
	 */
	public Player(Game game, String playerName) {
		this.name = playerName;
		this.game = game;
	}

	/**
	 * 行動を選択する（Gameから呼ばれる）。
	 * 
	 * @return 選択した行動。または、ターン終了ならnullを返す。
	 */
	public Action getAction() {
		return null;
	}

	public String getName() {
		return name;
	}

	public int getSP() {
		return sp;
	}

	public void addSP(int n) {
		sp += n;
		System.out.println("SP+" + n);
		repaint();
	}

	protected final String name;

	private int sp;

	protected final Game game;

	// プレイヤー情報座標
	private static final int spx = 10;

	private static final int spy = 16;

	private static final int namex = 40;

	private static final int namey = 16;

	/**
	 * プレイヤー情報描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 * @param playernumber
	 *            プレイヤー
	 */
	public void paint(Graphics g) {
		if (this.equals(game.getPlayer1())) {
			g.setOrigin(0, game.getCanvas().getHeight()
					- GameCanvas.playerheight);
		} else {
			g.setOrigin(0, 0);
		}
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(name, namex, namey);
		g.drawString("" + sp, spx, spy);

		if (game.getCurrentPlayer() == this) {
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.drawRect(namex - 1, namey - 11, 25, 13);
		}
	}

	public void repaint() {
		Graphics g = game.getCanvas().getGraphics();
		g.lock();
		int player = 1;
		if (this.equals(game.getPlayer2())) {
			player = 2;
		}
		game.getCanvas().getBackImage().paintPlayerBakc(g, player);
		paint(g);
		g.unlock(false);
	}

	public Image getTurnOnBack() {
		if (turnonback == null)
			loadTurnOnBack();
		return turnonback;
	}

	private void loadTurnOnBack() {
		final CanvasControl canvas = game.getCanvas();
		turnonback = Image.createImage(canvas.getWidth(),
				canvas.getHeight() / 2);
	}

	private static Image turnonback;

	public String toString() {
		return name;
	}

}
