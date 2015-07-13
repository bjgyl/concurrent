package chapter7;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * P113 7-3 ��֤��־���˳��������������������
 * ��������ܼ򵥣�ʹ���жϾ�����
 * @author skywalker
 *
 */
public class BlockFlagExit {
	
	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		//�������߳�
		Producer producer = new Producer(queue);
		Thread producerThread = new Thread(producer);
		//�������߳�
		Thread consumerThread = new Thread(new Consumer(queue));
		producerThread.start();
		consumerThread.start();
		//����10ms�����߳�
		TimeUnit.MILLISECONDS.sleep(10);
		consumerThread.interrupt();
		producer.cancell();
	}

	/**
	 * ������
	 * @author skywalker
	 *
	 */
	private static class Producer implements Runnable {
		private final BlockingQueue<Integer> queue;
		private volatile boolean cancelled = false;
		private final Random random = new Random();
		
		public Producer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		
		public void cancell() {
			cancelled = true;
		}

		@Override
		public void run() {
			while (!cancelled) {
				try {
					queue.put(random.nextInt(10));
					System.out.println("��������" + queue.size() + "��");
				} catch (InterruptedException e) {
				}
			}
		}
		
	}
	
	/**
	 * ������
	 * @author skywalker
	 *
	 */
	private static class Consumer implements Runnable {
		
		private final BlockingQueue<Integer> queue;
		private Integer i;
		
		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			try {
				while (true) {
					TimeUnit.MILLISECONDS.sleep(2);
					i = queue.take();
					System.out.println(i + "��ʹ��");
				}
			} catch (InterruptedException e) {
				System.out.println("�������˳�...");
			}
		}
		
	}
	
}
