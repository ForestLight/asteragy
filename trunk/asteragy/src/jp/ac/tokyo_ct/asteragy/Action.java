package jp.ac.tokyo_ct.asteragy;

import java.io.OutputStream;
import java.io.PrintStream;

final class Action {

	private Player player;

	/**
	 * Action�̐���
	 * 
	 * @param player
	 *            ����Action���N�������v���C���[
	 */
	public Action(Player player) {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	Aster aster;
	int commandType;
	int[] args;
	
	public void printToStream(OutputStream os) {
		PrintStream ps = new PrintStream(os);
		ps.print(Main.game.getPlayerIndex(player));
		ps.print(' ');
		ps.print(commandType);
		for (int i = 0; i < args.length; i++) {
			ps.print(' ');
			ps.print(args[i]);
		}
	}
}
