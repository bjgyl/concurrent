package chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import annotations.ThreadSafe;

/**
 * P89 5-19 ��Ч����������������հ汾
 * @author skywalker
 *
 * @param <V>
 * @param <A>
 */
@ThreadSafe
public class Memoizer<V, A> implements Computable<V, A>{
	
	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<V, A> computable;
	
	public Memoizer(Computable<V, A> computable) {
		this.computable = computable;
	}

	@Override
	public V compute(A arg) {
		//��ѭ����Ŀ��Ӧ�����׳��쳣���������
		while(true) {
			Future<V> future = cache.get(arg);
			if(future == null) {
				FutureTask<V> futureTask = new FutureTask<V>(new Callable<V>() {
					@Override
					public V call() throws Exception {
						return computable.compute(arg);
					}
				});
				//�ؼ�:ʹ��ԭ�ӵĲ���
				//��ʵ�����˫�ؼ�����˼
				future = cache.putIfAbsent(arg, futureTask);
				if(future == null) {
					//���֮ǰû��ֵ����ô����null(��old value)
					future = futureTask;
					futureTask.run();
				}
			}
			try {
				return future.get();
			} catch (CancellationException e) {
				//������㱻ȡ����Ӧ�ôӻ������Ƴ�
				cache.remove(arg, future);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
