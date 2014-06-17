package model;

import java.awt.Color;
import model.DataFactory.Direction;

public interface Vehicle {
	public void run(double time);
	public void setmaxVelocity(double maxVelocity);
	public void setbrakeDistance(double brakeDistance);
	public void setstopDistance(double stopDistance);
	public void setlength(double length);
	public void setfrontPosition(double position);
	public void settimeStep(double timestep);
	public double getbackPosition();
	public double getmaxVelocity();
	public double getbrakeDistance();
	public double getstopDistance();
	public double getlength();
	public double getfrontPosition();
	public void setCurrentRoad(Road road);
	public void setCurrentIntersection(Light intersection);
	public CarAcceptor getCurrentRoad();
	public CarReleaser getCurrentInt();
	public Color getColor();
	public Direction getDirection();
}
