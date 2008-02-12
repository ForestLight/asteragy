/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import javax.microedition.io.*;
import com.nttdocomo.io.*;

/**
 * @author Yusuke 現在、1P, 2Pの両方がHTTPPlayerになることはできない。
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
	 * (非 Javadoc)
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
			return null; //暫定
		}
	}

	/**
	 * @brief 行動記録をサーバへ送信する。
	 * @param a
	 *            送信する行動を記したActionのインスタンス
	 */
	synchronized void log(Action a) {
		action = a;
		notify(); // 叩き起す
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
