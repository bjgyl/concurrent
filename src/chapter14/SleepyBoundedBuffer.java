package chapter14;

import java.util.concurrent.TimeUnit;

/**
 * P241 14-5 ʹ�ü�������ʵ���н绺��
 * ���²��õ������Լ�������
 * @author skywalker
 *
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public void put(V value) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isFull()) {
					doPut(value);
					return;
				}
			}
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
	public V get() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty()) {
					return doGet();
				}
			}
			TimeUnit.SECONDS.sleep(1);
		}
	}

}
