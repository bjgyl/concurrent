package chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * P81 5-12
 * ʹ��FutureTaskԤ����
 * @author skywalker
 *
 */
public class Preloader {

	private final FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
		@Override
		public String call() throws Exception {
			return "������δ��";
		}
	});
	
	public void start() {
		//��ΪFutureTaskʵ����RunnableFuture�ӿڣ����˽ӿڼ̳���Runnable��Future
		new Thread(future).start();
	}
	
	public String get() throws InterruptedException, ExecutionException {
		return future.get();
	}
	
}
