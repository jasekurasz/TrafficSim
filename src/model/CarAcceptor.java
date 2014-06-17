package model;

import java.util.Set;
import model.DataFactory.Direction;

public interface CarAcceptor {
	public boolean accept(Vehicle c, double frontPosition);
	public double distanceToObstacle(double fromPositon, Direction d);
	public boolean remove(Vehicle car);
	public void setNextRoad(CarReleaser car);
	public double getEndPosition();
	public CarReleaser getNextRoad(Direction d);
	public Set<Vehicle> getCars();
}
