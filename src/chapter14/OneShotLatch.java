package chapter14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import annotations.ThreadSafe;

/**
 * ʹ��AbstractQueuedSynchronizerʵ�ּ򵥵Ķ�Ԫ����
 * ���൱��һ������
 * @author skywalker
 *
 */
@ThreadSafe
public class OneShotLatch {
	
	private final Sync sync = new Sync();
	
	public void await() throws InterruptedException {
		sync.acquireInterruptibly(0);
	}
	
	public void signal() {
		sync.releaseShared(0);
	}

	/**
	 * java.util.concurrent�Ĺ���Ҳ��ʹ��������Ǽ̳еķ�ʽ��ʵ�ֵ�
	 */
	@SuppressWarnings("serial")
	private class Sync extends AbstractQueuedSynchronizer {
		@Override
		protected int tryAcquireShared(int arg) {
			return getState() == 1 ? 1 : -1;
		}
		@Override
		protected boolean tryReleaseShared(int arg) {
			//�򿪱���
			setState(1);
			//���������߳̿��Ի�ȡ����
			return true;
		}
	}
	
}
