package chapter7;

import java.util.concurrent.TimeUnit;

/**
 * ����P119 7-8��˼·
 * ����һ���߳�������ò�ƻ��̵�RuntimeException
 * �����߳����׳�RuntimeException����̨���ǻ��ӡ����ջ��Ϣ(ò���Ƿϻ�...)
 * �����е�"�̵�"Ӧ��ָ�����޷������������
 * ���ֽ����ʽ�е����������ĸо�
 * @author skywalker
 *
 */
public class SwallowException {

	//���н��:ArithmeticException
	public static void main(String[] args) {
		Thread workThread = Thread.currentThread();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
					workThread.interrupt();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}).start();
		try {
			//��main�߳�����������
			System.out.println(5 / 0);
		} catch (RuntimeException e) {
			System.out.println(e.getClass().getSimpleName());
		}
	}
	
}
