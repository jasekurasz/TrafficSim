package model;

import model.DataFactory.Direction;
import agent.Agent;
import agent.TimeServer;

final class Source implements Agent, CarSource {

	private Direction direction;
	private CarAcceptor firstRoad;
	private TimeServer timeserver;
	private double carCreationInterval;
	private MP mp = MP.getProperties();

	//source constructor
	public Source(Direction d) {
		carCreationInterval = Math.random()*(mp.getEntryRateMax() - mp.getEntryRateMin()) + mp.getEntryRateMin();
		timeserver = mp.getTimeServer();
		timeserver.enqueue(timeserver.currentTime(), this);
		direction = d;
	}
	
	//run method which will create a new car if there is room for it to be put on the road and enqueue it to timeserver
	public void run(double time) {
		boolean canGo = true;
		Vehicle car = DataFactory.makeCar(this.direction);
		for (Vehicle c : firstRoad.getCars()) {
			if (c.getfrontPosition() <= c.getlength() + c.getbrakeDistance())
				canGo = false;
		}
		if (canGo == true) {
			firstRoad.accept(car, 0);
			timeserver.enqueue(timeserver.currentTime(), (Agent) car);
		}
		timeserver.enqueue(timeserver.currentTime() + carCreationInterval, this);
	}
	
	//all setters and getters below
	public void setNextRoad(CarAcceptor r) {
		this.firstRoad = r;
	}
	
	public CarAcceptor getNextRoad() {
		return firstRoad;
	}
}
