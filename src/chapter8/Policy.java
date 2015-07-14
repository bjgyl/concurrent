package chapter8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * P145 8-3 �޸Ķ��б��Ͳ���
 * @author skywalker
 *
 */
public class Policy {

	public static void main(String[] args) {
		//ע��˴�����ʹ���丸��������
		ThreadPoolExecutor executor = 
				new ThreadPoolExecutor(1, 10, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
		//���������в���
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
}
