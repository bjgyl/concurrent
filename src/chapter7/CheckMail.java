package chapter7;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * P130 7-20 ��Ҫ��Ϊ�˼��һ��volatile boolean��AtomicBoolean������ʲô����
 * ��ʵ���в��õ�ԭ����ֻ��final�ſ������ξֲ�����!!!
 * Ҳ����û���ջ�:
 * 	a) java.util.concurrent.atomic������Ӳ����ʵ��ԭ�Ӳ�����(��ӳ��java����sun.misc.Unsafe)��������ȥ��
 * 		������������ģ����Ǵӱ��˲���ժ����:
 * 		��x86 ƽ̨�ϣ�CPU�ṩ����ָ��ִ���ڼ�����߼������ֶΡ�CPUоƬ����һ������#HLOCK pin�����������Եĳ�������һ��ָ��ǰ�����ǰ׺"LOCK"��
 * 		��������Ժ�Ļ��������ʹCPU��ִ������ָ���ʱ���#HLOCK pin�ĵ�λ���ͣ�����������ָ�����ʱ�ſ����Ӷ���������ס��
 * 		����ͬһ�����ϱ��CPU����ʱ����ͨ�����߷����ڴ��ˣ���֤������ָ���ڶദ���������е�ԭ���ԡ�
 *  b) ���嵽volatitle boolean��AtomicBoolean�����𣬵�һ����Ǻ���֧��һЩ���ж���ִ�е�ԭ�Ӳ�����ǰ��ֻ���ֶ��������ڶ���
 *  	�Ǵ�StackOverFlow(http://stackoverflow.com/questions/3786825/volatile-boolean-vs-atomicboolean)��ժ���ľ���:
 *  	I use volatile fields when said field is ONLY UPDATED by its owner thread and the value
 *  	is only read by other threads, you can think of it as a publish/subscribe scenario 
 *  	where there are many observers but only one publisher. 
 *  	However if those observers must perform some logic based on the value of the field 
 *  	and then push back a new value then I go with Atomic* vars or locks or synchronized blocks, 
 *  	whatever suits me best. In many concurrent scenarios it boils down to get the value, 
 *  	compare it with another one and update if necessary, hence the compareAndSet 
 *  	and getAndSet methods present in the Atomic* classes.
 * @author skywalker
 *
 */
public class CheckMail {
	
	public static void main(String[] args) throws InterruptedException {
		checkMail(Arrays.asList("Tom", "xsdwem7@hotmail.com"));
	}

	/**
	 * �˴��򻯴�������ַ����к���@����ô�ͷ���true
	 * @param hosts ����
	 * @throws InterruptedException 
	 */
	private static boolean checkMail(List<String> hosts) throws InterruptedException {
		final ExecutorService service = Executors.newCachedThreadPool();
		final AtomicBoolean hasMail = new AtomicBoolean(false);
		try {
			for (final String host : hosts) {
				service.execute(new Runnable() {
					@Override
					public void run() {
						if (host.indexOf("@") >= 0) {
							hasMail.set(true);
						}
					}
				});
			}
		} finally {
			service.shutdown();
			service.awaitTermination(10, TimeUnit.SECONDS);
		}
		return hasMail.get();
	}
	
}
