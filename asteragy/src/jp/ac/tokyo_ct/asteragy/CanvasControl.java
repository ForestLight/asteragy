package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class CanvasControl {

	private final Game game;

	private final GameCanvas canvas;

	private final BackImage back;

	private final Cursor cursor;

	private final CommonCommand commoncommand;

	private final SunCommand suncommand;

	private int topmargin;

	private int leftmargin;

	private boolean lockrepaint;

	public CanvasControl(Game game) {
		lockrepaint = false;
		this.game = game;
		canvas = new GameCanvas(this);
		back = new BackImage(this);
		cursor = new Cursor(this);
		commoncommand = new CommonCommand(this);
		suncommand = new SunCommand(this);
		Display.setCurrent(canvas);
	}

	private void setFieldMargin() {
		topmargin = (getHeight() - game.getField().getY() * GameCanvas.measure) / 2;
		leftmargin = (getWidth() - game.getField().getX() * GameCanvas.measure) / 2;
	}

	public int getTopMargin() {
		if (topmargin <= 0)
			setFieldMargin();
		return topmargin;
	}

	public int getLeftMargin() {
		if (leftmargin <= 0)
			setFieldMargin();
		return leftmargin;
	}

	public BackImage getBackImage() {
		return back;
	}

	public PaintItem getField() {
		return game.getField();
	}

	public PaintItem getPlayer1() {
		return game.getPlayer1();
	}

	public PaintItem getPalyer2() {
		return game.getPlayer2();
	}

	/**
	 * �J�[�\�����擾
	 * 
	 * @return �J�[�\��
	 */
	public Cursor getCursor() {
		return cursor;
	}

	public CommonCommand getCommonCommand() {
		return commoncommand;
	}

	public SunCommand getSunCommand() {
		return suncommand;
	}

	public PaintItem getCommand() {
		if (suncommand.visible()) {
			System.out.println("return suncommand");
			return suncommand;
		}
		System.out.println("return commoncommand");
		return commoncommand;
	}

	/**
	 * �������擾
	 * 
	 * @return ����
	 */
	public int getHeight() {
		return canvas.getHeight();
	}

	/**
	 * �����擾
	 * 
	 * @return ��
	 */
	public int getWidth() {
		return canvas.getWidth();
	}

	/**
	 * �ĕ`��
	 * 
	 */
	public void repaint() {
		if (lockrepaint)
			return;
		lockrepaint = true;
		lockrepaint = false;
		synchronized(canvas){
			canvas.repaint();
		}
	}

	private volatile EventProcesser eventProcesser;

	public void setEventProcesser(EventProcesser e) {
		eventProcesser = e;
	}

	/**
	 * ���݂̃C�x���g�v���Z�b�T���擾����B
	 * 
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
	 * 
	 * @param e
	 *            �폜����C�x���g�v���Z�b�T
	 * @return �폜������true�A���Ȃ����false; ��v������폜����B
	 */
	public boolean removeEventProcesser(EventProcesser e) {
		if (e == eventProcesser) {
			eventProcesser = null;
			return true;
		} else {
			return false;
		}
	}

	public void onTurnStart(Player player) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
