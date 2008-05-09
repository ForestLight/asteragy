/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.*;
import com.nttdocomo.io.*;
import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.IApplication;

/**
 * @author Yusuke ���݁A1P, 2P�̗�����HTTPPlayer�ɂȂ邱�Ƃ͂ł��Ȃ��B
 */
final class HTTPPlayer extends Player implements Runnable {
	/**
	 * @param game
	 * @param playerName
	 */
	HTTPPlayer(Game game, String playerName) {
		super(game, playerName);
		loggingThread = new Thread(this);
		loggingThread.start();
		System.out.print("sourceURL: ");
		System.out.println(sourceURL);
	}

	// �������̐l�ԃv���C������U�Ȃ�true��Ԃ��B
	boolean initialize(Option opt) throws IOException {
		String url = sourceURL.concat("?scmd=querygame");
		System.out.println("querygame: ".concat(url));
		HttpConnection con = (HttpConnection) Connector.open(url,
				Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
			InputStream is = con.openInputStream();
			try {
				id = readLine(is);
				if (readLine(is).equals("0")) {
					isLocalFirst = true;
				} else {
					isLocalFirst = false;
				}
				System.out.println("id: " + id + " first: " + isLocalFirst);
			} finally {
				is.close();
			}
		} finally {
			con.close();
		}
		if (isLocalFirst) {
			postOption(opt);
		} else {
			getOption(opt);
		}
		return isLocalFirst;
	}

	// ��U�ɂȂ����Ƃ��A�������̐ݒ�𑗂�B
	private void postOption(Option opt) throws IOException {
		String url = getUrl("postoption", isLocalFirst).concat("&opt=").concat(
				opt.toString());
		System.out.print("postoption: ");
		System.out.println(url);
		HttpConnection con = (HttpConnection) Connector.open(url,
				Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
		} finally {
			con.close();
		}
	}

