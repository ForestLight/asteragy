package jp.ac.tokyo_ct.asteragy;

import com.nttdocomo.ui.*;

/**
 * @author Okubo ���ꂩ�Ȃ�����
 * 
 */
public class Title extends Canvas{
	private static Image title;
	private static Image[] menu;
	private static int depth = 0;
	private static int cursor = 0;
	private static int gameType = -1;
	// private static int highScore;

	public Title(){
		title = loadImage("title.jpg");
		menu = new Image[5];
		for(int i = 0; i < 5; i++)
			menu[i] = loadImage("menu_" + i + ".gif");

	}

	/**
	 * �^�C�g����ʂ̕`��
	 * @return gameType
	 * 				0:��@�ΐ� 1:AI�ΐ� 2:�l�b�g���[�N�ΐ�
	 */
	public int start(){
		Graphics g = getGraphics();
		for(;;){
			System.out.println("roop");
			if(gameType >= 0){
				return gameType;
			}

			g.lock();

			g.drawImage(title, 0, 0);

			// �ėp���̂Ȃ��R�[�h�ł����܂���
			if(depth == 0) {
				g.drawImage(menu[0],getWidth()/2-menu[0].getWidth()/2,150);
				g.drawImage(menu[1],getWidth()/2-menu[1].getWidth()/2,150+menu[1].getHeight());
				g.drawImage(menu[2],getWidth()/2-menu[2].getWidth()/2,150+menu[1].getHeight()+menu[2].getHeight());
			}
			else {
				g.drawImage(menu[3],getWidth()/2-menu[3].getWidth()/2,150);
				g.drawImage(menu[4],getWidth()/2-menu[4].getWidth()/2,150+menu[4].getHeight());
			}
			g.setColor(Graphics.getColorOfName(Graphics.YELLOW));
			g.drawRect(getWidth()/2-menu[0].getWidth()/2,150+menu[0].getHeight()*cursor,menu[0].getWidth(),menu[0].getHeight());

			g.unlock(true);

			// sleep�͂Ȃ񂩃}�Y���C������
			try{
				Thread.sleep(50);
			}catch(Exception e){
			}
		}
	}

	private static Image loadImage(String s){
		try {
			// ���\�[�X����ǂݍ���
			MediaImage m = MediaManager.getImage("resource:///"+ s);
			// ���f�B�A�̎g�p�J�n
			m.use();
			// �ǂݍ���
			return m.getImage();
		} catch(Exception e) {
		}
		return null;
	}

	public void processEvent(int type, int param){
		if(type == Display.KEY_PRESSED_EVENT){
			switch(param){
			case Display.KEY_UP:
				if(cursor > 0)
					cursor--;
				break;
			case Display.KEY_DOWN:
				if(depth + cursor < 2)
					cursor++;
				break;
			case Display.KEY_SELECT:
				if(depth == 0 && cursor == 1){
					gameType = 0;
				}
				else if(depth == 0 && cursor == 2){
					IApplication.getCurrentApp().terminate();
				}
				else if(depth == 1 && cursor == 0){
					gameType = 1;
					
				}
				else if(depth == 1 && cursor == 1){
					gameType = 2;
				}
				else{
					depth++;
				}
				break;
			default:
				break;
			}
		}
	}

	public void paint(Graphics g){
	}
}
