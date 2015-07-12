package chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * P95 6-2
 * Ϊÿһ�����󵥶�����һ������
 * ȱ�����ڴ����̵߳ĳɱ�
 * @author skywalker
 *
 */
public class ThreadPerTaskServer {

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8080);
		Thread thread = null;
		while (true) {
			final Socket socket = server.accept();
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					handleRequest(socket);
				}
			});
			thread.start();
		}
	}
	
	private static void handleRequest(Socket socket) {
		
	}
	
}
