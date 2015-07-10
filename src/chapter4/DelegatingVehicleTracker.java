package chapter4;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import annotations.ThreadSafe;

/**
 * �̰߳�ȫ��ί��ʵ�ֵĳ���׷����
 * ��Ϊ�������locations���������������̰߳�ȫ�ģ�����������Ҳ���̰߳�ȫ��
 * @author skywalker
 *
 */
@ThreadSafe
public class DelegatingVehicleTracker {

	private final ConcurrentMap<String, Point> locations;
	//λ�õ�һ�����ɱ���ͼ
	private final Map<String, Point> unmodifiableMap;
	
	public DelegatingVehicleTracker(Map<String, Point> locations) {
		this.locations = new ConcurrentHashMap<String, Point>(locations);
		this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
	}
	
	public Point getLocation(String id) {
		return locations.get(id);
	}
	
	/**
	 * ���ص��ǿ��Ը���locationsʵʱ�仯����ͼ
	 */
	public Map<String, Point> getLocations() {
		return unmodifiableMap;
	}
	
	public void setLocation(String id, int x, int y) {
		if(locations.replace(id, new Point(x, y)) == null) {
			throw new IllegalArgumentException("not exists");
		}
	} 
	
}
