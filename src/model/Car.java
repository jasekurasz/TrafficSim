package model;

import java.awt.Color;

import model.DataFactory.Direction;
import agent.Agent;
import agent.TimeServer;

final class Car implements Agent, Vehicle{
	
	private double carLength;
	private double maxVelocity;
	private double stopDistance;
	private double brakeDistance;
	private double timeStep;
	private double frontPosition;
	private double velocity;
	private TimeServer timeserver;
	private CarAcceptor currentRoad;
	private CarReleaser currentInt;
	private boolean inIntersection;
	private double distanceToObstacle;
	private Direction direction;
	private Color color;
	private MP mp = MP.getProperties();
	
	//Car constructor
	public Car(Direction d) {
		carLength = Math.random()*(mp.getCarLengthMax() - mp.getCarLengthMin()) + mp.getCarLengthMin();
		maxVelocity = Math.random()*(mp.getVelocityMax() - mp.getVelocityMin()) + mp.getVelocityMin();
		stopDistance = Math.random()*(mp.getStopDistMax() - mp.getStopDistMin()) + mp.getStopDistMin();
		brakeDistance = Math.random()*(mp.getBrakeDistMax() - mp.getBrakeDistMin()) + mp.getBrakeDistMin();
		frontPosition = 0;
		direction = d;
		timeStep = mp.getTimeStep();
		timeserver = mp.getTimeServer();
		color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
	}
	//run method for the car that will update its velocity and reenqueue it in the timeserver
	public void run(double time) {
		if (inIntersection) {
			distanceToObstacle = currentInt.distanceToObstacle(frontPosition, this.direction);
		} else distanceToObstacle = currentRoad.distanceToObstacle(frontPosition, direction);
		
		if (distanceToObstacle < maxVelocity && (distanceToObstacle > brakeDistance || distanceToObstacle > stopDistance))
			velocity = distanceToObstacle / 2;
		else {
			velocity = (maxVelocity / (brakeDistance - stopDistance)) * (distanceToObstacle - stopDistance);
		}
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(maxVelocity, velocity);
		frontPosition = frontPosition + velocity * timeStep;
		
		setfrontPosition(frontPosition);
		timeserver.enqueue(timeserver.currentTime() + this.timeStep, this);
	}
	
	public void setfrontPosition(double position) {
		if (inIntersection) {
			if (position > currentInt.getEndPosition()) {
			    currentInt.getNextRoad(this.direction).accept(this, position - currentInt.getEndPosition());
			    currentInt.remove(this);
				return;
			}
			else {
				frontPosition = position;
			}
		} else {
			if (position > currentRoad.getEndPosition()) {
			    currentRoad.getNextRoad(direction).accept(this, position - currentRoad.getEndPosition());
			    currentRoad.remove(this);
				return;
			}
			else {
				frontPosition = position;
			}
		}
	}
	//All the setters and getters below
	public void setmaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public void setbrakeDistance(double brakeDistance) {
		this.brakeDistance = brakeDistance;
	}

	public void setstopDistance(double stopDistance) {
		this.stopDistance = stopDistance;
	}

	public void setlength(double length) {
		this.carLength = length;
	}
	
	public void settimeStep(double timestep) {
		this.timeStep = timestep;
	}

	public double getbackPosition() {
		return this.frontPosition - this.carLength;
	}

	public double getmaxVelocity() {
		return this.maxVelocity;
	}

	public double getbrakeDistance() {
		return this.brakeDistance;
	}

	public double getstopDistance() {
		return this.stopDistance;
	}

	public double getlength() {
		return this.carLength;
	}

	public double getfrontPosition() {
		return this.frontPosition;
	}
	
	public void setCurrentRoad(Road road) {
		this.currentRoad = road;
		inIntersection = false;
	}
	
	public void setCurrentIntersection(Light intersection) {
		this.currentInt = intersection;
		inIntersection = true;
	}
	
	public CarAcceptor getCurrentRoad() {
		return this.currentRoad;
	}

	public CarReleaser getCurrentInt() {
		return this.currentInt;
	}

	public Color getColor() {
		return this.color;
	}

	public Direction getDirection() {
		return direction;
	}

}
