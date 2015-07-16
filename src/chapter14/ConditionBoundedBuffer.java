package chapter14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import annotations.ThreadSafe;

/**
 * P252 14-11 ʹ����ʽ�����������н绺��
 * @author skywalker
 *
 */
@ThreadSafe
public class ConditionBoundedBuffer<V> extends BaseBoundedBuffer<V> {
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();
	
	public ConditionBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public void put(V value) throws InterruptedException {
		lock.lock();
		try {
			while (isFull()) {
				notFull.await();
			}
			doPut(value);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public V get() throws InterruptedException {
		lock.lock();
		try {
			while (isEmpty()) {
				notEmpty.await();
			}
			V value = doGet();
			notFull.signal();
			return value;
		} finally {
			lock.unlock();
		}
	}

}
