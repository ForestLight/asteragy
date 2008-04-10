package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * @author Okubo
 * 
 */
public class Title extends Canvas {
	private static Image title;

	private static Image[] menu;

	private static Image credit;

	private int depth = 0;

	private int cursor = 0;

	private boolean optionMenuFlag = false;

	private boolean explainAsterClassFlag = false;

	private boolean explainRuleFlag = false;

	private Option option = new Option();

	private ExplainAsterClass eac = new ExplainAsterClass();

	private ExplainRules er = new ExplainRules();

	// private static int highScore;

	private AudioPresenter ap;

	public Title() {
		if (title == null) {
			title = loadImage("title.jpg");
		}
		if (menu == null) {
			menu = new Image[10];
			for (int i = 0; i < 10; i++)
				menu[i] = loadImage("menu_" + i + ".gif");
		}
		if (credit == null) {
			credit = loadImage("credit.gif");
		}
	}

	private Thread startThread;

	/**
	 * �^�C�g����ʂ̕`��
	 * 
	 * @return option.gameType 0:��@�ΐ� 1:AI�ΐ� 2:�l�b�g���[�N�ΐ�
	 */
	public Option start() {
		startThread = Thread.currentThread();
		Display.setCurrent(this);
		option.gameType = -1;

		for (;;) {
			// System.out.println("loop");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if (option.gameType != -1)
				return option;
		}
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

	public void processEvent(int type, int param) {
		if (type == Display.KEY_PRESSED_EVENT) {
			switch (param) {
			case Display.KEY_UP:
				if (!explainAsterClassFlag && !explainRuleFlag) {
					if (!optionMenuFlag) {
						if (cursor > 0)
							cursor--;
						else if (depth == 0)
							cursor = 4;
						else
							cursor = 2; 
						break;
					}
					else {
						if (cursor > 0)
							cursor--;
						else
							cursor = 5;
						break;
					}
				}
			case Display.KEY_DOWN:
				if (!explainAsterClassFlag && !explainRuleFlag) {
					if (!optionMenuFlag) {
						if ((depth == 0 && cursor < 4) || (depth != 0 && cursor < 2))
							cursor++;
						else
							cursor = 0;
						break;
					}
					else {
						if (cursor < 5)
							cursor++;
						else
							cursor = 0;
						break;
					}
				}
			case Display.KEY_SELECT:
				if (!explainAsterClassFlag && !explainRuleFlag) {
					if (!optionMenuFlag) {
						if (depth == 0) {
							switch (cursor) {
							case 0:
								depth++;
								break;
							case 1:
								option.gameType = 0;
								break;
							case 2:
								depth += 2;
								cursor = 0;
								break;
							case 3:
								optionMenuFlag = true;
								cursor = 0;
								break;			
							case 4:
								IApplication.getCurrentApp().terminate();
							}
						}
						else if (depth == 1) {
							switch (cursor) {
							case 0:
								option.gameType = 1;
								break;
							case 1:
								option.gameType = 2;
								break;
							case 2:
								depth--;
								cursor = 0;
								break;	
							}
						}
						else {
							switch (cursor) {
							case 0:
								explainRuleFlag = true;
								break;
							case 1:
								explainAsterClassFlag = true;
								break;
							case 2:
								depth -= 2;
								cursor = 0;
								break;	
							}
						}
					}
					else {
						if (cursor == 5) {
							optionMenuFlag = false;
							cursor = 0;
						}
						else {
							cursor = 5;
						}
					}
				}
				break;
			case Display.KEY_LEFT:
				if (optionMenuFlag) {
					switch (cursor) {
					case 0:
						if (option.fieldYSize > 3)
							option.fieldYSize -= 2;
						break;
					case 1:
						if (option.fieldXSize > 3)
							option.fieldXSize -= 2;
						break;
					case 2:
						if (option.numOfColors > 4)
							option.numOfColors--;
						break;
					case 3:
						if (option.connection > 3)
							option.connection--;
						break;
					case 4:
						if (option.AP_Pointer > 0)
							option.AP_Pointer--;
						break;
					}
				}
				else if (explainAsterClassFlag && !explainRuleFlag) {
					eac.number--;
					if (eac.number < 1) {
						eac.number = 12;
					}
					eac.renew();
				}
				else if (explainRuleFlag) {
					
				}
				break;
			case Display.KEY_RIGHT:
				if (optionMenuFlag) {
					switch (cursor) {
					case 0:
						if (option.fieldYSize < 11)
							option.fieldYSize += 2;
						break;
					case 1:
						if (option.fieldXSize < 11)
							option.fieldXSize += 2;
						break;
					case 2:
						if (option.numOfColors < 5)
							option.numOfColors++;
						break;
					case 3:
						if (option.connection < 5)
							option.connection++;
						break;
					case 4:
						if (option.AP_Pointer < option.initialAP.length - 1)
							option.AP_Pointer++;
						break;
					}
				}
				else if (explainAsterClassFlag && !explainRuleFlag) {
					eac.number++;
					if (eac.number > 12) {
						eac.number = 1;
					}
					eac.renew();
				}
				else if (explainRuleFlag) {
					
				}
				break;
			case Display.KEY_0:
				explainAsterClassFlag = false;
				eac.number = 1;
				eac.renew();
				explainRuleFlag = false;
				
				break;
			}
			repaint();
		}
	}

	public void paint(Graphics g) {

		if (explainAsterClassFlag) {
			eac.paint(g);
		}
		else if (explainRuleFlag) {
			er.paint(g);
		}
		else {
			if (!optionMenuFlag) {
				g.lock();

				g.drawImage(title, 0, 0);

				if (depth == 0) {
					g.drawImage(menu[0], getWidth() / 2 - menu[0].getWidth() / 2,
						127);
					g.drawImage(menu[1], getWidth() / 2 - menu[1].getWidth() / 2,
						127 + menu[1].getHeight());
					g.drawImage(menu[2], getWidth() / 2 - menu[2].getWidth() / 2,
						127 + menu[1].getHeight() + menu[2].getHeight());
					g.drawImage(menu[3], getWidth() / 2 - menu[3].getWidth() / 2,
						127 + menu[1].getHeight() + menu[2].getHeight() + menu[3].getHeight());
					g.drawImage(menu[4], getWidth() / 2 - menu[4].getWidth() / 2,
						127 + menu[1].getHeight() + menu[2].getHeight() + menu[3].getHeight() + menu[4].getHeight());
				}
				else if (depth == 1) {
					g.drawImage(menu[5], getWidth() / 2 - menu[5].getWidth() / 2,
						127);
					g.drawImage(menu[6], getWidth() / 2 - menu[6].getWidth() / 2,
						127 + menu[6].getHeight());
					g.drawImage(menu[7], getWidth() / 2 - menu[7].getWidth() / 2,
						127 + menu[6].getHeight() + menu[7].getHeight());
				}
				else {
					g.drawImage(menu[8], getWidth() / 2 - menu[8].getWidth() / 2,
						127);
					g.drawImage(menu[9], getWidth() / 2 - menu[9].getWidth() / 2,
						127 + menu[9].getHeight());
					g.drawImage(menu[7], getWidth() / 2 - menu[7].getWidth() / 2,
						127 + menu[9].getHeight() + menu[7].getHeight());
				}
				g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
				g.drawRect(getWidth() / 2 - menu[0].getWidth() / 2, 127
					+ menu[0].getHeight() * cursor, menu[0].getWidth(), menu[0].getHeight());

				g.drawImage(credit, getWidth() / 2 - credit.getWidth() / 2,
						getHeight() - credit.getHeight());

				g.unlock(true);
			}
			else {
				boolean leftTriangle = false;
				boolean rightTriangle = false;

				g.lock();

				g.drawImage(title, 0, 0);
				g.setColor(Graphics.getColorOfRGB(0, 0, 0, 160));
				g.fillRect(0, 0, getWidth(), getHeight());

				g.setColor(Graphics.getColorOfName(Graphics.WHITE));
				g.drawString("�c�̒���", 0, 15);
				g.drawString("���̒���", 0, 30);
				g.drawString("�F�̐�", 0, 45);
				g.drawString("�����鐔", 0, 60);
				g.drawString("����AP", 0, 75);
				g.drawString("���ǂ�", 0, 237);

				g.drawString("" + option.fieldYSize, 120, 15);
				g.drawString("" + option.fieldXSize, 120, 30);
				g.drawString("" + option.numOfColors, 120, 45);
				g.drawString("" + option.connection, 120, 60);
				g.drawString("" + option.initialAP[option.AP_Pointer], 120, 75);

				g.setColor(Graphics.getColorOfName(Graphics.YELLOW));

				if (cursor < 5) {
					g.drawRect(118, cursor * 15 + 2, 15 + 7 * (cursor / 4), 15);
					switch (cursor) {
					case 0:
						if (option.fieldYSize > 3)
							leftTriangle = true;
						else
							leftTriangle = false;
						if (option.fieldYSize < 11)
							rightTriangle = true;
						else
							rightTriangle = false;
						break;
					case 1:
						if (option.fieldXSize > 3)
							leftTriangle = true;
						else
							leftTriangle = false;
						if (option.fieldXSize < 11)
							rightTriangle = true;
						else
							rightTriangle = false;
						break;
					case 2:
						if (option.numOfColors > 4)
							leftTriangle = true;
						else
						leftTriangle = false;
						if (option.numOfColors < 5)
							rightTriangle = true;
						else
							rightTriangle = false;
						break;
					case 3:
						if (option.connection > 3)
							leftTriangle = true;
						else
							leftTriangle = false;
						if (option.connection < 5)
							rightTriangle = true;
						else
							rightTriangle = false;
						break;
					case 4:
						if (option.AP_Pointer > 0)
							leftTriangle = true;
						else
							leftTriangle = false;
						if (option.AP_Pointer < option.initialAP.length - 1)
							rightTriangle = true;
						else
							rightTriangle = false;
						break;
					}
					if (leftTriangle) {
						g.drawString("<", 105, cursor * 15 + 14);
						g.drawString("<", 105, cursor * 15 + 15);
						g.drawString("|", 108, cursor * 15 + 14);
						g.drawString("|", 109, cursor * 15 + 14);
					}
					if (rightTriangle) {
						g.drawString(">", 142 + 7 * (cursor / 4), cursor * 15 + 14);
						g.drawString(">", 142 + 7 * (cursor / 4), cursor * 15 + 15);
						g.drawString("|", 138 + 7 * (cursor / 4), cursor * 15 + 14);
						g.drawString("|", 139 + 7 * (cursor / 4), cursor * 15 + 14);
					}
				}
				else {
					g.drawRect(0, 224, 35, 15);
				}

				g.unlock(true);

			}
		}
	}
}