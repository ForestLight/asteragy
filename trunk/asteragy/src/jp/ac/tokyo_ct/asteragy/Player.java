package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public abstract class Player implements PaintItem {
	/**
	 * �v���C���[���̈捂��
	 */
	public static final int playerheight = 20;

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
	public abstract Action getAction();

	public final String getName() {
		return name;
	}

	public final int getAP() {
		return ap;
	}

	public final void addAP(int n) {
		ap += n;
		System.out.println("AP+" + n);
		repaint();
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
	public void paint(Graphics g) {
		if (this.equals(game.getPlayer1())) {
			g.setOrigin(0, game.getCanvas().getHeight() - playerheight);
		} else {
			g.setOrigin(0, 0);
		}
		if (game.getCurrentPlayer() == this) {
			//������W
		}
		g.setColor(Graphics.getColorOfRGB(228, 196, 255));
		g.drawString("" + ap, apx + 1, apy + 1);
		g.setColor(Graphics.getColorOfRGB(196, 64, 255));
		g.drawString("" + ap, apx, apy);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawString(name, namex, namey);
	}

	public void repaint() {
		if (game.isInit())
			return;
		final CanvasControl canvas = game.getCanvas();
		Graphics g = canvas.getScreen().getGraphics();
		int player = game.getPlayerIndex(this) + 1;
		canvas.getBackImage().paintPlayerBack(g, player);
		paint(g);
		g.dispose();
	}

	public Image getTurnOnBack() {
		if (turnonback == null)
			loadTurnOnBack();
		return turnonback;
	}

	private void loadTurnOnBack() {
		final CanvasControl canvas = game.getCanvas();
		turnonback = Image.createImage(canvas.getWidth(),
				canvas.getHeight() / 2);
	}

	private static Image turnonback;

	public String toString() {
		return name;
	}

	public boolean isPlayer2() {
		return this == game.getPlayer2();
	}

}
