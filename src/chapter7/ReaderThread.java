package chapter7;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * P122 7-11 ����I/O�̵߳��жϷ���---ֱ�ӹر�InputStream
 * @author skywalker
 *
 */
public class ReaderThread extends Thread {

	private final Socket socket;
	private final InputStream is;
	
	public ReaderThread(Socket socket) throws IOException {
		this.socket = socket;
		this.is = socket.getInputStream();
	}
	
	/**
	 * ��д�жϷ����������ж�����IO
	 */
	@Override
	public void interrupt() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			super.interrupt();
		}
	}
	
	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1024];
			int length = 0;
			while (true) {
				length = is.read(buffer);
				if (length > 0) {
					process(buffer);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			//�߳��˳�
		}
	}

	private void process(byte[] buffer) {}
	
}
