package model;

import junit.framework.TestCase;
import model.DataFactory.Direction;

import org.junit.Assert;

public class SinkTEST extends TestCase{
	public SinkTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		Sink s1 = new Sink();
		Car c1 = new Car(Direction.EW);
		Assert.assertTrue(s1.accept(c1, 10.0));
		Assert.assertTrue(s1.distanceToObstacle(0, Direction.EW) == Double.POSITIVE_INFINITY);
		Assert.assertTrue(s1.getEndPosition() == Double.POSITIVE_INFINITY);
	}

}
