package model;

import model.DataFactory.Direction;

import org.junit.Assert;

import junit.framework.TestCase;

public class CarTEST extends TestCase {
	public CarTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		MP mp = MP.getProperties();
		Car c = new Car(Direction.EW);
		Assert.assertTrue(c.getlength() >= mp.getCarLengthMin() && c.getlength() <= mp.getCarLengthMax());
		Assert.assertTrue(c.getmaxVelocity() >= mp.getVelocityMin() && c.getmaxVelocity() <= mp.getVelocityMax());
		Assert.assertTrue(c.getstopDistance() >= mp.getStopDistMin() && c.getstopDistance() <= mp.getStopDistMax());
		Assert.assertTrue(c.getbrakeDistance() >= mp.getBrakeDistMin() && c.getbrakeDistance() <= mp.getBrakeDistMax());
	}
}