	private void getOption(Option opt) throws IOException {
		for (;;) {
			System.out.println("getoption");
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"getoption", isLocalFirst), Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				if (con.getLength() != 0) {
					InputStream is = con.openInputStream();
					try {
						opt.inputFromStream(is);
						return;
					} finally {
						is.close();
					}
				}
				Game.sleep(2000);
			} finally {
				con.close();
			}
		}
	}

	// ��U�ɂȂ����Ƃ��A��U�������܂őҋ@����B
	void waitForAnotherPlayer() throws IOException {
		System.out.println("querystart");
		for (;;) {
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"querystart", isLocalFirst), Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				InputStream is = con.openInputStream();
				try {
					String s = readLine(is);
					if (s.equals("ok"))
						return;
				} finally {
					is.close();
				}
			} finally {
				con.close();
			}
			Game.sleep(2000);
		}
	}

	void sendInitField(Field f) throws IOException {
		String url = getUrl("sendinitfield", isLocalFirst).concat("&field=")
				.concat(f.toStringForInit());
//		System.out.println(url);
		System.out.println("sendInitField");
		HttpConnection con = (HttpConnection) Connector.open(url,
				Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
		} finally {
			con.close();
		}
	}

	void getField(Field f) throws IOException {
		for (;;) {
			System.out.println("getinitfield");
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"getinitfield", isLocalFirst), Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				if (con.getLength() != 0) {
					InputStream is = con.openInputStream();
					try {
						f.inputFromStream(is);
						return;
					} finally {
						is.close();
					}
				}
				Game.sleep(2000);
			} finally {
				con.close();
			}
		}
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	Action getAction() {
		System.out.println("getaction");
		try {
			for (;;) {
				Game.sleep(3000);
				HttpConnection con = (HttpConnection) Connector.open(getUrl(
						"getaction", isLocalFirst), Connector.READ);
				try {
					con.setRequestMethod(HttpConnection.GET);
					con.connect();
					if (con.getLength() == 0) {
						continue;
					}
					InputStream is = con.openInputStream();
					try {
						String s = readLine(is);
						if (s.equals("end"))
							return null;
						System.out.println(s);
						Action a = Action.readFromString(game, s);
						// Action.readFromStream(game, is);
						System.out.println("getaction2");
						if (a == null) {
							a = Action.readFromString(game, s);
						}
						a.deleteList = readLine(is);
						System.out.println("getaction3");
						System.out.println(a.deleteList);
						if (a != null) {
							System.out.print("getaction: ");
							System.out.println(a.toString());
							return a;
						}
					} finally {
						is.close();
					}
				} finally {
					con.close();
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return null; // �b��
		}
	}

	void endTurn(int player) {
		if (game.getPlayerIndex(game.getCurrentPlayer()) != (isLocalFirst ? 0
				: 1)) {
			return;
		}
		try {
			System.out.println("endTurn");
			String url = getUrl("endturn", player != 0);
			HttpConnection con = (HttpConnection) Connector.open(url,
					Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
			} finally {
				con.close();
			}
		} catch (IOException e) {
			Dialog d = new Dialog(Dialog.BUTTON_OK, e.toString());
			d.setText("�\���󂠂�܂���B�ڑ����ł��܂���ł����B�Q�[���𑱍s�ł��Ȃ��\��������܂��B");
			d.show();
			System.out.println(e.toString());
		}
	}

	/**
	 * @brief �s���L�^���T�[�o�֑��M����B
	 * @param a
	 *            ���M����s�����L����Action�̃C���X�^���X
	 */
	synchronized void log(Action a) {
		if (game.getPlayerIndex(game.getCurrentPlayer()) != (isLocalFirst ? 0
				: 1)) {
			return;
		}
		System.out.println("log");
		async = a.toString();
	}

	synchronized void logDeletedList(Field f, Vector deletedList) {
		System.out.println("logDeletedList");
		if (game.getPlayerIndex(game.getCurrentPlayer()) != (isLocalFirst ? 0
				: 1)) {
			return;
		}
		StringBuffer buf = new StringBuffer();
		buf.append(getUrl("sendaction", !isLocalFirst));
		buf.append("&action=");
		buf.append(async);
		if (deletedList != null) {
			buf.append("&delete=");
			Enumeration en = deletedList.elements();
			while (en.hasMoreElements()) {
				Point pt = (Point) en.nextElement();
				buf.append(pt.x);
				buf.append(pt.y);
				buf.append(f.at(pt).getColor());
			}
		}
		async = buf.toString();
		System.out.print("logDeletedList: ");
		System.out.println(async);
		notify(); // �@���N��
	}

	public synchronized void run() {
		for (;;) {
			try {
				wait();
				if (async != null) {
					sendLog();
					async = null;
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private String getUrl(String cmd, boolean first) {
		return sourceURL.concat("?cmd=").concat(cmd).concat("&id=").concat(
				id).concat("&turn=").concat(first ? "1" : "0");
	}

	private void sendLog() {
		try {
			System.out.println(async);
			HttpConnection con = (HttpConnection) Connector.open(async,
					Connector.READ);
			async = null;
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
			} finally {
				con.close();
			}
		} catch (IOException e) {
			Dialog d = new Dialog(Dialog.BUTTON_OK, e.toString());
			d.setText("�\���󂠂�܂���B�ڑ����ł��܂���ł����B�Q�[���𑱍s�ł��܂���B");
			d.show();
			System.out.println(e.toString());
		}
	}

	static String readLine(InputStream is) throws IOException {
		final StringBuffer buf = new StringBuffer(16);
		for (;;) {
			int c = is.read();
			if (c == -1 || c == '\n')
				break;
			if (c != '\r') // �蔲��
				buf.append((char) c);
		}
		if (buf.length() != 0) {
			return buf.toString();
		} else {
			return "";
		}
	}

	/**
	 * 16�i�@��1�����ƂȂ�l���o��
	 * 
	 * @param os
	 * @param x
	 * @throws IndexOutOfBoundsException
	 *             x��[0, 15]�͈̔͂ɂȂ��Ƃ�
	 */
	static void writeIntChar(OutputStream os, int x)
			throws IndexOutOfBoundsException, IOException {
		if (x < 0 || x >= 16)
			throw new IndexOutOfBoundsException("Action.printInt");
		else if (x < 10)
			os.write(x + '0');
		else
			os.write(x + 'A');
	}

	static int readIntChar(InputStream is) throws IOException {
		return parseIntChar(is.read());
	}

	static int parseIntChar(int c) {
		if (c < 0)
			return -1;
		if (Character.isDigit((char) c))
			return c - '0';
		int t = c - 'A';
		if (0 <= t && t < 6)
			return t + 10;
		else
			return -1;
	}

	private Thread loggingThread;

	// run��sendLog�Ŏg��
	private volatile String async;

	private String id;

	private boolean isLocalFirst = false;

	private static String sourceURL = "http://clc2007.infocraft.co.jp/asteragy/";
		//IApplication.getCurrentApp().getSourceURL();
}
