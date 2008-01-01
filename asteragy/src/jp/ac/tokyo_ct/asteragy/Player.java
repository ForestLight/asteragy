package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Graphics;

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
	
	public void addSP(int n){
		sp += n;
		System.out.println("SP+"+n+"");
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
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(name, namex, namey);
		g.drawString("" + sp, spx, spy);

		if (game.getCurrentPlayer() == this) {
			g.setColor(Graphics.getColorOfName(Graphics.RED));
			g.drawRect(namex - 1, namey - 11, 25, 13);
		}
	}
}
