package jp.ac.tokyo_ct.asteragy;

public abstract class Player {
	/**
	 * @param playerName �v���C���[�̖��O
	 */
	public Player(String playerName) {
		this.name = playerName;
	}
	
	/**
	 * �s����I������iGame����Ă΂��j�B
	 * @return �I�������s���B�܂��́A�^�[���I���Ȃ�null��Ԃ��B
	 */
	public Action getAction() {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	private String name;
}
