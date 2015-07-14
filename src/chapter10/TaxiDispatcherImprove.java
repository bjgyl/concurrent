package chapter10;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import annotations.NotDeadLock;

/**
 * P176 10-6 ʹ�ÿ��ŵ��õķ�ʽ�������
 * @author skywalker
 *
 */
@NotDeadLock
public class TaxiDispatcherImprove {

	private final Set<TaxiImproved> taxis;
	private final Set<TaxiImproved> availableTaxis;
	
	public TaxiDispatcherImprove() {
		this.taxis = new HashSet<TaxiImproved>();
		this.availableTaxis = new HashSet<TaxiImproved>();
	}
	
	/**
	 * ���ѿ��õĳ��⳵
	 */
	public synchronized void notifyIfAvailable(TaxiImproved taxi) {
		availableTaxis.add(taxi);
	}
	
	/**
	 * ��Ϊ���ŵ���
	 * ���Ƴ��⳵λ��ͼ
	 * �����������ʵ�˷��������巢���˱仯��֮ǰ�Ǹ��汾�ǻ���һ�����ն��˰汾��ʵʱ�仯��
	 * ǰ���ǿ��Խ��������ı仯
	 */
	public void getIamge() {
		Set<TaxiImproved> copy;
		synchronized (this) {
			copy = new HashSet<TaxiImproved>(taxis);
		}
		for (TaxiImproved taxi : copy) {
			taxi.getLocation();
		}
	}
	
}

class TaxiImproved {
	
	private Point location, destination;
	private final TaxiDispatcherImprove dispatcher;
	
	public TaxiImproved(TaxiDispatcherImprove dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public synchronized Point getLocation() {
		return location;
	}
	
	/**
	 * ��Ϊ���ŵ���
	 * ��������ǰ�����޸�λ�ú�֪ͨdispatcher���ò���Ҫ��ԭ�Ӳ���
	 */
	public void setLocation(Point location) {
		boolean reachDestination = false;
		synchronized (this) {
			this.location = location;
			reachDestination = location.equals(destination);
		}
		if (reachDestination) {
			dispatcher.notifyIfAvailable(this);
		}
	}
  	
}
