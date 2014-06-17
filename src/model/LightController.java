package model;

import model.DataFactory.LightState;
import agent.Agent;
import agent.TimeServer;

public class LightController implements Agent {

	private double greenNS;
	private double yellowNS;
	private double greenEW;
	private double yellowEW;
	private TimeServer timeserver; 
	private LightState lightstate;
	private MP mp = MP.getProperties();
	
	//lightcontroller constructor
	public LightController() {
		greenNS = Math.random()*(mp.getGreenMax() - mp.getGreenMin()) + mp.getGreenMin();
		yellowNS = Math.random()*(mp.getYellowMax() - mp.getYellowMin()) + mp.getYellowMin();
		greenEW = Math.random()*(mp.getGreenMax() - mp.getGreenMin()) + mp.getGreenMin();
		yellowEW = Math.random()*(mp.getYellowMax() - mp.getYellowMin()) + mp.getYellowMin();
		timeserver = mp.getTimeServer();
		lightstate = LightState.YELLOW_NS;
	}
	
	//run method which will change states and enqueue the light time into the timeserver
	public void run(double time) {
		if (lightstate == LightState.GREEN_EW) {
			lightstate = LightState.YELLOW_EW;
			timeserver.enqueue(timeserver.currentTime() + yellowEW, this);
			return;
		}
		else if (lightstate == LightState.YELLOW_EW) {
			lightstate = LightState.GREEN_NS;
			timeserver.enqueue(timeserver.currentTime() + greenNS, this);
			return;
		}
		else if (lightstate == LightState.GREEN_NS) {
			lightstate = LightState.YELLOW_NS;
			timeserver.enqueue(timeserver.currentTime() + yellowNS, this);
			return;
		}
		else if (lightstate == LightState.YELLOW_NS) {
			lightstate = LightState.GREEN_EW;
			timeserver.enqueue(timeserver.currentTime() + greenEW, this);
			return;
		}
		else {
			lightstate = LightState.GREEN_EW;
			timeserver.enqueue(timeserver.currentTime() + greenEW, this);
			return;
		}
	}
	
	//all setters and getters below
	public LightState getLightState() {
		return lightstate;
	}
	
	public void setLightState(LightState state) {
		this.lightstate = state;
	}

}
