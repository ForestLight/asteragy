package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

abstract class Player {
	/**
	 * プレイヤー情報領域高さ
	 */
	static final int playerheight = 20;

	/**
	 * @param playerName
	 *            プレイヤーの名前
	 */
	Player(Game game, String playerName) {
		this.name = playerName;
		this.game = game;
	}

	/**
	 * 行動を選択する（Gameから呼ばれる）。
	 * 
	 * @return 選択した行動。または、ターン終了ならnullを返す。
	 */
	abstract Action getAction();

	final int getAP() {
		return ap;
	}

	final void addAP(int n) {
		ap += n;
		System.out.println("AP+" + n);
	}

	protected final String name;

	private int ap;

	protected final Game game;

	// プレイヤー情報座標
	private static final int apx = 5;

	private static final int apy = (playerheight + Font.getDefaultFont()
			.getAscent()) / 2;

	private static final int namex = 42;

	private static final int namey = (playerheight + Font.getDefaultFont()
			.getAscent()) / 2;

	/**
	 * プレイヤー情報描画
	 * 
	 * @param g
	 *            描画先グラフィクス
	 * @param playernumber
	 *            プレイヤー
	 */
	final void paint(Graphics g) {
//		System.out.println("Player.paint");
		if (this.equals(game.player[0])) {
			g.setOrigin(0, game.getCanvas().getHeight() - playerheight);
		} else {
			g.setOrigin(0, 0);
		}
		if (game.getCurrentPlayer() == this) {
			// ここ募集
		}
		g.setColor(Graphics.getColorOfRGB(228, 196, 255));
		g.drawString("" + ap, apx + 1, apy + 1);
		g.setColor(Graphics.getColorOfRGB(196, 64, 255));
		g.drawString("" + ap, apx, apy);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawString(name, namex, namey);
	}

	static final Image turnOnBack = Game.loadImage("turnon_effect");

	public String toString() {
		return name;
	}
}
