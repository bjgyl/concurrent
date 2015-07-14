package chapter8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * P149 8-9 �Զ���ThreadPoolExecutor����Ӽ�ʱͳ��
 * @author skywalker
 *
 */
public class TimingThreadPool extends ThreadPoolExecutor {
	
	//ע��Ĭ�ϵļ�����INFO����������������־����ʾ
	private static final Logger logger = Logger.getLogger("TimingPoolExecutor");
	//��¼һ���̵߳Ŀ�ʼʱ�䣬�Ա���afterExecution()��ʹ��
	private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	//���е���������
	private static final AtomicInteger taskNum = new AtomicInteger();
	//���������ܵ�����ʱ��
	private static final AtomicLong totalTime = new AtomicLong();

	public TimingThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		logger.log(Level.INFO, "Thread " + t.getName() + " start");
		startTime.set(System.nanoTime());
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			logger.log(Level.INFO, String.format("Thread %s: end %s, time=%dns", t, r, taskTime));
			taskNum.getAndIncrement();
			totalTime.addAndGet(taskTime);
		} finally {
			super.afterExecute(r, t);
		}
	}
	
	@Override
	protected void terminated() {
		try {
			logger.info(String.format("Service terminted, ������ %d ������, ����ʱ %dns", taskNum.get(), totalTime.get()));
		} finally {
			super.terminated();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService service = new TimingThreadPool(0, 20, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};
		service.submit(task);
		service.submit(task);
		service.submit(task);
		service.shutdown();
	}

}
