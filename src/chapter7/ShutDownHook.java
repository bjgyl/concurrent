package chapter7;

/**
 * P136 7-26 JVM�رչ���
 * ���йرչ����ǲ���ִ�е�,����ǰ����еĽ�������ŵ�һ���������洮��ִ��
 * @author skywalker
 *
 */
public class ShutDownHook {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				//XXX�ɵ㻵�¶�
			}
		});
	}
	
}
