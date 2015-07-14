package chapter8;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * P146 8-7 �Զ����߳�
 * @author skywalker
 *
 */
public class MyAppThread extends Thread {

	public static final String DEFAULTNAME = "MyAppThread";
	private static volatile boolean debugLifeCycle = false;
	//���������̵߳�����
	private static final AtomicInteger created = new AtomicInteger();
	//���ŵ��̵߳�����
	private static final AtomicInteger alive = new AtomicInteger();
	private static final Logger logger = Logger.getAnonymousLogger();
	
	public MyAppThread(Runnable runnable) {
		this(runnable, DEFAULTNAME);
	}
	
	public MyAppThread(Runnable runnable, String name) {
		super(runnable, name);
		created.getAndIncrement();
		setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.log(Level.SEVERE, "UNCAUGHT in thread " + getName(), e);
			}
		});
	}
	
	@Override
	public void run() {
		//����һ�ݣ�����һ���ԣ�����Ƚϵ�
		boolean debug = debugLifeCycle;
		if (debug) {
			logger.log(Level.FINE, getName() + "created");
		}
		try {
			alive.getAndIncrement();
			super.run();
		} finally {
			alive.getAndDecrement();
			if (debug) {
				logger.log(Level.FINE, getName() + " exited");
			}
		}
	}
	
}
