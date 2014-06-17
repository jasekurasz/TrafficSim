package model;

import model.DataFactory.Direction;

public interface CarReleaser {
	public boolean accept(Vehicle c, double frontPosition);
	public double distanceToObstacle(double fromPositon, Direction d);
	public boolean remove(Vehicle car);
	public void setNextRoad(CarAcceptor car, Direction d);
	public double getEndPosition();
	public CarAcceptor getNextRoad(Direction d);
	public LightController getLight();
}
