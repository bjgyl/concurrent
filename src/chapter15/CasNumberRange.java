package chapter15;

import java.util.concurrent.atomic.AtomicReference;

import annotations.ThreadSafe;

/**
 * P267 15-3 ͨ��CASά�ְ�����������Ĳ���������
 * @author skywalker
 *
 */
@ThreadSafe
public class CasNumberRange {

	private static class IntPair {
		
		private final int upper;
		private final int lower;
		
		public IntPair(int upper, int lower) {
			this.upper = upper;
			this.lower = lower;
		}
		
	}
	
	private final AtomicReference<IntPair> reference = new AtomicReference<IntPair>(new IntPair(0, 0));
	
	public int getLower() {
		return reference.get().lower;
	}
	
	public int getUpper() {
		return reference.get().upper;
	}
	
	public void setUpper(int upper) {
		while (true) {
			IntPair pair = reference.get();
			if (upper < pair.lower) {
				throw new IllegalArgumentException();
			}
			//������������㣬˵���Ѿ�����һ���߳��޸��ˣ���ô����
			if (reference.compareAndSet(pair, new IntPair(upper, pair.lower))) {
				return;
			}
		}
	}
	
	public void setLower(int lower) {
		while (true) {
			IntPair pair = reference.get();
			if (lower > pair.upper) {
				throw new IllegalArgumentException();
			}
			if (reference.compareAndSet(pair, new IntPair(pair.upper, lower))) {
				return;
			}
		}
	}
	
}
