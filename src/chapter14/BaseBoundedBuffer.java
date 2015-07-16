package chapter14;

import annotations.ThreadSafe;

/**
 * P239 14-2 ѭ���н绺��Ļ���
 * @author skywalker
 *
 */
@ThreadSafe
public abstract class BaseBoundedBuffer<V> {

	private final V[] buf;
	//ͷ��βָ�룬Ĭ�϶���0����Ϊ��ʾ����ʵ��Ԫ�ص�λ��
	private int head = 0;
	private int tail = 0;
	//Ԫ�صĸ���
	private int count = 0;
	
	@SuppressWarnings("unchecked")
	public BaseBoundedBuffer(int capacity) {
		this.buf = (V[]) new Object[capacity];
	}
	
	public synchronized final void doPut(V value) {
		buf[tail] = value;
		if (++ tail == buf.length) {
			tail = 0;
		}
		count ++;
	}
	
	public synchronized final V doGet() {
		V value = buf[head];
		//�����в�Ҫ�ٳ���Ԫ�ص�����
		buf[head] = null;
		if (++ head == buf.length) {
			head = 0;
		}
		-- count;
		return value;
	}
	
	public synchronized boolean isEmpty() {
		return count == 0;
	}
	
	public synchronized boolean isFull() {
		return count == buf.length;
	}
	
}
