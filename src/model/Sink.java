package model;

import model.DataFactory.Direction;

final class Sink implements CarReleaser {
	
	//sink constructor
	public Sink() {}

	//always accept a car
	public boolean accept(Vehicle c, double frontPosition) {
		return true;
	}
	
	
	public double getEndPosition() {
		return Double.POSITIVE_INFINITY;
	}

	public boolean remove(Vehicle car) {
		throw new UnsupportedOperationException();
	}
	
	public double distanceToObstacle(double fromPositon, Direction d) {
		return Double.POSITIVE_INFINITY;
	}

	public void setNextRoad(CarAcceptor car, Direction d) {
		throw new UnsupportedOperationException();
	}

	public CarAcceptor getNextRoad(Direction d) {
		throw new UnsupportedOperationException();
	}

	public LightController getLight() {
		throw new UnsupportedOperationException();
	}
}
