package chapter4;

/**
 * �ɱ��࣬������java.awt.Point
 * @author skywalker
 *
 */
public class MutablePoint {

	public int x, y;
	
	public MutablePoint(MutablePoint m) {
		this.x = m.x;
		this.y = m.y;
	}
	
}
