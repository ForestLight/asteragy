
public abstract class Player {
	/**
	 * @param playerName プレイヤーの名前
	 */
	public Player(String playerName) {
		this.name = playerName;
	}
	
	public Action getAction() {
		return null;
	}
	
	private String name;
}
