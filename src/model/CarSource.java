package model;

public interface CarSource {
	public void setNextRoad(CarAcceptor r);
	public CarAcceptor getNextRoad();
	public void run(double time);
}
