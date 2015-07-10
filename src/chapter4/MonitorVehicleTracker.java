package chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import annotations.ThreadSafe;

/**
 * 52ҳ���ڼ�����ģʽ�ĳ���׷��
 * @author skywalker
 *
 */
@ThreadSafe
public class MonitorVehicleTracker {

	//�˱������������
	private Map<String, MutablePoint> locations;
	
	public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
		this.locations = deepCopy(locations);
	}
	
	public synchronized Map<String, MutablePoint> getLocations() {
		return deepCopy(locations);
	}
	
	public synchronized MutablePoint getLocation(String id) {
		MutablePoint point = locations.get(id);
		return point == null ? null : new MutablePoint(point);
	}
	
	public synchronized void setLocation(String id, int x, int y) {
		MutablePoint point = locations.get(id);
		if(point == null) {
			throw new IllegalArgumentException("The id is not exists");
		}
		point.x = x;
		point.y = y;
	}

	/**
	 * ��ȿ���map
	 */
	private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> locations) {
		Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
		for(String id : locations.keySet()) {
			result.put(id, new MutablePoint(locations.get(id)));
		}
		return Collections.unmodifiableMap(result);
	}
	
}
