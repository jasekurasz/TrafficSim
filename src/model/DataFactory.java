package model;

public class DataFactory {
	
	public DataFactory() {}
	//enums for light states
	public enum LightState {GREEN_NS, YELLOW_NS, GREEN_EW, YELLOW_EW}
	
	//enums for directions
	public enum Direction {EW, NS }
	
	//facotry methods to build new objects
	static public final Vehicle makeCar(Direction d) {
		return new Car(d);
	}
	
	static public final CarReleaser makeSink() {
		return new Sink();
	}
	
	static public final CarSource makeSource(Direction d) {
		return new Source(d);
	}
	
	static public final CarAcceptor makeRoad() {
		return new Road();
	}
	
	static public final CarReleaser makeLight() {
		return new Light();
	}

}