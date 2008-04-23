/**
 * 
 */
package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import javax.microedition.io.*;
import com.nttdocomo.io.*;
import com.nttdocomo.ui.Dialog;

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

	// �������̐l�ԃv���C������U�Ȃ�true��Ԃ��B
	boolean initialize(Option opt) throws IOException {
		System.out.println("querygame");
		HttpConnection con = (HttpConnection) Connector.open(
				"http://localhost/?scmd=querygame", Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
			InputStream is = con.openInputStream();
			try {
				id = Integer.parseInt(readLine(is));
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
		System.out.println("postoption: " + url);
		HttpConnection con = (HttpConnection) Connector.open(url,
				Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
			con.openInputStream().close(); // �_�~�[�F�O�̂���
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
		String url = getUrl("sendinitfield", isLocalFirst).concat("&field=").concat(
				f.toStringForInit());
		System.out.println("sendinifield: " + url);
		HttpConnection con = (HttpConnection) Connector.open(url,
				Connector.READ);
		try {
			con.setRequestMethod(HttpConnection.GET);
			con.connect();
			con.openInputStream().close(); // �_�~�[�F�O�̂���
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
	public Action getAction() {
		System.out.println("getaction");
		try {
			for (;;) {
				HttpConnection con = (HttpConnection) Connector.open(getUrl(
						"getaction", isLocalFirst), Connector.READ);
				try {
					con.setRequestMethod(HttpConnection.GET);
					con.connect();
					if (con.getLength() != 0) {
						InputStream is = con.openInputStream();
						try {
							String s = readLine(is);
							if (s.equals("end"))
								return null;
							Action a = Action.readFromString(game, s);//Action.readFromStream(game, is);
							if (a != null) {
								System.out.print("getaction: ");
								System.out.println(a.toString());
								return a;
							}
						} finally {
							is.close();
						}
					}
				} finally {
					con.close();
				}
				Game.sleep(3000);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return null; // �b��
		}
	}

	void endTurn(int player) {
		try {
			String url = getUrl("endturn", player != 0);
			HttpConnection con = (HttpConnection) Connector.open(url,
					Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				con.openInputStream().close(); // �_�~�[�F�O�̂���
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
				String.valueOf(id)).concat("&turn=").concat(first ? "1" : "0");
	}

	private void sendLog(Action a) {
		try {
			String url = getUrl("sendaction", !isLocalFirst).concat("&action=")
					.concat(a.toString());
			System.out.println(url);
			HttpConnection con = (HttpConnection) Connector.open(url,
					Connector.READ);
			try {
				con.setRequestMethod(HttpConnection.GET);
				con.connect();
				con.openInputStream().close(); // �_�~�[�F�O�̂���
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
		if (Character.isDigit((char)c))
			return c - '0';
		int t = c - 'A';
		if (0 <= t && t < 6)
			return t + 10;
		else
			return -1;
	}

	private Thread loggingThread;

	private volatile Action action;

	private int id;

	private boolean isLocalFirst = false;
}
