package chapter7;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * P130 7-21 ���shutDownNow()�ı׶�---ֻ�ܷ���û��ִ�е����񣬵����޷����ر�ȡ��������
 * ����װ��ģʽ��
 * @author skywalker
 *
 */
public class TrackingExecutor extends AbstractExecutorService {

	private final ExecutorService service;
	private final Set<Runnable> cancelledTasks = Collections.synchronizedSet(new HashSet<Runnable>());
	
	public TrackingExecutor(ExecutorService service) {
		this.service = service;
	}
	
	public Set<Runnable> getCanceledTasks() {
		//ֻ���ս��˲ſ���ִ�д˹���
		if (!isShutdown()) {
			throw new IllegalStateException();
		}
		//��Ҫ����
		return new HashSet<Runnable>(cancelledTasks);
	}
	
	@Override
	public void execute(Runnable command) {
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					command.run();
				} finally {
					if (isShutdown() && Thread.currentThread().isInterrupted()) {
						cancelledTasks.add(command);
					}
				}
			}
		});
	}

	//���¶���ί�и�service
	@Override
	public void shutdown() {
		service.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return service.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return service.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return service.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException {
		return service.awaitTermination(timeout, unit);
	}
	
	public static void main(String[] args) throws InterruptedException {
		//ÿ��ֻ��ִ��һ������
		TrackingExecutor trackingExecutor = new TrackingExecutor(Executors.newFixedThreadPool(3));
		trackingExecutor.submit(new Reset.Task());
		trackingExecutor.submit(new Reset.Task());
		trackingExecutor.submit(new Reset.Task());
		trackingExecutor.submit(new Reset.Task());
		trackingExecutor.submit(new Reset.Task());
		TimeUnit.SECONDS.sleep(2);
		int unPerformed = trackingExecutor.shutdownNow().size();
		System.out.println(unPerformed + "��δ��ִ��");
		System.out.println(trackingExecutor.getCanceledTasks().size() + "��ȡ��");
	}

}
