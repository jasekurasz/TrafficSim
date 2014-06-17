package model;

import junit.framework.TestCase;
import model.DataFactory.Direction;

import org.junit.Assert;

public class SourceTEST extends TestCase{
	public SourceTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		Source s = new Source(Direction.EW);
		Road r1 = new Road();
		s.setNextRoad(r1);
		Assert.assertEquals(s.getNextRoad(), r1);
	}

}