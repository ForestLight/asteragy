package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

abstract class Player {
	/**
	 * �v���C���[���̈捂��
	 */
	static final int playerheight = 20;

	/**
	 * @param playerName
	 *            �v���C���[�̖��O
	 */
	Player(Game game, String playerName) {
		this.name = playerName;
		this.game = game;
	}

	/**
	 * �s����I������iGame����Ă΂��j�B
	 * 
	 * @return �I�������s���B�܂��́A�^�[���I���Ȃ�null��Ԃ��B
	 */
	abstract Action getAction();

	final int getAP() {
		return ap;
	}

	final void addAP(int n) {
		ap += n;
		System.out.println("AP+" + n);
	}

	protected final String name;

	private int ap;

	protected final Game game;

	// �v���C���[�����W
	private static final int apx = 5;

	private static final int apy = (playerheight + Font.getDefaultFont()
			.getAscent()) / 2;

	private static final int namex = 42;

	private static final int namey = (playerheight + Font.getDefaultFont()
			.getAscent()) / 2;

	/**
	 * �v���C���[���`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 * @param playernumber
	 *            �v���C���[
	 */
	final void paint(Graphics g) {
//		System.out.println("Player.paint");
		if (this.equals(game.player[0])) {
			g.setOrigin(0, game.getCanvas().getHeight() - playerheight);
		} else {
			g.setOrigin(0, 0);
		}
		if (game.getCurrentPlayer() == this) {
			// ������W
		}
		g.setColor(Graphics.getColorOfRGB(228, 196, 255));
		g.drawString("" + ap, apx + 1, apy + 1);
		g.setColor(Graphics.getColorOfRGB(196, 64, 255));
		g.drawString("" + ap, apx, apy);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawString(name, namex, namey);
	}

	static final Image turnOnBack = Game.loadImage("turnon_effect");

	public String toString() {
		return name;
	}
}
