package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class GameCanvas extends com.nttdocomo.ui.Canvas {

	/**
	 * 18�~18
	 */
	private final int measure = 18;

	/**
	 * �A�X�e���̎��
	 */
	private final int asterkind = 11;

	/**
	 * �v���C���[���̈捂��
	 */
	private final int playerheight = 20;

	// �v���C���[�����W
	private final int spx = 10;

	private final int spy = 16;

	private final int namex = 40;

	private final int namey = 16;

	/**
	 * �Œ�w�i�摜
	 */
	private Image background;

	/**
	 * �t�B�[���h�摜
	 */
	private Image fieldimage;

	/**
	 * �J�[�\��
	 */
	private int cursor;

	private int cursorx;

	private int cursory;

	/**
	 * �e��A�X�e���摜
	 */
	private Image[] asterimage;

	private final Game game;

	public GameCanvas(Game g) {
		super();
		createBackGround();
		loadAsterImage();
		loadField();
		game = g;
	}

	public void paint(Graphics g) {
		// �_�u���o�b�t�@�J�n
		g.lock();
		// �N���A
		g.clearRect(0, 0, 240, 240);
		// �Œ�w�i�`��
		g.drawImage(background, 0, 0);
		// �t�B�[���h�`��
		paintField(g);
		// �v���C���[���`��
		paintPlayerInfo(g);
		// �_�u���o�b�t�@�I��
		g.unlock(false);
	}

	/**
	 * �J�[�\���`��
	 *
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintCursor(Graphics g) {
		switch (cursor) {
		case CURSOR_CLEAR:
			break;
		case CURSOR_1:
			g.setColor(Graphics.getColorOfRGB(255, 0, 0));
			g.drawRect(cursorx * measure, cursory * measure, measure, measure);
			g.setColor(Graphics.getColorOfRGB(0, 0, 0));
			break;
		}
	}

	/**
	 * �t�B�[���h�`��
	 *
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintField(Graphics g) {

		Aster[][] aster = game.getField().getField();
		// ���_���W�ʒu�v�Z
		g.setOrigin((this.getHeight() - aster.length * measure) / 2, (this
				.getWidth() - aster[0].length * measure) / 2);
		// �t�B�[���h�C�A�X�e���`��
		for (int i = 0; i < aster.length; i++) {
			for (int j = 0; j < aster[0].length; j++) {
				if (aster[i][j] == null)
					continue;
				// �t�B�[���h
				g.drawImage(fieldimage, i * measure, j * measure);
				// �A�X�e��
				Aster a = aster[i][j];
				g.drawScaledImage(asterimage[a.getNumber()], i * measure
						+ 1, j * measure + 1, measure - 1, measure - 1,
						(measure - 1) * (a.getColor() - 1), 0, measure - 1,
						measure - 1);
			}
		}
		paintCursor(g);
		g.setOrigin(0, 0);
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

	/**
	 * �A�X�e���摜�ǂݍ���
	 *
	 */
	private void loadAsterImage() {
		// �Ǎ���C���[�W
		asterimage = new Image[asterkind];
		for (int i = 0; i < asterimage.length; i++) {
			try {
				// ���\�[�X����ǂݍ���
				MediaImage m = MediaManager.getImage("resource:///aster_" + i
						+ ".gif");
				// ���f�B�A�̎g�p�J�n
				m.use();
				// �ǂݍ���
				asterimage[i] = m.getImage();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * �Œ�w�i�쐬
	 *
	 */
	private void createBackGround() {
		// �w�i�摜�쐬
		background = Image.createImage(this.getWidth(), this.getHeight());
		// �O���t�B�N�X�쐬
		Graphics g = background.getGraphics();
		// �w�i�`��
		paintBackGround(g);
		// �O���t�B�N�X�p��
		g.dispose();
	}

	/**
	 * �w�i�`��
	 *
	 * @param g
	 *            �`���O���t�B�N�X
	 */
	private void paintBackGround(Graphics g) {
		// �Ǎ���C���[�W
		Image back = null;
		try {
			// �w�i�摜���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///back.jpg");
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			back = m.getImage();
		} catch (Exception e) {
		}
		// �`��
		if (back != null)
			g.drawImage(back, 0, 0);
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

	public EventProcesser getEventProcesser() {
		return eventProcesser;
	}

	public void resetEventProcesser() {
		eventProcesser = null;
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
		cursorx = x;
		cursory = y;
		cursor = cursorType;
		this.repaint();
	}

	/**
	 * �^�[�����n�܂����Ƃ��ɌĂ΂��B
	 * @param player
	 */
	public void onTurnStart(Player player) {
	}

	public static final int CURSOR_CLEAR = 0; //�J�[�\���̏���
	public static final int CURSOR_1 = 1;
	
	public void drawCommandSelection(int cmd, Point pt) {
	}
}
