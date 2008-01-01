/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.Display;

/**
 * @author Yusuke Ichinohe EventProcesserの実装補助クラス
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
			// ここはbreakもreturnも置かない。
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

	protected final void setSelected() {
		selected = true;
	}

	/**
	 * 選択をユーザに行わせるため、待機する。
	 * 
	 * @param c
	 *            画面表示を行うGameCanvas。processEventを取得する対象。
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
