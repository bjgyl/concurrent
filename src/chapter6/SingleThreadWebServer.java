package chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * P94 6-1
 * �򵥵ķ���������һ�̴߳�������
 * @author skywalker
 *
 */
public class SingleThreadWebServer {

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket socket = null;
		while (true) {
			socket = serverSocket.accept();
			handleRequest(socket);
		}
	}
	
	private static void handleRequest(Socket socket) {
		
	}
	
}
