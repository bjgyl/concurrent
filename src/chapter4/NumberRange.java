package chapter4;

import java.util.concurrent.atomic.AtomicInteger;

import annotations.ThreadNotSafe;

/**
 * 4.3.3��ί��ʧЧʱ
 * ʧЧ��ԭ����������̰߳�ȫ�ı���֮������������ϵ
 * ����������Ǽ���
 * @author skywalker
 *
 */
@ThreadNotSafe
public class NumberRange {

	//Լ��������upper > lower
	private final AtomicInteger upper = new AtomicInteger();
	private final AtomicInteger lower = new AtomicInteger();
	
	public void setUpper(int i) {
		//�ȼ���ִ�У��������Դ
		if(i > lower.get()) {
			upper.set(i);
		}
	}
	
	public void setLower(int i) {
		//����ԭ��ͬ��
		if(i < upper.get()) {
			lower.set(i);
		}
	}
	
}
