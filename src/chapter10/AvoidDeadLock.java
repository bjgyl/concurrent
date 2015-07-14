package chapter10;

/**
 * P172 10-3 ʹ�ù̶���˳���Ա�������
 * ����������߳�ͬʱ���෴��˳��ת��ǰ����ô���ܻ�����
 * @author skywalker
 *
 */
public class AvoidDeadLock {
	
	//"��ʱ��"�������������ĵ��ɵ�ַ���������hashCode��һ�µģ���ôʹ�������
	private static final Object tieLock = new Object();
	
	/**
	 * ��money��fromת�Ƶ�to
	 */
	public void transfer(final Account from, final Account to, int money) {
		
		/**
		 * �ֲ���
		 * ���������̫�����ˣ����������ķ�������ʵ�ִ��ֹ�����?
		 * @author skywalker
		 *
		 */
		class Helper {
			public void transfer() {
				if (from.getBalance() < money) {
					throw new IllegalArgumentException();
				}
				from.debit(money);
				to.credit(money);
			}
		}
		
		//���������˻���Ĭ��hashCode
		int fromHash = System.identityHashCode(from);
		int toHash = System.identityHashCode(to);
		//�����Ȼ�ȡhash��С�ĵ���
		if (fromHash < toHash) {
			synchronized (from) {
				synchronized (to) {
					new Helper().transfer();
				}
			}
		} else if (fromHash > toHash) {
			synchronized (to) {
				synchronized (from) {
					new Helper().transfer();
				}
			}
		} else {
			synchronized (tieLock) {
				synchronized (from) {
					synchronized (to) {
						new Helper().transfer();
					}
				}
			}
		}
	}

	//�˻�
	private static class Account {
		
		//��ȡ���
		public synchronized int getBalance() {
			return 10;
		}
		
		//ȡ��
		public synchronized void debit(int money) {}
		
		//���
		public synchronized void credit(int money) {}
		
	}
	
}
