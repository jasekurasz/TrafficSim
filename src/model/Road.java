package model;

import java.util.HashSet;
import java.util.Set;
import model.DataFactory.Direction;

final class Road implements CarAcceptor {

	private Set<Vehicle> cars;
	private CarReleaser nextRoad;
	double endPosition;
	private MP mp = MP.getProperties();
	
	public MP getMP() {
		return this.mp;
	}
	
	//Road constructor
	public Road() {
		endPosition = Math.random()*(mp.getRoadLengthMax() - mp.getRoadLengthMin()) + mp.getRoadLengthMin();
		cars = new HashSet<Vehicle>();
	}
	
	//acceptor for the road that will add cars to current road or set then to the next one
	public boolean accept(Vehicle c, double frontPosition) {
		if (!cars.isEmpty())
			cars.remove(c);
		if (frontPosition > endPosition) {
			return nextRoad.accept(c, frontPosition - endPosition);
		}else {
			c.setCurrentRoad(this);
			c.setfrontPosition(frontPosition);
			cars.add(c);
			return true;
		}
	}
	
	//checks position of cars behind
	private double distanceToCarBack(double fromPosition) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Vehicle c : cars) {
			if (c.getbackPosition() >= fromPosition && c.getbackPosition() < carBackPosition)
				carBackPosition = c.getbackPosition();
		}
		return carBackPosition;
	}
	
	//checks distance to the nearest obstacle
	public double distanceToObstacle(double fromPosition, Direction d) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY) {
			double distanceToEnd = this.endPosition - fromPosition;
			obstaclePosition = nextRoad.distanceToObstacle(0, d) + distanceToEnd;
			return obstaclePosition;
		}
		return obstaclePosition - fromPosition;
	}
	
	//remove the car from this road
	public boolean remove(Vehicle c) {
		if (cars.contains(c)) {
			cars.remove(c);
			return true;
		}
		else {
			return false;
		}
	}
	
	//all setters and getters below
	public void setNextRoad(CarReleaser road) {
		this.nextRoad = road;
	}
	
	public CarReleaser getNextRoad(Direction d) {
		return this.nextRoad;
	}

	public double getEndPosition() {
		return this.endPosition;
	}

	public Set<Vehicle> getCars() {
		return cars;
	}
}
