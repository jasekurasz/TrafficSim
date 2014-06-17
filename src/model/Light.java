package model;

import java.util.HashSet;
import java.util.Set;

import model.DataFactory.Direction;
import model.DataFactory.LightState;

final class Light implements CarReleaser {

	private CarAcceptor nextRoadEW;
	private CarAcceptor nextRoadNS;
	private Set<Vehicle> carsEW;
	private Set<Vehicle> carsNS;
	private LightController lightcontroller;
	private double intLength;
	MP mp = MP.getProperties();
	
	//Light constructor
	public Light() {
		intLength = Math.random()*(mp.getIntLengthMax() - mp.getIntLengthMin()) + mp.getIntLengthMin();
		carsEW = new HashSet<Vehicle>();
		carsNS = new HashSet<Vehicle>();
		lightcontroller = new LightController();
	}
	
	//acceptor for the intersection based on direction car is going
	public boolean accept(Vehicle c, double frontPosition) {
		if (c.getDirection() == Direction.EW) {
			if (!carsEW.isEmpty())
				carsEW.remove(c);
		}
		else {
			if(!carsNS.isEmpty())
				carsNS.remove(c);
		}
		if (c.getDirection() == Direction.EW){
			if (frontPosition > intLength && distanceToObstacle(frontPosition, c.getDirection()) != 0) {
				return nextRoadEW.accept(c, frontPosition - intLength);
			}else {
				c.setCurrentIntersection(this);
				c.setfrontPosition(frontPosition);
			    carsEW.add(c);
				return true;
			}
		}
		else {
			if (frontPosition > intLength && distanceToObstacle(frontPosition, c.getDirection()) != 0) {
				return nextRoadNS.accept(c, frontPosition - intLength);
			}else {
				c.setCurrentIntersection(this);
				c.setfrontPosition(frontPosition);
			    carsNS.add(c);
				return true;
			}
		}	
	}
	
	//checks distance to the cars behind (might need some work to get working properly)
	private double distanceToCarBack(double fromPosition, Set<Vehicle> cars) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Vehicle c : cars) {
			if (c.getbackPosition() >= fromPosition && c.getbackPosition() < carBackPosition)
				carBackPosition = c.getbackPosition();
		}
		return carBackPosition;
	}
	
	//checks distance to obstacle (might need some work to get working properly)
	public double distanceToObstacle(double fromPosition, Direction d) {
		if (d == Direction.EW){
			if (lightcontroller.getLightState() == LightState.GREEN_EW || lightcontroller.getLightState() == LightState.YELLOW_EW) {
				double obstaclePosition = this.distanceToCarBack(fromPosition, carsEW);
				if (obstaclePosition == Double.POSITIVE_INFINITY) {
					double distanceToEnd = this.intLength - fromPosition;
					obstaclePosition = nextRoadEW.distanceToObstacle(0, Direction.EW) + distanceToEnd;
					return obstaclePosition;
				}
				return obstaclePosition - fromPosition;
			} else return 0;
		} 
		else {
			if (lightcontroller.getLightState() == LightState.GREEN_NS || lightcontroller.getLightState() == LightState.YELLOW_NS) {
				double obstaclePosition = this.distanceToCarBack(fromPosition, carsNS);
				if (obstaclePosition == Double.POSITIVE_INFINITY) {
					double distanceToEnd = this.intLength - fromPosition;
					obstaclePosition = nextRoadNS.distanceToObstacle(0, Direction.NS) + distanceToEnd;
					return obstaclePosition;
				}
				return obstaclePosition - fromPosition;
			} else return 0;
		}		
	}
	
	//remove a vehicle from the intersection
	public boolean remove(Vehicle c) {
		if (c.getDirection() == Direction.EW){
			if (carsEW.contains(c)) {
				carsEW.remove(c);
				return true;
			} else return false;
		}
		else {
			if (carsNS.contains(c)) {
				carsNS.remove(c);
				return true;
			} else return false;
		}
	}
	
	//all setters and getters below
	public LightController getLight() {
		return this.lightcontroller;
	}
	
	public double getEndPosition() {
		return this.intLength;
	}
	
	public void setNextRoad(CarAcceptor road, Direction d) {
		if (d == Direction.EW)
			this.nextRoadEW = road;
		else this.nextRoadNS = road;
	}
	
	public CarAcceptor getNextRoad(Direction d) {
		if (d == Direction.EW)
			return this.nextRoadEW;
		else return this.nextRoadNS;
	}

	public Set<Vehicle> getCarsEW() {
		return carsEW;
	}
	
	public Set<Vehicle> getCarsNS() {
		return carsNS;
	}

}
