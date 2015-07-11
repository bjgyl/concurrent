package chapter5;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * P87 5-17��Ч�������������ڶ����汾
 * ����ConcurrentHashMapʵ��
 * @author skywalker
 *
 * @param <V> �������
 * @param <A> ��������
 */
public class Memoizer2<V, A> implements Computable<V, A> {
	
	private final ConcurrentMap<A, V> cache = new ConcurrentHashMap<A, V>();
	private final Computable<V, A> computable;
	
	public Memoizer2(Computable<V, A> computable) {
		this.computable = computable;
	}

	/**
	 * �˰汾����ڵ�һ���汾��ȥ����ͬ������������ܣ����ǻ��ǻ�����ظ���������⣬ԭ��:
	 * a) ��������ʱ��Ƚϳ�����һ���߳����ڼ��㣬���ǵڶ����̲߳�֪����һ���߳����ڼ��㣬���Ի��ǻ����
	 * b) ���ǵ��͵��ȼ���ִ�еĴ��룬�㶮�� 
	 */
	@Override
	public V compute(A arg) {
		V result = cache.get(arg);
		if(result == null) {
			result = computable.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
