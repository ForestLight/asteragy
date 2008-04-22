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

	boolean initialize(Option opt) {
		try {
			HttpConnection con = (HttpConnection) Connector.open(
					"http://localhost/?scmd=querygame", Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				InputStream is = con.openInputStream();
				try {
					id = Integer.parseInt(readLine(is));
					if (readLine(is) == "0") {
						isFirst = true;
						postOption(opt);
						return true;
					} else {
						getOption(opt);
						return false;
					}
				} finally {
					is.close();
				}
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return false; // �b��
		}
	}

	// ��U�ɂȂ����Ƃ��A�������̐ݒ�𑗂�B
	private void postOption(Option opt) {
		try {
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"postoption", isFirst).concat("&opt=").concat(opt.toString()),
					Connector.WRITE);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private void getOption(Option opt) {
		try {
			for (;;) {
				HttpConnection con = (HttpConnection) Connector.open(getUrl(
						"getoption", isFirst), Connector.READ);
				try {
					con.setRequestMethod(HttpConnection.GET);
					con.connect();
					if (con.getResponseCode() == HttpConnection.HTTP_NO_CONTENT) {
						Game.sleep(5000);
						continue;
					}
					InputStream is = con.openInputStream();
					try {
						opt.inputFromStream(is);
					} finally {
						is.close();
					}
				} finally {
					con.close();
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	// ��U�ɂȂ����Ƃ��A��U�������܂őҋ@����B
	void waitForAnotherPlayer() {
		try {
			for (;;) {
				HttpConnection con = (HttpConnection) Connector.open(getUrl(
						"querystart", isFirst), Connector.READ);
				try {
					con.setRequestMethod(HttpConnection.GET);
					con.connect();
					InputStream is = con.openInputStream();
					try {
						if (readLine(is) == "ok")
							return;
					} finally {
						is.close();
					}
				} finally {
					con.close();
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	void sendInitField(Field f) {
		try {
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"sendinitfield", isFirst).concat("&field=0"),
					Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.ac.tokyo_ct.asteragy.Player#getAction()
	 */
	public Action getAction() {
		try {
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"getaction", isFirst), Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				InputStream is = con.openInputStream();
				try {
					return Action.readFromStream(game, is);
				} finally {
					is.close();
				}
			} finally {
				con.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return null; // �b��
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

	private String getUrl(String cmd, boolean first) {
		return "http://localhost/?cmd=".concat(cmd).concat("&id=").concat(
				String.valueOf(id)).concat("&turn=0");
	}

	private void sendLog(Action a) {
		try {
			HttpConnection con = (HttpConnection) Connector.open(getUrl(
					"postaction", !isFirst), Connector.WRITE);
			try {
				con.setRequestProperty("Content-Type", "text/plain");
				con.setRequestMethod(HttpConnection.POST);
				OutputStream os = con.openOutputStream();
				try {
					a.outputToStream(os);
					os.write('\r');
					os.write('\n');
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

	static String readLine(InputStream is) throws IOException {
		StringBuffer buf = new StringBuffer(16);
		for (;;) {
			int c = is.read();
			if (c == -1 || c == '\n')
				break;
			if (c != '\r') // �蔲��
				buf.append((char) c);
		}
		return buf.toString();
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
		else if (c - '0' < 10)
			return c - '0';
		else if (c - 'A' < 6)
			return c - 'A' + 10;
		else
			return -1;
	}

	private Thread loggingThread;

	private volatile Action action;

	private int id;

	private boolean isFirst = false;
}
