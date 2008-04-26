package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;
import com.nttdocomo.util.Timer;
import com.nttdocomo.util.TimerListener;

/**
 * @author Okubo
 * 
 */
public final class Title extends Canvas implements TimerListener {
	private static Image title = Game.loadImage("title");

	private static Image back = Game.loadImage("titleback");

	private static Image[] menu;

	private static Image credit = Game.loadImage("credit");

	private int depth = 0;

	private int cursor = 0;

	private int key;

	private boolean optionMenuFlag = false;

	private boolean explainAsterClassFlag = false;

	private boolean explainRuleFlag = false;

	private boolean unlimit = false;

	private Option option = new Option();

	private ExplainAsterClass eac;

	private ExplainRules er = new ExplainRules();

	private ShootingStar star;

	private Timer timer;

	// private static int highScore;

	Title() {
		if (menu == null) {
			menu = new Image[10];
			for (int i = 0; i < 10; i++)
				menu[i] = Game.loadImage("menu_".concat(String.valueOf(i)));
		}
		if (timer == null) {
			timer = new Timer();
			timer.setListener(this);
			timer.setTime(5);
			timer.setRepeat(true);
		}
		star = new ShootingStar(this, null);
		eac = new ExplainAsterClass(this);
	}

	/**
	 * タイトル画面の描画
	 * 
	 * @return option.gameType 0:一機対戦 1:AI対戦 2:ネットワーク対戦
	 */
	public Option start() {
		Display.setCurrent(this);
		option.gameType = -1;
		timer.start();

		for (;;) {
			// System.out.println("loop");
			Game.sleep(1000);
			if (option.gameType != -1) {
				timer.stop();
				return option;
			}
		}
	}

