package model;

import visual.SwingAnimatorBuilder;
import agent.TimeServer;
import agent.TimeServerLinked;

public class MP {
	
	private static MP properties = null;
	private double carLengthMax = 15;
	private double carLengthMin = 10;
	private double roadLengthMax = 500;
	private double roadLengthMin = 200;
	private double intLengthMin = 10;
	private double intLengthMax = 15;
	private double carVelocityMax = 3; 
	private double carVelocityMin = 1; 
	private double stopDistMax = 5;
	private double stopDistMin = 0.5;
	private double brakeDistMax = 10;
	private double brakeDistMin = 9;
	private double carEntryRateMax = 2.5;
	private double carEntryRateMin = 1;
	private double greenDurationMax = 180;
	private double greenDurationMin = 30;
	private double yellowDurationMax = 40;
	private double yellowDurationMin = 32;
	private double timeStep = .4;
	private double runTime = 1000;
	private int numRows = 1;
	private int numColumns = 2;
	private boolean isAlternating = false;
	private TimeServer timeserver = new TimeServerLinked();
	
	public static double GUIcarLength = 10;
	public static double GUIroadLength = 200;
	public static double GUImaxVelocity = 6;
	
	private MP() {}
	
	//if MP is already made then return otherwise create a new one
	public static MP getProperties() {
		if (MP.properties == null)
			MP.properties = new MP();
		return MP.properties;
	}
	
	//returns model type based on user input (used for strategy pattern)
	public Model getModel() {
		if (this.isAlternating)
			return new AltModel(new SwingAnimatorBuilder(),this.getNumRows(),this.getNumColumns());
		else return new SimpleModel(new SwingAnimatorBuilder(),this.getNumRows(),this.getNumColumns());
	}	
	
	//return current variables in a string
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Simulation time step (seconds) [" + this.getTimeStep() + "]\n");
		buffer.append("Simulation run time (seconds) [" + this.getRunTime() + "]\n");
		buffer.append("Grid size (number of roads) [row=" + this.getNumRows()+",column="+this.getNumColumns()+"]\n");
		buffer.append("Traffic pattern [" + this.TrafficPattern() + "]\n");
		buffer.append("Car entry rate (seconds/car) [min="+this.getEntryRateMin()+",max="+this.getEntryRateMax()+"]\n");
		buffer.append("Road segment length (meters) [min="+this.getRoadLengthMin()+",max="+this.getRoadLengthMax()+"]\n");
		buffer.append("Intersection length (meters) [min="+this.getIntLengthMin()+",max="+this.getIntLengthMax()+"]\n");
		buffer.append("Car length (meters) [min="+this.getCarLengthMin()+",max="+this.getCarLengthMax()+"]\n");
		buffer.append("Car maximum velocity (meters/second) [min="+this.getVelocityMin()+",max="+this.getVelocityMax()+"]\n");
		buffer.append("Car stop distance (meters) [min="+this.getStopDistMin()+",max="+this.getStopDistMax()+"]\n");
		buffer.append("Car brake distance (meters) [min="+this.getBrakeDistMin()+",max="+this.getBrakeDistMax()+"]\n");
		buffer.append("Traffic light green time (seconds) [min="+this.getGreenMin()+",max="+this.getGreenMax()+"]\n");
		buffer.append("Traffic light yellow time (seconds) [min="+this.getYellowMin()+",max="+this.getYellowMax()+"]\n");
		return buffer.toString();
	}
	
	//all setters and getters below
	public void setDefaults() {
		MP.properties = null;
	}
	
	public void setIsAlternating(int num) {
		if (num == 1) {
			isAlternating = false;
		} else isAlternating = true;
	}
	
	public boolean getIsAlternating() {
		return isAlternating;
	}
	
	private String TrafficPattern() {
		if (isAlternating)
			return "alternating";
		return "simple";
	}

	public void setNumRows(int num) {
		numRows = num;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumColumns(int num) {
		numColumns = num;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public void setGreenMax(double max) {
		this.greenDurationMax = max;
	}
	
	public double getGreenMax() {
		return greenDurationMax;
	}
	
	public void setGreenMin(double min) {
		this.greenDurationMin = min;
	}
	
	public double getGreenMin() {
		return greenDurationMin;
	}
	
	public void setYellowMax(double max) {
		this.yellowDurationMax = max;
	}
	
	public double getYellowMax() {
		return yellowDurationMax;
	}
	
	public void setYellowMin(double min) {
		this.yellowDurationMin = min;
	}
	
	public double getYellowMin() {
		return yellowDurationMin;
	}
	
	public void setEntryRateMax(double max) {
		carEntryRateMax = max;
	}
	
	public double getEntryRateMax() {
		return carEntryRateMax;
	}
	
	public void setEntryRateMin(double min) {
		carEntryRateMin = min;
	}
	
	public double getEntryRateMin() {
		return carEntryRateMin;
	}

	public void setIntLengthMax(double max) {
		this.intLengthMax = max;
	}

	public double getIntLengthMax() {
		return intLengthMax;
	}

	public void setIntLengthMin(double min) {
		this.intLengthMin = min;
	}

	public double getIntLengthMin() {
		return intLengthMin;
	}

	public void setCarLengthMax(double max) {
		this.carLengthMax = max;
	}

	public double getCarLengthMax() {
		return carLengthMax;
	}

	public void setCarLengthMin(double min) {
		this.carLengthMin = min;
	}

	public double getCarLengthMin() {
		return carLengthMin;
	}
	
	public void setVelocityMax(double max) {
		this.carVelocityMax = max;
	}
	
	public double getVelocityMax() {
		return carVelocityMax;
	}
	
	public void setVelocityMin(double min) {
		this.carVelocityMin = min;
	}
	
	public double getVelocityMin() {
		return carVelocityMin;
	}

	public void setStopDistMax(double max) {
		this.stopDistMax = max;
	}

	public double getStopDistMax() {
		return stopDistMax;
	}

	public void setStopDistMin(double min) {
		this.stopDistMin = min;
	}

	public double getStopDistMin() {
		return stopDistMin;
	}

	public void setBrakeDistMax(double max) {
		this.brakeDistMax = max;
	}

	public double getBrakeDistMax() {
		return brakeDistMax;
	}

	public void setBrakeDistMin(double min) {
		this.brakeDistMin = min;
	}

	public double getBrakeDistMin() {
		return brakeDistMin;
	}

	public void setTimeStep(double time) {
		this.timeStep = time;
	}

	public double getTimeStep() {
		return timeStep;
	}

	public void setRoadLengthMax(double max) {
		this.roadLengthMax = max;
	}

	public double getRoadLengthMax() {
		return roadLengthMax;
	}

	public void setRoadLengthMin(double min) {
		this.roadLengthMin = min;
	}

	public double getRoadLengthMin() {
		return roadLengthMin;
	}

	public void setRunTime(double time) {
		this.runTime = time;
	}

	public double getRunTime() {
		return runTime;
	}
	
	public TimeServer getTimeServer() {
		return timeserver;
	}
}
