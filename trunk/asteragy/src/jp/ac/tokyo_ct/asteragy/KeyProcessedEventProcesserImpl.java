/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * @author Yusuke Ichinohe EventProcesser�̎����⏕�N���X
 */
abstract class KeyProcessedEventProcesserImpl implements EventProcesser {
	public synchronized void processEvent(int type, int param) {
		if (type != Display.KEY_PRESSED_EVENT) {
			return;
		}
		if (selected) {
			return;
		}

		switch (param) {
		case Display.KEY_0:
			System.out.println("Cancel");
			onCancel();
			// ������break��return���u���Ȃ��B
		case Display.KEY_SELECT:
			selected = true;
			notifyAll();
			return;
		default:
			System.out
					.println("KeyProcessedEventProcesserImpl.processEvent - processKeyEvent");
			processKeyEvent(param);
		}
	}

	/**
	 * KEY_PRESSED_EVENT�ŁA����ł��N���A�ł��Ȃ��{�^���������ꂽ�Ƃ��ɌĂ΂��B
	 * 
	 * @param key
	 *            �����ꂽ�L�[
	 */
	abstract protected void processKeyEvent(int key);

	/**
	 * �L�����Z���̑��삪�Ȃ��ꂽ�Ƃ���EventProcesserImplBase����Ă΂��B�h���N���X�ŏ㏑�����Ă悢�B
	 * 
	 * @return �L�����Z����������Ȃ�true�A�L�����Z������𖳎�����Ȃ�false�B
	 */
	protected boolean onCancel() {
		return true;
	}

	public final boolean isSelected() {
		return selected;
	}

	protected final void resetSelected() {
		selected = false;
	}

	protected final void setSelected() {
		selected = true;
	}

	/**
	 * �I�������[�U�ɍs�킹�邽�߁A�ҋ@����B
	 * 
	 * @param c
	 *            ��ʕ\�����s��GameCanvas�BprocessEvent���擾����ΏہB
	 */
	protected synchronized final void waitForSelect(CanvasControl c) {
		c.setEventProcesser(this);
		try {
			while (!selected) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
					// Thread.currentThread().interrupt();
				}
			}
		} finally {
			c.removeEventProcesser(this);
		}
	}

	private volatile boolean selected = false;
}
