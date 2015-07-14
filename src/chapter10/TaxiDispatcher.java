package chapter10;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import annotations.DeadLock;

/**
 * P174 10-5 ���⳵�ͳ��⳵������
 * setLocation()��getImage()���ܻ��������
 * ��Э������֮�䷢������
 * @author skywalker
 *
 */
@DeadLock
public class TaxiDispatcher {

	private final Set<Taxi> taxis;
	private final Set<Taxi> availableTaxis;
	
	public TaxiDispatcher() {
		this.taxis = new HashSet<Taxi>();
		this.availableTaxis = new HashSet<Taxi>();
	}
	
	/**
	 * ���ѿ��õĳ��⳵
	 */
	public synchronized void notifyIfAvailable(Taxi taxi) {
		availableTaxis.add(taxi);
	}
	
	/**
	 * ���Ƴ��⳵λ��ͼ
	 */
	public synchronized void getIamge() {
		for (Taxi taxi : taxis) {
			taxi.getLocation();
		}
	}
	
}

class Taxi {
	
	private Point location, destination;
	private final TaxiDispatcher dispatcher;
	
	public Taxi(TaxiDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public synchronized Point getLocation() {
		return location;
	}
	
	public synchronized void setLocation(Point location) {
		this.location = location;
		if (location.equals(destination)) {
			dispatcher.notifyIfAvailable(this);
		}
	}
  	
}
