package chapter14;

import annotations.ThreadSafe;

/**
 * P240 14-3 ������������ʱ������ִ����Ӧ�Ĳ���
 * ��������ȱ����ǵ����������д�������ʧ�ܵ����
 * @author skywalker
 *
 */
@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public GrumpyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public synchronized void put(V value) throws BufferFullException {
		if (isFull()) {
			throw new BufferFullException();
		}
		doPut(value);
	}
	
	public synchronized V get() throws BufferEmptyException {
		if (isEmpty()) {
			throw new BufferEmptyException();
		}
		return doGet();
	}
	
	/**
	 * �����ǵ��õ�ʾ��:
	 */
	public static void main(String[] args) {
		GrumpyBoundedBuffer<String> buffer = new GrumpyBoundedBuffer<String>(10);
		while (true) {
			try {
				buffer.get();
				//�ɵ㻵�¶�
				break;
			} catch (BufferEmptyException e) {
				//�˴�������ѡ��
				//a) ɶҲ���ɣ�ֱ�����ԣ�����������ᵼ��CPUʱ���˷�
				//b) ˯һ������ᵼ�µ���Ӧ��
				//c) Thread.yeild()��ȱ�����JVM��������������Ͳ�֪����
			}
		}
	}
	
}
