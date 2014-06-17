package model;

import java.util.Iterator;

import model.DataFactory.Direction;

import org.junit.Assert;

import junit.framework.TestCase;

public class RoadTEST extends TestCase {
	public RoadTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		Sink snk = new Sink();
		Road r1 = new Road();
		r1.setNextRoad(snk);
		Assert.assertNotNull(r1.getCars());
		Assert.assertEquals(r1.getCars().size(), 0);
		Assert.assertEquals(r1.getNextRoad(Direction.EW), snk);
		
	}
		
	public void testCarAcceptandRemove() {
		Road r2 = new Road();
		r2.setNextRoad(new Sink());
		Car c1 = new Car(Direction.EW);
		
		Assert.assertEquals(r2.getCars().size(), 0);
		r2.accept(c1, 0);
		Assert.assertEquals(r2.getCars().size(), 1);
		Iterator<Vehicle> iterator = r2.getCars().iterator();
		while (iterator.hasNext()) {
			Vehicle c = iterator.next();
			Assert.assertEquals(c.getCurrentRoad(), r2);
			Assert.assertEquals(c, c1);
		}
		r2.getCars().remove(c1);
		Assert.assertEquals(r2.getCars().size(), 0);		
	}
	
	public void testDistanceToObstacle() {
		Road r3 = new Road();
		r3.setNextRoad(new Sink());
		Vehicle c1 = new Car(Direction.EW);
		r3.accept(c1, r3.getEndPosition()/2);
		Assert.assertEquals(r3.getCars().size(), 1);
		Assert.assertEquals(c1.getCurrentRoad(), r3);
		Assert.assertTrue(c1.getfrontPosition() == r3.getEndPosition()/2);
		Assert.assertTrue(r3.distanceToObstacle(0, Direction.EW) == c1.getbackPosition());
		
	}
		
}