	public void resume() {
		if (option.gameType == -1) {
			timer.start();
		}
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
					} else {
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
						if ((depth == 0 && cursor < 4)
								|| (depth != 0 && cursor < 2))
							cursor++;
						else
							cursor = 0;
						break;
					} else {
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
								option.gameType = 0;
								break;
							case 1:
								depth++;
								cursor = 0;
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
						} else if (depth == 1) {
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
						} else {
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
					} else {
						if (cursor == 5) {
							optionMenuFlag = false;
							cursor = 0;
						} else {
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
						if (!(((key >> Display.KEY_1) & 0x1) == 1) && !(((key >> Display.KEY_3) & 0x1) == 1)) {
							unlimit = false;
							if (option.asterPower > Option.initialAP[option.AP_Pointer]) {
								option.AP_Pointer++;
							}
						}
						if (unlimit) {
							option.asterPower--;
							if (option.AP_Pointer > 0 && option.asterPower <= Option.initialAP[option.AP_Pointer-1])
								option.AP_Pointer--;
						}
						else if (option.AP_Pointer > 0) {
							option.AP_Pointer--;
							option.asterPower = Option.initialAP[option.AP_Pointer];
						}
						break;
					}
				} else if (explainAsterClassFlag && !explainRuleFlag) {
					eac.number--;
					if (eac.number < 0) {
						eac.number = 11;
					}
				} else if (explainRuleFlag) {
					er.page--;
					if (er.page < 0) {
						er.page = 5;
					}
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
						if (!(((key >> Display.KEY_1) & 0x1) == 1) && !(((key >> Display.KEY_3) & 0x1) == 1)) {
							unlimit = false;
							if (option.asterPower < Option.initialAP[option.AP_Pointer]) {
								option.AP_Pointer--;
							}
						}	
						if (unlimit) {
							option.asterPower++;
							if (option.AP_Pointer < Option.initialAP.length - 1 && option.asterPower >= Option.initialAP[option.AP_Pointer+1])
								option.AP_Pointer++;
						}
						else if (option.AP_Pointer < Option.initialAP.length - 1) {
							option.AP_Pointer++;
							option.asterPower = Option.initialAP[option.AP_Pointer];
						}
						break;
					}
				} else if (explainAsterClassFlag && !explainRuleFlag) {
					eac.number++;
					if (eac.number > 11) {
						eac.number = 0;
					}
				} else if (explainRuleFlag) {
					er.page++;
					if (er.page > 5) {
						er.page = 0;
					}
				}
				break;
			case Display.KEY_0:
			case Display.KEY_2:
			case Display.KEY_IAPP:
			case Display.KEY_CLEAR:
				explainAsterClassFlag = false;
				eac.number = 0;
				explainRuleFlag = false;
				er.page = 0;

				break;
			}
			repaint();
		}
		System.out.println(type + "," + param);
	}

	public void paint(Graphics g) {
		key = getKeypadState();

		if (explainAsterClassFlag) {
			eac.paint(g);
		} else if (explainRuleFlag) {
			er.paint(g);
		} else {
			if (!optionMenuFlag) {
				g.lock();

				g.drawImage(back, 0, 0);

				star.paint(g);

				g.drawImage(title, 10, 0);

				final int w = getWidth() / 2;
				if (depth == 0) {
					g.drawImage(menu[0], w - menu[0].getWidth() / 2, 127);
					g.drawImage(menu[1], w - menu[1].getWidth() / 2,
							127 + menu[1].getHeight());
					g.drawImage(menu[2], w - menu[2].getWidth() / 2, 127
							+ menu[1].getHeight() + menu[2].getHeight());
					g.drawImage(menu[3], w - menu[3].getWidth() / 2, 127
							+ menu[1].getHeight() + menu[2].getHeight()
							+ menu[3].getHeight());
					g.drawImage(menu[4], w - menu[4].getWidth() / 2, 127
							+ menu[1].getHeight() + menu[2].getHeight()
							+ menu[3].getHeight() + menu[4].getHeight());
				} else if (depth == 1) {
					g.drawImage(menu[5], w - menu[5].getWidth() / 2, 127);
					g.drawImage(menu[6], w - menu[6].getWidth() / 2,
							127 + menu[6].getHeight());
					g.drawImage(menu[7], w - menu[7].getWidth() / 2, 127
							+ menu[6].getHeight() + menu[7].getHeight());
				} else {
					g.drawImage(menu[8], w - menu[8].getWidth() / 2, 127);
					g.drawImage(menu[9], w - menu[9].getWidth() / 2,
							127 + menu[9].getHeight());
					g.drawImage(menu[7], w - menu[7].getWidth() / 2, 127
							+ menu[9].getHeight() + menu[7].getHeight());
				}
				g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
				g.drawRect(w - menu[0].getWidth() / 2, 127
						+ menu[0].getHeight() * cursor, menu[0].getWidth(),
						menu[0].getHeight());

				g.drawImage(credit, w - credit.getWidth() / 2, getHeight()
						- credit.getHeight());

				g.unlock(true);
			} else {
				boolean leftTriangle = false;
				boolean rightTriangle = false;

				g.lock();

				g.drawImage(back, 0, 0);

				star.paint(g);

				g.drawImage(title, 10, 0);
				g.setColor(Graphics.getColorOfRGB(0, 0, 0, 160));
				g.fillRect(0, 0, getWidth(), getHeight());

				g.setColor(Graphics.getColorOfName(Graphics.WHITE));
				g.drawString("縦の長さ", 0, 15);
				g.drawString("横の長さ", 0, 30);
				g.drawString("色の数", 0, 45);
				g.drawString("消える数", 0, 60);
				g.drawString("初期AP", 0, 75);
				g.drawString("もどる", 0, 237);

				g.drawString(String.valueOf(option.fieldYSize), 120, 15);
				g.drawString(String.valueOf(option.fieldXSize), 120, 30);
				g.drawString(String.valueOf(option.numOfColors), 120, 45);
				g.drawString(String.valueOf(option.connection), 120, 60);
				g.drawString(String.valueOf(option.asterPower), 120, 75);

				g.setColor(Graphics.getColorOfName(Graphics.YELLOW));

				if (cursor < 5) {
					g.drawRect(118, cursor * 15 + 2, 15 + 7 * (cursor / 4), 15);
					switch (cursor) {
					case 0:
						leftTriangle = option.fieldYSize > 3;
						rightTriangle = option.fieldYSize < 11;
						break;
					case 1:
						leftTriangle = option.fieldXSize > 3;
						rightTriangle = option.fieldXSize < 11;
						break;
					case 2:
						leftTriangle = option.numOfColors > 4;
						rightTriangle = option.numOfColors < 5;
						break;
					case 3:
						leftTriangle = option.connection > 3;
						rightTriangle = option.connection < 5;
						break;
					case 4:
						leftTriangle = option.AP_Pointer > 0;
						rightTriangle = option.AP_Pointer < Option.initialAP.length - 1;
						break;
					}
					if (leftTriangle) {
						g.drawString("<", 105, cursor * 15 + 14);
						g.drawString("<", 105, cursor * 15 + 15);
						g.drawString("|", 108, cursor * 15 + 14);
						g.drawString("|", 109, cursor * 15 + 14);
					}
					if (rightTriangle) {
						g.drawString(">", 142 + 7 * (cursor / 4),
								cursor * 15 + 14);
						g.drawString(">", 142 + 7 * (cursor / 4),
								cursor * 15 + 15);
						g.drawString("|", 138 + 7 * (cursor / 4),
								cursor * 15 + 14);
						g.drawString("|", 139 + 7 * (cursor / 4),
								cursor * 15 + 14);
					}
					if(cursor == 4 && ((key >> Display.KEY_1) & 0x1) == 1 && ((key >> Display.KEY_3) & 0x1) == 1){
						unlimit = true;
						g.setColor(Graphics.getColorOfName(Graphics.LIME));
						g.drawRect(118, cursor * 15 + 2, 15 + 7 * (cursor / 4), 15);
						g.drawString("<", 105, cursor * 15 + 14);
						g.drawString("<", 105, cursor * 15 + 15);
						g.drawString("|", 108, cursor * 15 + 14);
						g.drawString("|", 109, cursor * 15 + 14);
						g.drawString(">", 142 + 7 * (cursor / 4), cursor * 15 + 14);
						g.drawString(">", 142 + 7 * (cursor / 4), cursor * 15 + 15);
						g.drawString("|", 138 + 7 * (cursor / 4), cursor * 15 + 14);
						g.drawString("|", 139 + 7 * (cursor / 4), cursor * 15 + 14);
						g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
					}
				} else {
					g.drawRect(0, 224, 35, 15);
				}

				g.unlock(true);
			}
		}
	}

	public void timerExpired(Timer timer) {
		repaint();
	}
}
