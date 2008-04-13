package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

public class ExplainRules extends Canvas {

	public int page;

	public Image image;

	public Image[] pageImage = new Image[3];

	ExplainRules() {
		page = 0;
		image = AsterClass.loadImage(0);

		// �y�[�W�̍쐬�i�쐬���j
		//
		Graphics g;
		for (int i = 0; i < 3; i++) {
			pageImage[i] = Image.createImage(getWidth(), getHeight());
		}

		g = pageImage[0].getGraphics();
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
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
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
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
		
		g = pageImage[2].getGraphics();
		image = AsterClass.loadImage(0);
		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString("\"�A�X�e��\"��4�q����Ə�����", 20, 25);
		g.drawScaledImage(image, 20, 50, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 37, 50, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 54, 50, 17, 17, 0, 0, 17, 17);
		g.drawScaledImage(image, 71, 50, 17, 17, 0, 0, 17, 17);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.lock();

		g.drawImage(pageImage[page], 0, 0);

		g.unlock(true);
	}

}
