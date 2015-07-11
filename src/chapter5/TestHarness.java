package chapter5;

import java.util.concurrent.CountDownLatch;

/**
 * P79 5-11
 * ʹ��CountDownLatch��������ֹͣ�߳�
 * @author skywalker
 *
 */
public class TestHarness {

	/**
	 * ����-������
	 * @param threads �߳�����
	 * @param task �߳����е�����
	 */
	public static void timeTasks(int threads, final Runnable task) throws InterruptedException {
		//������
		final CountDownLatch beginGate = new CountDownLatch(1);
		//������
		final CountDownLatch endGate = new CountDownLatch(threads);
		Thread thread = null;
		for(int i = 0;i < threads;i ++) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//�ȴ�������
						beginGate.await();
						//ִ������
						task.run();
						//�����ż�һ
						endGate.countDown();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			});
			thread.start();
		}
		long beginTime = System.nanoTime();
		//ȫ���߳�����
		beginGate.countDown();
		//�ȴ������߳̽���
		endGate.await();
		long endTime = System.nanoTime();
		System.out.println("��ʱ:" + (endTime - beginTime));
	}
	
}
