package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * 
 * @author Okubo
 *
 * Title.java�ɓ����������������C�����Ȃ��ł��Ȃ�
 *
 */
public class ExplainAsterClass extends Canvas {

	public int number;

	private static Image asterClassImage;

	private int[][] range;

	ExplainAsterClass() {
		number = 1;
		asterClassImage = AsterClass.loadImage(number);
		range = AsterClass.getDefaultRange(number);
	}

	public void paint(Graphics g) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		g.lock();

		g.setColor(Graphics.getColorOfName(Graphics.BLACK));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(asterClassImage, 20, 20);
		g.setColor(Graphics.getColorOfName(Graphics.WHITE));
		g.drawString(AsterClass.classNameB[number-1], 20, 60);
		g.drawString("�R�}���h: " + AsterClass.commandName[number-1], 20, 80);
		g.drawString(AsterClass.commandExplain[number-1], 20, 100);
		g.drawString("�N���X�R�X�g: " + AsterClass.classCost[number-1], 20, 120);
		g.drawString("�R�}���h�R�X�g: " + AsterClass.commandCost[number-1], 20, 140);
		g.drawString("�s���񐔁F " + AsterClass.actionNum[number-1],20,160);

		g.drawString("0: ���ǂ�", 20, 237);

		for (int i = 0; i < range.length; i++) {
			for (int j = 0; j < range[0].length; j++) {
				if (range[i][j] == 1) {
					g.fillRect(180 + 5 * j, 180 + 5 * i, 5, 5);
					g.setColor(Graphics.getColorOfName(Graphics.BLACK));
					g.drawRect(180 + 5 * j, 180 + 5 * i, 4, 4);
					g.setColor(Graphics.getColorOfName(Graphics.WHITE));
				}
			}
		}
		g.setColor(Graphics.getColorOfName(Graphics.RED));
		g.fillRect(180+5*(range[0].length/2) + 1, 180+5*(range.length/2) + 1, 3, 3);

		g.unlock(true);
	}

	public void renew() {
		asterClassImage = AsterClass.loadImage(number);
		range = AsterClass.getDefaultRange(number);
	}
}