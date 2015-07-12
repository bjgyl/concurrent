package chapter6;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * P102 6-9
 * java.util.Timer�������:һ�������׳��쳣���ᵼ�º�������������޷�����(һ��Timer����ֻ�ᴴ��һ���߳���ִ�����е�����)
 * @author skywalker
 *
 */
public class OutOfTime {

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		TimeUnit.SECONDS.sleep(1);
		//����������쳣��Timer�ͱ�ȡ���ˣ�����ľͻ��׳�һ��
		//java.lang.IllegalStateException: Timer already cancelled.
		timer.schedule(new ThrowTask(), 5);
		TimeUnit.SECONDS.sleep(5);
	}
	
	/**
	 * �����׳��쳣������
	 * @author skywalker
	 *
	 */
	private static class ThrowTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("ִ��һ��");
			throw new RuntimeException();
		}
		
	}
	
}
