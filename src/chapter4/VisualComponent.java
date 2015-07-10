package chapter4;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

import annotations.ThreadSafe;

/**
 * ���ӻ����
 * ���̰߳�ȫ��ί�и�����̰߳�ȫ�ı�����ֻҪ
 * ��Щ�����Ǳ˴˶����ģ�����϶��ɵ��ಢ���������а����Ķ��״̬�����������κβ���������
 * @author skywalker
 *
 */
@ThreadSafe
public class VisualComponent {

	//��������
	private final CopyOnWriteArrayList<MouseListener> mouseListeners = new CopyOnWriteArrayList<MouseListener>();
	//���̼�����
	private final CopyOnWriteArrayList<KeyListener> keyListeners = new CopyOnWriteArrayList<KeyListener>();
	
	public void addMouseListener(MouseListener mouseListener) {
		mouseListeners.add(mouseListener);
	}
	
	public void removeMouseListener(MouseListener mouseListener) {
		mouseListeners.remove(mouseListener);
	}
	
	public void addKeyListener(KeyListener keyListener) {
		keyListeners.add(keyListener);
	}
	
	public void removeKeyListener(KeyListener keyListener) {
		keyListeners.remove(keyListener);
	}
	
}
