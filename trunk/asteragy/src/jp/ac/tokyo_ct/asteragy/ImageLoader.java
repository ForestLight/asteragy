package jp.ac.tokyo_ct.asteragy;

import java.io.*;
import java.util.*;

import javax.microedition.io.Connector;

import com.nttdocomo.io.*;
import com.nttdocomo.ui.*;

public class ImageLoader {

	private static final String filelisturl = "filelist.txt";

	private static final String imageurl = "image/";

	private final Hashtable images;

	public ImageLoader() {
		images = new Hashtable(30);
	}

	private Vector loadFileList() {
		HttpConnection connection = null;
		Vector filelist = new Vector(30);

		BufferedReader reader = null;

		try {
			String url = IApplication.getCurrentApp().getSourceURL().concat(
					filelisturl);

			// ヘッダ
			connection = (HttpConnection) Connector.open(url);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "plane/text, */*");

			// 接続
			connection.connect();
			if (connection.getResponseCode() != HttpConnection.HTTP_OK) {
				return null;
			}

			// 取得
			reader = new BufferedReader(new InputStreamReader(connection
					.openInputStream()));
			String buffer = null;
			while ((buffer = reader.readLine()) != null) {
				filelist.addElement(buffer);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return filelist;
	}

	private void downloadImage(Vector filelist) {
		Enumeration enumeration = filelist.elements();
		HttpConnection connection = null;

		try {
			int position = 0;
			while (enumeration.hasMoreElements()) {
				String filename = (String) enumeration.nextElement();

				String url = IApplication.getCurrentApp().getSourceURL()
						.concat(imageurl.concat(filename));
				System.out.println(url);

				connection = (HttpConnection) Connector.open(url);
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "image/gif, */*");

				connection.connect();
				InputStream input = connection.openInputStream();
				byte[] get = new byte[(int) connection.getLength()];
				input.read(get);
				MediaImage media = MediaManager.getImage(get);
				media.use();
				Image image = media.getImage();

				writeImage(position, filename, get);
				images.put(filename, image);
				position += 8 + filename.getBytes().length + get.length;
				System.out.println(filename);
				System.out.println(position);
				connection.close();
			}
		} catch (IOException e) {
			if (e instanceof ConnectionException)
				System.out.println(((ConnectionException) e).getStatus());
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	private boolean loadImage() {
		DataInputStream input = null;
		int position = 0;
		try {
			while (true) {
				input = Connector.openDataInputStream("scratchpad:///0;pos="
						.concat(String.valueOf(position)));
				// filename
				int lenght = input.readInt();
				if (lenght <= 0) {
					if (images.size() > 0)
						return false;
					return true;
				}
				System.out.println(lenght);
				byte[] buffer = new byte[lenght];
				if (input.read(buffer) < 0)
					return true;
				String filename = new String(buffer);
				System.out.println(filename);
				position += 4 + lenght;
				// image
				lenght = input.readInt();
				System.out.println(lenght);
				if (lenght <= 0)
					return true;
				buffer = new byte[lenght];
				if (input.read(buffer) < 0)
					return true;
				MediaImage media = MediaManager.getImage(buffer);
				media.use();
				images.put(filename, media.getImage());
				position += 4 + lenght;
			}
		} catch (EOFException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static byte[] integerToByte(int value) {
		byte[] v = new byte[4];
		v[0] = (byte) ((value >>> 24) & 0xff);
		v[1] = (byte) ((value >>> 16) & 0xff);
		v[2] = (byte) ((value >>> 8) & 0xff);
		v[3] = (byte) (value & 0xff);
		return v;
	}

	private void writeImage(int position, String filename, byte[] image) {
		OutputStream output = null;
		try {
			output = Connector.openOutputStream("scratchpad:///0;pos="
					.concat(String.valueOf(position)));
			System.out.println("filename:" + filename.getBytes().length);
			output.write(integerToByte(filename.getBytes().length));
			output.write(filename.getBytes());
			System.out.println("image:" + image.length);
			output.write(integerToByte(image.length));
			output.write(image);

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	public void load() {
		// System.out.println("loading");
		if (loadImage()) {
			Vector filelist = loadFileList();
			downloadImage(filelist);
		}
	}

	public Hashtable getImages() {
		return images;
	}

}
