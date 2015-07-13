package chapter7;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * P125 7-13 ��֧�ֹرյ�������-��������־����
 * �˴��Ĳ�֧�ֹر�Ӧ���������˼:
 * �����߿��Թر�(ͨ��IntteruptedException)�����������߲�֪�������߹ر��ˣ�
 * ������BlockingQueue�������Ϣ���Ӷ��������е������߶�������
 * @author skywalker
 *
 */
public class LogWriter {

	private final BlockingQueue<String> queue;
	private final LogThread logThread;
	
	public LogWriter(Writer writer) {
		this.queue = new ArrayBlockingQueue<String>(100);
		this.logThread = new LogThread(writer);
	}
	
	public void start() {
		logThread.start();
	}
	
	/**
	 * ��־��¼
	 */
	public void log(String messgae) throws InterruptedException {
		queue.put(messgae);
	}
	
	//��־�߳�
	private class LogThread extends Thread {
		
		private final Writer writer;
		
		public LogThread(Writer writer) {
			this.writer = writer;
		}
		
		@Override
		public void run() {
			try {
				while (true) {
					writer.write(queue.take());
				}
			} catch (IOException | InterruptedException e) {
				//�������˳�
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
