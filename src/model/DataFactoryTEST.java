package model;

import junit.framework.TestCase;
import model.DataFactory.Direction;

import org.junit.Assert;

public class DataFactoryTEST extends TestCase{
	public DataFactoryTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		Assert.assertTrue(DataFactory.makeCar(Direction.EW) instanceof Car);
		Assert.assertTrue(DataFactory.makeRoad() instanceof Road);
		Assert.assertTrue(DataFactory.makeSink() instanceof Sink);
		Assert.assertTrue(DataFactory.makeSource(Direction.EW) instanceof Source);
	}

}