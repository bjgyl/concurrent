package chapter7;

import java.util.concurrent.TimeUnit;

/**
 * �׳��ж��쳣���жϱ�־λ��λ��?
 * @author skywalker
 *
 */
public class Reset {

	public static void main(String[] args) {
		Task task = new Task();
		task.start();
		task.interrupt();
	}
	
	public static class Task extends Thread {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				//false, ȷʵ��λ��
				//System.out.println(Thread.currentThread().isInterrupted());
				//һ����Ҫ�̵�!
				Thread.currentThread().interrupt();
			}
		}
	}
	
}
