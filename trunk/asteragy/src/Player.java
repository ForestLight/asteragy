
public abstract class Player {
	/**
	 * @param playerName �v���C���[�̖��O
	 */
	public Player(String playerName) {
		this.name = playerName;
	}
	
	public Action getAction() {
		return null;
	}
	
	private String name;
}
