package jp.ac.tokyo_ct.asteragy;

public abstract class Player {
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

	private int sp = 999;

	protected final Game game;
}
