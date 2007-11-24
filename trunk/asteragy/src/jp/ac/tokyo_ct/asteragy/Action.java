package jp.ac.tokyo_ct.asteragy;

abstract class Action {

	private Player player;

	/**
	 * Actionの生成
	 * 
	 * @param player
	 *            このActionを起こしたプレイヤー
	 */
	public Action(Player player) {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
