package chapter7;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * P128 7-17 ͨ��"����"����ر�������-�����߷���
 * Ҫ�󼸸�����:
 * 	a) �����������ߵ���������֪��
 *  b) �������޽����
 * @author skywalker
 *
 */
public class IndexingService {

	private final BlockingQueue<File> files = new LinkedBlockingQueue<File>();
	//�������ֻ��һ��
	private static final File POISON = new File("");
	private final File root;
	private final CrawlerThread producer = new CrawlerThread();
	private final IndexerThread consumer = new IndexerThread();
	
	public void start() {
		producer.start();
		consumer.start();
	}
	
	public void stop() {
		//�ж������ߣ������߷��붾��
		producer.interrupt();
	}
	
	public IndexingService(File root) {
		this.root = root;
	}
	
	//�������߳�
	private class CrawlerThread extends Thread {
		@Override
		public void run() {
			try {
				//������ڵ�
				crawler(root);
			} finally {
				//���붾�����
				while (true) {
					try {
						files.put(POISON);
						break;
					} catch (InterruptedException e) {
						//���³���
					}
				}
			}
			
		}

		private void crawler(File root) {}
	}
	
	//�������߳�
	private class IndexerThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					File file = files.take();
					if (file == POISON) {
						break;
					} else {
						//��������
						index(file);
					}
				}
			} catch (InterruptedException e) {
				//�˳�
			}
		}

		private void index(File file) {}
	}
	
}
