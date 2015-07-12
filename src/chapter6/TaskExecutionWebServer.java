package chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * P97 6-4 ʹ���̳߳�
 * @author skywalker
 *
 */
public class TaskExecutionWebServer {
	
	private static final int THREADS = 100;
	private static final Executor EXECUTOR = Executors.newFixedThreadPool(THREADS);

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			final Socket socket = server.accept();
			Runnable task = new Thread(new Runnable() {
				@Override
				public void run() {
					handleRequest(socket);
				}
			});
			//�߳��ǲ���ģ��߳�ִ�е������ǿɱ��
			EXECUTOR.execute(task);
		}
	}
	
	private static void handleRequest(Socket socket) {
		
	}
	
}
