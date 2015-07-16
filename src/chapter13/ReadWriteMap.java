package chapter13;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import annotations.ThreadSafe;

/**
 * P236 13-7 �򵥵Ķ�дMap
 * ���ֻ����Ҫһ��ͬ����map,��ôConcurrentHashMap��ȫ�㹻��
 * �����Ҫһ���̰߳�ȫ��LinkedHashMap����������˼·��һ��
 * @author skywalker
 *
 */
@ThreadSafe
public class ReadWriteMap<K, V> {

	private final Map<K, V> map;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();
	
	public ReadWriteMap(Map<K, V> map) {
		this.map = map;
	}
	
	public V get(K key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}
	
	public void put(K key, V value) {
		writeLock.lock();
		try {
			map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}
	
}
