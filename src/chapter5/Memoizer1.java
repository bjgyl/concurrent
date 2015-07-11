package chapter5;

import java.util.HashMap;
import java.util.Map;

import annotations.ThreadSafe;

/**
 * P86 5-16��Ч��������������һ���汾
 * @author skywalker
 *
 */
@ThreadSafe
public class Memoizer1<V, A> implements Computable<V, A> {

	private final Map<A, V> cache = new HashMap<A, V>();
	//���Լ�����������������û�У���ô�ᱻ����
	private final Computable<V, A> computable;
	
	public Memoizer1(Computable<V, A> computable) {
		this.computable = computable;
	}

	/**
	 * ��򵥴ֱ����̰߳�ȫ
	 * ������������ʱ��ܳ��������������粻�ӻ���
	 */
	@Override
	public synchronized V compute(A arg) {
		V result = cache.get(arg);
		if(result == null) {
			result = computable.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
