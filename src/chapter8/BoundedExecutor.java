package chapter8;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * P145 8-4
 * ʹ���ź�������������ύ����
 * �������˼�Ǵﵽ���޺�execute�ͻ���������Ĭ�ϵĲ����޷�ʵ�ִ˹���
 * @author skywalker
 *
 */
public class BoundedExecutor {

	private final Executor executor;
	private final Semaphore semaphore;
	
	public BoundedExecutor(Executor executor, int bound) {
		this.executor = executor;
		this.semaphore = new Semaphore(bound);
	}
	
	/**
	 * �ύ����
	 * ��ϸ��ĥ������try���λ��
	 * @param runnable
	 * @throws InterruptedException 
	 */
	public void submitTask(final Runnable runnable) throws InterruptedException {
		semaphore.acquire();
		try {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						runnable.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}
	
}
