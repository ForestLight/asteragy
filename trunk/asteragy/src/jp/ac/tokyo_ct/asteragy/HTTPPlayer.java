/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import javax.microedition.io.*;
import com.nttdocomo.io.*;

/**
 * @author Yusuke ���݁A1P, 2P�̗�����HTTPPlayer�ɂȂ邱�Ƃ͂ł��Ȃ��B
 */
final class HTTPPlayer extends Player implements Runnable {

	/**
	 * @param game
	 * @param playerName
	 */
	public HTTPPlayer(Game game, String playerName) {
		super(game, playerName);
		loggingThread = new Thread(this);
		loggingThread.start();
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	public Action getAction() {
		try {
			HttpConnection con = (HttpConnection)Connector.open("http://localhost/?cmd=getaction",
					Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				InputStream is = con.openInputStream();
				try {
					return Action.inputFromStream(game, is);
				} finally {
					is.close();
				}
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return null; //�b��
		}
	}

	/**
	 * @brief �s���L�^���T�[�o�֑��M����B
	 * @param a
	 *            ���M����s�����L����Action�̃C���X�^���X
	 */
	synchronized void log(Action a) {
		action = a;
		notify(); // �@���N��
	}

	synchronized public void run() {
		for (;;) {
			try {
				wait();
				if (action != null) {
					sendLog(action);
					action = null;
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private void sendLog(Action a) {
		try {
			HttpConnection con = (HttpConnection)Connector.open("http://localhost/?cmd=postaction",
					Connector.WRITE);
			try {
				con.setRequestProperty("Content-Type", "text/plain");
				con.setRequestMethod(HttpConnection.POST);
				OutputStream os = con.openOutputStream();
				try {
					a.printToStream(os);
				} finally {
					os.close();
				}
				con.connect();
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private Thread loggingThread;

	private volatile Action action;
}
