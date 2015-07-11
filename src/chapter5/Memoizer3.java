package chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * P88 5-18 ��Ч�������������������汾
 * �˰汾��Ҫ����˵ڶ����̲߳�֪����һ���߳����ڼ��������
 * @author skywalker
 *
 * @param <V>
 * @param <A>
 */
public class Memoizer3<V, A> implements Computable<V, A> {
	
	//���ﻺ���ֵ�����ǹؼ�
	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<V, A> computable;
	
	public Memoizer3(Computable<V, A> computable) {
		this.computable = computable;
	}

	/**
	 * ������һ������:
	 * �ȼ���ִ�е�������Ȼû�н��
	 */
	@Override
	public V compute(A arg) {
		Future<V> future = cache.get(arg);
		if(future == null) {
			FutureTask<V> futureTask = new FutureTask<V>(new Callable<V>() {
				@Override
				public V call() throws Exception {
					return computable.compute(arg);
				}
			});
			//FutureTaskʵ����Future�ӿ�
			cache.put(arg, futureTask);
			future = futureTask;
			//��ʼ����
			futureTask.run();
		}
		try {
			//�����һ���߳��ڼ��㣬��ô�����ȴ�
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
