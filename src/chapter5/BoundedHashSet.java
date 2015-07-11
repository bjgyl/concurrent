package chapter5;

import java.util.Set;
import java.util.concurrent.Semaphore;

import annotations.ThreadSafe;

/**
 * P83 5-14
 * ʹ��Semaphone�����н�����
 * @author skywalker
 *
 */
@ThreadSafe
public class BoundedHashSet<E> {

	private final Set<E> set;
	private final Semaphore semaphore;
	
	public BoundedHashSet(Set<E> set, int count) {
		this.set = set;
		this.semaphore = new Semaphore(count);
	}
	
	public boolean add(E e) throws InterruptedException {
		semaphore.acquire();
		boolean result = false;
		try {
			result = set.add(e);
			return result;
		}finally {
			//���ʧ�ܣ��ͷ�һ���ź���
			if(!result) {
				semaphore.release();
			}
		}
	}
	
	public boolean remove(E e) {
		boolean result = set.remove(e);
		if(result) {
			semaphore.release();
		}
		return result;
	}
	
}
