package jp.ac.tokyo_ct.asteragy;

abstract class Action {

	private Player player;

	/**
	 * Action�̐���
	 * 
	 * @param player
	 *            ����Action���N�������v���C���[
	 */
	public Action(Player player) {
		super();
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
