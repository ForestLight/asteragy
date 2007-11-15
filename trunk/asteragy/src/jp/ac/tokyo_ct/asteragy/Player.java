package jp.ac.tokyo_ct.asteragy;

public abstract class Player {
	/**
	 * @param playerName プレイヤーの名前
	 */
	public Player(String playerName) {
		this.name = playerName;
	}
	
	/**
	 * 行動を選択する（Gameから呼ばれる）。
	 * @return 選択した行動。または、ターン終了ならnullを返す。
	 */
	public Action getAction() {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	private String name;
}
