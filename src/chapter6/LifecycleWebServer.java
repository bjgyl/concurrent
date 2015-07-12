package chapter6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * P100 6-8
 * Ϊ�����������������ڹ���
 * @author skywalker
 *
 */
public class LifecycleWebServer {

	private static final int THREADS = 100;
	private final ExecutorService service = Executors.newFixedThreadPool(THREADS);
	
	/**
	 * ����������
	 */
	public void start() throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(80);
		while (!service.isShutdown()) {
			try {
				final Socket socket = server.accept();
				service.execute(new Runnable() {
					@Override
					public void run() {
						handleRequest(socket);
					}
				});
			} catch (RejectedExecutionException e) {
				if (!service.isShutdown()) {
					//��¼��־
				}
			}
		}
	}
	
	public void stop() {
		service.shutdown();
	}
	
	private void handleRequest(Socket connection) {
		if (isShutDownOrder(connection)) {
			stop();
		} else {
			dispather(connection);
		}
	}

	//ʵ�ʴ�������
	private void dispather(Socket connection) {
	}

	/**
	 * �ж��Ƿ��ǹرշ���������
	 */
	private boolean isShutDownOrder(Socket connection) {
		return false;
	}
	
}
