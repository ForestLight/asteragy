package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class NeptuneClass extends AsterClass {
	private static final int[][] defaultRange = { 
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0 }};

	private static Image asterImage;

	public NeptuneClass(Aster a, Player p) {
		super(a, p);
	}

	public NeptuneClass(NeptuneClass a) {
		super(a);
	}

	public AsterClass clone() {
		return new NeptuneClass(this);
	}

	public int getNumber() {
		return 10;
	}

	public int[][] getRange() {
		switch (mode) {
		case 0:
			return swapGetRange(defaultRange);
		case 1:
			int[][] range = new int[defaultRange.length][defaultRange[0].length];
			final Field field = getAster().getField();
			final Point thisPoint = field.asterToPoint(getAster());
			// �����W�̍���̍��W�̃t�B�[���h���ł̈ʒu
			Point pt = new Point();
			pt.x = thisPoint.x - (range[0].length / 2);
			pt.y = thisPoint.y - (range.length / 2);

			for (int i = 0; i < defaultRange.length; i++) {
				if (!field.isYInFieldBound(pt.y + i))
					continue;
				for (int j = 0; j < defaultRange[0].length; j++) {
					if (!field.isXInFieldBound(pt.x + j))
						continue;

					// �����W���ł���
					if (defaultRange[i][j] == 1) {
						range[i][j] = 1;
						// ���̈ʒu�̃A�X�e���ɃN���X������
						final Aster a = field.at(pt.y + i, pt.x + j);
						final AsterClass c = a.getAsterClass();
						if (c != null) {
							if (c.getNumber() == 1) {
								range[i][j] = 0;
							}
						}
					}
				}
			}
			return range;
		}
		
		return null;
	}

	public boolean setPointAndNext(Point pt) {
		switch (mode) {
		case 0:
			return swapSetPointAndNext(pt);
		case 1:
			target1 = pt;
			return true;
		}
		return false;
	}

	public boolean hasNext() {

		switch (mode) {
		case 0:
			return swapHasNext();
		case 1:
			if (target1 == null)
				return true;
			else
				return false;
		}
		return false;
	}

	public boolean moveAstern() {
		switch (mode) {
		case 0:
			return swapMoveAstern();
		case 1:
			return true;
		}
		return false;
	}

	public void executeSpecialCommand() {
		// �^�[�Q�b�g�Ǝ�����swap
		final Field f = getAster().getField();
		f.getCanvas().paintEffect(new EffectCommandNeptune(f, target1));

		f.swap(target1, f.asterToPoint(getAster()));
		logAction(target1);

	}

	static int[][] getDefaultRange() {
		return defaultRange;
	}

	public Image getImage() {
		if (asterImage == null) {
			asterImage = loadImage(10);
		}
		return asterImage;
	}

}
