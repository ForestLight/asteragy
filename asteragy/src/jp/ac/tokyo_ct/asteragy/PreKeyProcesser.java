package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

final class PreKeyProcesser {

	// private final CanvasControl canvas;

	PreKeyProcesser(CanvasControl canvas) {
		// this.canvas = canvas;
	}

	void initKey(Canvas set) {
		// set.setSoftLabel(Frame.SOFT_KEY_1, "TITLE");
		// set.setSoftLabel(Frame.SOFT_KEY_2, "");
	}

	int processEvent(int type, int param) {
		if (type == Display.KEY_PRESSED_EVENT) {
			switch (param) {
			case Display.KEY_SOFT1:
				break;
			case Display.KEY_SOFT2:
				break;
			default:
				break;
			}
		}
		return type;
	}

}
