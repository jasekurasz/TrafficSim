package model;

import junit.framework.TestCase;
import model.DataFactory.LightState;

import org.junit.Assert;

public class LightControllerTEST extends TestCase{
	public LightControllerTEST(String name) {
		super(name);
	}
	
	public void testConstructorAndAttributes() {
		LightController lc = new LightController();
		Assert.assertEquals(lc.getLightState(), LightState.YELLOW_NS);
		lc.setLightState(LightState.GREEN_EW);
		Assert.assertEquals(lc.getLightState(), LightState.GREEN_EW);
	}

}