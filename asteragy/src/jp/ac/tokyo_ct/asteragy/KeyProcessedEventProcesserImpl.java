/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * @author Yusuke Ichinohe EventProcesserの実装補助クラス
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
		// ここはbreakもreturnも置かない。
		case Display.KEY_SELECT:
			selected = true;
			return;
		default:
			processKeyEvent(param);
		}
	}

	/**
	 * KEY_PRESSED_EVENTで、決定でもクリアでもないボタンが押されたときに呼ばれる。
	 * 
	 * @param key
	 *            押されたキー
	 */
	abstract protected void processKeyEvent(int key);

	/**
	 * キャンセルの操作がなされたときにEventProcesserImplBaseから呼ばれる。派生クラスで上書きしてよい。
	 * 
	 * @return キャンセルを許可するならtrue、キャンセル操作を無視するならfalse。
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
