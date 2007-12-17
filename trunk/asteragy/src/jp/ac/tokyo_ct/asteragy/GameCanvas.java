package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18�~18
	 */
	public static final int measure = 18;

	/**
	 * �t�B�[���h�摜
	 */
	private Image fieldimage;

	/**
	 * �v���C���[���̈捂��
	 */
	private static final int playerheight = 20;

	// �v���C���[�����W
	private static final int spx = 10;

	private static final int spy = 16;

	private static final int namex = 40;

	private static final int namey = 16;

	private final Game game;

	public GameCanvas(Game g) {
		super();
		loadField();
		game = g;
//		int[][] r = { { 0, 1, 1 }, { 0, 1, 0 }, { 1, 1, 0 } };
//		Range.setRange(new Point(5, 5), r);
	}

	public void paint(Graphics g) {
		// �_�u���o�b�t�@�J�n
		g.lock();
		// �N���A
		g.clearRect(0, 0, 240, 240);
		// �Œ�w�i�`��
		BackImage.paint(g);
		// �t�B�[���h�`��
		paintField(g);
		// �v���C���[���`��
		paintPlayerInfo(g);
		// �_�u���o�b�t�@�I��
		g.unlock(false);
	}

	/**
	 * �v���C���[���`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintPlayerInfo(Graphics g) {
		// 1P
		g.setOrigin(0, this.getHeight() - playerheight);
		paintPlayerInfo(g, 1);
		// 2P
		g.setOrigin(0, 0);
		paintPlayerInfo(g, 2);
	}

	/**
	 * �v���C���[���`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 * @param playernumber
	 *            �v���C���[
	 */
	private void paintPlayerInfo(Graphics g, int playernumber) {
		Player player = null;
		if (playernumber == 1)
			player = game.getPlayer1();
		else if (playernumber == 2)
			player = game.getPlayer2();
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(player.getName(), namex, namey);
		g.drawString("" + player.getSP(), spx, spy);
	}

	/**
	 * �t�B�[���h�`��
	 * 
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	public void paintField(Graphics g) {
		Aster[][] aster = game.getField().getField();
		// ���_���W�ʒu�v�Z
		g.setOrigin((240 - aster.length * GameCanvas.measure) / 2,
				(240 - aster[0].length * GameCanvas.measure) / 2);
		// �t�B�[���h�C�A�X�e���`��
		for (int i = 0; i < aster.length; i++) {
			for (int j = 0; j < aster[0].length; j++) {
				if (aster[i][j] == null)
					continue;
				// �t�B�[���h
				g.drawImage(fieldimage, i * GameCanvas.measure, j
						* GameCanvas.measure);
				// �A�X�e��
				g.drawScaledImage(aster[i][j].getImage(), i * measure + 1, j
						* measure + 1, measure - 1, measure - 1, (measure - 1)
						* (aster[i][j].getColor() - 1), 0, measure - 1,
						measure - 1);
				// �����W
				Range.paint(g, i, j);
			}
		}
		// �J�[�\���`��
		Cursor.paintCursor(g);
		// �R�}���h�`��
		Command.paintCommand(g);
		g.setOrigin(0, 0);
	}

	/**
	 * �t�B�[���h�摜�ǂݍ���
	 */
	private void loadField() {
		// �Ǎ���C���[�W
		fieldimage = null;
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///fieldimage.jpg");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			fieldimage = m.getImage();
		} catch (Exception e) {
		}
		fieldimage = Image.createImage(measure + 1, measure + 1);
		Graphics g = fieldimage.getGraphics();
		// �Վ��}�X
		g.setColor(Graphics.getColorOfRGB(255, 243, 236));
		g.fillRect(0, 0, measure + 1, measure + 1);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.drawRect(0, 0, measure, measure);
		g.dispose();
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see com.nttdocomo.ui.Canvas#processEvent(int, int)
	 */
	public void processEvent(int type, int param) {
		if (eventProcesser != null)
			eventProcesser.processEvent(type, param);
		else
			super.processEvent(type, param);
	}

	private volatile EventProcesser eventProcesser;

	public void setEventProcesser(EventProcesser e) {
		eventProcesser = e;
	}

	/**
	 * ���݂̃C�x���g�v���Z�b�T���擾����B
	 * @return �ݒ肳��Ă���C�x���g�v���Z�b�T�B�Ȃ����null�B
	 */
	public EventProcesser getEventProcesser() {
		return eventProcesser;
	}

	/**
	 * �C�x���g�v���Z�b�T���폜����B
	 */
	public void resetEventProcesser() {
		eventProcesser = null;
	}

	/**
	 * �C�x���g�v���Z�b�T���폜����B
	 * @param e �폜����C�x���g�v���Z�b�T
	 * @return �폜������true�A���Ȃ����false;
	 * ��v������폜����B
	 */
	public boolean resetEventProcesser(EventProcesser e) {
		if (e == eventProcesser){
			eventProcesser = null;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * �I�����Ă���ʒu��\�����߁A�t�B�[���h���̎w�肵���ʒu��g�ň͂ށB
	 * 
	 * @param x
	 * @param y
	 * @param cursorType
	 *            �J�[�\���̎�
	 */
	public void drawCursor(int x, int y, int cursorType) {
		Cursor.setCursor(new Point(x, y), cursorType);
		repaint();
	}

	/**
	 * �^�[�����n�܂����Ƃ��ɌĂ΂��B
	 * 
	 * @param player
	 */
	public void onTurnStart(Player player) {
	}

	public void drawCommandSelection(int cmd, Point pt) {
		Command.setCommand(cmd, pt);
		repaint();
	}
}
