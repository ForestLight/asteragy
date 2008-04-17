package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public final class ExplainRules extends Canvas {

	public int page;

	private Image image;

	private Image back;

	private Image[] pageImage = new Image[6];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// �y�[�W�̍쐬�i�쐬���j
		//
		Graphics g;
		for (int i = 0; i < 6; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}

		g = pageImage[0].getGraphics();
		back = loadImage("back.jpg");
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�P�D�����Ă炶�����Ƃ�", 15, 25);
		g.drawString("�t�B�[���h��̃A�X�e���𓮂�����", 15, 70);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("�@�@�@�@�@�@�@�A�X�e��", 15, 70);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�키�{�[�h�Q�[���ł��B", 15, 85);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("�y�A�X�e���z", 15, 120);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�t�B�[���h�̑S�}�X�ɔz�u����Ă���", 15, 135);
		g.drawString("���̂ŁA���ꂼ��ɐF������܂��B", 15, 150);
		g.drawImage(image, 20, 160);
		

		g = pageImage[1].getGraphics();
		image = AsterClass.loadImage(1);
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�Q�D�Q�[���̗���ƖړI", 15, 25);
		g.drawString("�v���C���[�͐�s������݂Ƀ^�[����", 15, 70);
		g.drawString("�i�߂܂��B�^�[�����ɂ́A���j�b�g��", 15, 85);
		g.drawString("����ł��܂��B", 15, 100);
		g.drawString("����̃T�������ł�����Ώ����ł��B", 15, 115);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("�@�@�@�@�@�@�@�@�@�@�@�@���j�b�g", 15, 85);
		g.drawString("�@�@�@�T��", 15, 115);
		g.drawString("�y���j�b�g�z", 15, 150);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�v���C���[�����삷�邱�Ƃ̂ł���", 15, 165);
		g.drawString("�A�X�e���ł��B�T���݂͌��ɍŏ�����", 15, 180);
		g.drawString("���L���Ă��郆�j�b�g�ł��B", 15, 195);
		g.drawScaledImage(image, 20, 210, 17, 17, 0, 0, 17, 17);
		g.setFlipMode(Graphics.FLIP_VERTICAL);
		g.drawScaledImage(image, 45, 210, 17, 17, 34, 0, 17, 17);
		g.drawString("�����͏��L�҂�\�킵�܂��B", 70, 220);
		
		g = pageImage[2].getGraphics();
		image = AsterClass.loadImage(0);
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�R�D�A�X�e��", 15, 25);
		g.drawString("�����F�̃A�X�e���͂S�ȏキ����", 15, 70);
		g.drawString("���Ƃŏ��ł��܂��B", 15, 85);
		g.drawString("���ł������Ƃɂ́A�ǂ�����Ƃ��Ȃ�", 15, 100);
		g.drawString("�����_���ɃA�X�e������[����܂��B", 15, 115);
		g.drawScaledImage(image, 20, 130, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 20, 147, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 37, 147, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 37, 164, 17, 17, 17, 0, 17, 17);
		g.drawString("��", 66, 160);
		image = loadImage("disappear.gif");
		g.drawScaledImage(image, 90, 130, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 90, 147, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 107, 147, 17, 17, 0, 153, 17, 17);
		g.drawScaledImage(image, 107, 164, 17, 17, 0, 153, 17, 17);
		g.drawString("��", 136, 160);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 160, 130, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 160, 147, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 177, 147, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 177, 164, 17, 17, 68, 0, 17, 17);
		g.drawString("�܂��A���ł������A�X�e���̐������A", 15, 210);
		g.drawString("AP(�A�X�^�[�p���[)�����܂�܂��B", 15, 225);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("AP(�A�X�^�[�p���[)", 15, 225);

		g = pageImage[3].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�S�D�N���X�ƃ��j�b�g", 15, 25);
		g.drawString("�T���̃R�}���h�ŃA�X�e���ɃN���X��", 15, 70);
		g.drawString("�^���邱�ƂŁA�����̃��j�b�g�Ƃ���", 15, 85);
		g.drawString("���삷�邱�Ƃ��ł��܂��B", 15, 100);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 20, 115, 17, 17, 0, 0, 17, 17);
		image = AsterClass.loadImage(3);
		g.drawScaledImage(image, 37, 115, 17, 17, 17, 0, 17, 17);
		image = AsterClass.loadImage(5);
		g.drawScaledImage(image, 54, 115, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(8);
		g.drawScaledImage(image, 71, 115, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(9);
		g.drawScaledImage(image, 88, 115, 17, 17, 68, 0, 17, 17);
		g.drawString("���j�b�g�̓X���b�v�ƃR�}���h(����", 15, 160);
		g.drawString("�\��)�̂Q���g���܂��B", 15, 175);
		g.drawString("�R�}���h�̎�ނƃ����W(�˒��͈�)��", 15, 190);
		g.drawString("�^�����N���X�ɂ���ĈقȂ�܂��B", 15, 205);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("�@�@�@�@�@�X���b�v�@�R�}���h(����", 15, 160);
		g.drawString("�\��)", 15, 175);
		g.drawString("�@�@�@�@�@�@�@�@�����W(�˒��͈�)", 15, 190);

		g = pageImage[4].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�T�D���j�b�g�̔\��(�X���b�v)", 15, 25);
		g.drawString("���j�b�g�̊�{�I�ȍs���ł��B", 15, 70);
		g.drawString("�����W���ׂ̗荇�����Q�̃A�X�e��", 15, 85);
		g.drawString("�����ւ��܂��B", 15, 100);
		g.drawString("���̃��j�b�g���g���I���ł��܂��B", 15, 115);
		g.drawString("��", 106, 162);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 40, 131, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 21, 150, 17, 17, 68, 0, 17, 17);
		g.drawScaledImage(image, 59, 150, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 40, 169, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 40, 151, 17, 17, 51, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(20, 149, 19, 19);
		g.drawRect(58, 149, 19, 19);
		g.drawRect(39, 168, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(39, 130, 19, 19);
		g.drawRect(39, 149, 19, 19);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 160, 150, 17, 17, 17, 0, 17, 17);
		g.drawScaledImage(image, 141, 150, 17, 17, 68, 0, 17, 17);
		g.drawScaledImage(image, 179, 150, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 160, 169, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(2);
		g.drawScaledImage(image, 160, 132, 17, 17, 51, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(140, 149, 19, 19);
		g.drawRect(178, 149, 19, 19);
		g.drawRect(159, 168, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(159, 130, 19, 19);
		g.drawRect(159, 149, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�΂߂⃌���W�O�̃A�X�e���̓���ւ�", 15, 210);
		g.drawString("�͂ł��܂���B", 15, 225);

		g = pageImage[5].getGraphics();
		g.drawImage(back, 0, 0);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("�U�D���j�b�g�̔\��(�R�}���h)", 15, 25);
		g.drawString("�N���X�ɂ��قȂ����ȍs���ł��B", 15, 70);
		g.drawString("AP������Ďg�p���܂��B", 15, 85);
		g.drawString("��", 106, 140);
		image = AsterClass.loadImage(6);
		g.setFlipMode(Graphics.FLIP_VERTICAL);
		g.drawScaledImage(image, 40, 101, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(0);
		g.setFlipMode(Graphics.FLIP_NONE);
		g.drawScaledImage(image, 21, 120, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 40, 120, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 59, 120, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 21, 139, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 59, 139, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(4);
		g.drawScaledImage(image, 40, 139, 17, 17, 68, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(20, 119, 57, 19);
		g.drawRect(20, 138, 57, 19);
		g.drawRect(39, 119, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(39, 100, 19, 19);
		g.drawRect(39, 138, 19, 19);
		image = AsterClass.loadImage(6);
		g.drawScaledImage(image, 160, 101, 17, 17, 34, 0, 17, 17);
		image = AsterClass.loadImage(0);
		g.drawScaledImage(image, 141, 120, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 160, 120, 17, 17, 51, 0, 17, 17);
		g.drawScaledImage(image, 179, 120, 17, 17, 34, 0, 17, 17);
		g.drawScaledImage(image, 141, 139, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 179, 139, 17, 17, 51, 0, 17, 17);
		image = AsterClass.loadImage(4);
		g.drawScaledImage(image, 160, 139, 17, 17, 68, 0, 17, 17);
		g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
		g.drawRect(140, 119, 57, 19);
		g.drawRect(140, 138, 57, 19);
		g.drawRect(159, 119, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawRect(159, 100, 19, 19);
		g.drawRect(159, 138, 19, 19);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("��: ���B�[�i�X�̃R�}���h", 15, 180);
		g.drawString("�g�e���v�e�[�V�����h", 80, 195);
		g.drawString("�u" + AsterClass.commandExplain[3] + "�v", 40, 215);
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.drawString("AP", 15, 85);
		g.drawLine(50, 217, 188, 217);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.lock();

		g.drawImage(pageImage[page], 0, 0);

		g.unlock(true);
	}

	private static Image loadImage(String s) {
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///" + s);
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			return m.getImage();
		} catch (Exception e) {
		}
		return null;
	}

}
