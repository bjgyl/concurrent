package chapter4;

import java.util.List;

/**
 * 4.4.2���
 * Ϊ��ͨ��list���һ��ԭ�ӵ��������������ӵĲ���
 * ʹ����ϵķ�ʽ�̰߳�ȫ����չһ���࣬��Ҳ����õķ�ʽ
 * @author skywalker
 *
 */
public class ImprovedList<T> {

	//����ʵҲ����java�ļ�����ģʽ�����뱣֤����ӵ��list��Ψһ����
	private final List<T> list;
	
	public ImprovedList(List<T> list) {
		this.list = list;
	}
	
	public synchronized void addIfAbsent(T t) {
		if(!list.contains(t)) {
			list.add(t);
		}
	}
	
}
