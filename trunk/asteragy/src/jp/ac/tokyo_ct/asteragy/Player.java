package jp.ac.tokyo_ct.asteragy;

public abstract class Player {
	/**
	 * @param playerName
	 *            �v���C���[�̖��O
	 */
	public Player(Game game, String playerName) {
		this.name = playerName;
		this.game = game;
	}

	/**
	 * �s����I������iGame����Ă΂��j�B
	 * 
	 * @return �I�������s���B�܂��́A�^�[���I���Ȃ�null��Ԃ��B
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
