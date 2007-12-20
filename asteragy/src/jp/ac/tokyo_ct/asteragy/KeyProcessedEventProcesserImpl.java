/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * @author Yusuke Ichinohe EventProcesser�̎����⏕�N���X
 */
abstract class KeyProcessedEventProcesserImpl implements EventProcesser {
	public void processEvent(int type, int param) {
		if (type != Display.KEY_PRESSED_EVENT) {
			return;
		}
		if (selected) {
			return;
		}

		switch (param) {
		case Display.KEY_CLEAR:
			onCancel();
		// ������break��return���u���Ȃ��B
		case Display.KEY_SELECT:
			selected = true;
			return;
		default:
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

	protected final void waitForSelect() {
		while (!selected) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private volatile boolean selected = false;
}
