package model;

import java.util.Iterator;

import model.DataFactory.Direction;

import org.junit.Assert;

import junit.framework.TestCase;

public class LightTEST extends TestCase {
	public LightTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		Light l1 = new Light();
		Road r1 = new Road();
		l1.setNextRoad(r1, Direction.EW);
		Assert.assertNotNull(l1.getCarsEW());
		Assert.assertEquals(l1.getCarsEW().size(), 0);
		Assert.assertEquals(l1.getNextRoad(Direction.EW), r1);
		
	}
		
	public void testCarAcceptandRemove() {
		Light l2 = new Light();
		l2.setNextRoad(new Road(), Direction.EW);
		Car c1 = new Car(Direction.EW);
		
		Assert.assertEquals(l2.getCarsEW().size(), 0);
		l2.accept(c1, 0);
		Assert.assertEquals(l2.getCarsEW().size(), 1);
		Iterator<Vehicle> iterator = l2.getCarsEW().iterator();
		while (iterator.hasNext()) {
			Vehicle c = iterator.next();
			Assert.assertEquals(c.getCurrentInt(), l2);
			Assert.assertEquals(c, c1);
		}
		l2.getCarsEW().remove(c1);
		Assert.assertEquals(l2.getCarsEW().size(), 0);		
	}
	
	public void testDistanceToObstacle() {
		Light l3 = new Light();
		l3.setNextRoad(new Road(), Direction.EW);
		Car c1 = new Car(Direction.EW);
		l3.accept(c1, l3.getEndPosition()/2);
		Assert.assertEquals(l3.getCarsEW().size(), 1);
		Assert.assertEquals(c1.getCurrentInt(), l3);
		Assert.assertTrue(c1.getfrontPosition() == l3.getEndPosition()/2);
		System.out.println(l3.getLight().getLightState());
		Assert.assertTrue(l3.distanceToObstacle(0, Direction.EW) == 0);
		
	}
		
}