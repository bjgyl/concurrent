package chapter14;

/**
 * P243 14-6 ʹ����������ʵ�ֵ��н绺��--�ռ���
 * "����������һ��"�ļ��Ӹ�
 * ��ʵ�����ԸĽ���������notifyAll()��������Ӧ�û��������߳�
 * @author skywalker
 *
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public BoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public synchronized void put(V value) throws InterruptedException {
		while (isFull()) {
			wait();
		}
		doPut(value);
		notifyAll();
	}
	
	public synchronized V get() throws InterruptedException {
		while (isEmpty()) {
			wait();
		}
		V value = doGet();
		notifyAll();
		return value;
	}

}
