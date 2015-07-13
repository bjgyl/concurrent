package chapter7;

import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * P126 7-15 ���հ���־����
 * @author skywalker
 *
 */
public class LogService {

	private final BlockingQueue<String> queue;
	private final PrintWriter writer;
	private final LogThread logThread;
	//�Ƿ�ر�
	private boolean isShutDown = false;
	//��¼��������Ϣ�ĸ���
	private int reversation = 0;
	
	public LogService(PrintWriter writer) {
		this.queue = new ArrayBlockingQueue<String>(100);
		this.writer = writer;
		this.logThread = new LogThread();
	}
	
	public void start() {
		logThread.start();
	}
	
	public void stop() {
		synchronized (this) {
			isShutDown = true;
		}
		//����Ƕ����?
		logThread.interrupt();
	}
	
	public void log(String message) throws InterruptedException {
		synchronized (this) {
			if (isShutDown) {
				throw new IllegalStateException("The logger is shutdown");
			}
			++ reversation;
		}
		queue.put(message);
	}
	
	//��־�߳�
	private class LogThread extends Thread {
	
		@Override
		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogService.this) {
							if (isShutDown && reversation == 0) {
								break;
							}
						}
						writer.println(queue.take());
						synchronized (LogService.this) {
							-- reversation;
						}
					} catch (InterruptedException e) {
						//���ԣ���Ϊ��Ҫ�������������ʣ�����־�����������ж�
					}
				}
			} finally {
				writer.close();
			}
		}
		
	}
	
}
