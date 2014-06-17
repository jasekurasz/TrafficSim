package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

import visual.Animator;
import visual.AnimatorBuilder;
import agent.TimeServer;
import model.CarAcceptor;
import model.DataFactory.Direction;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class AltModel extends Observable implements Model {

	private Animator _animator;
	private boolean _disposed;
	private TimeServer _time;
	private MP mp = MP.getProperties();

	/** Creates a model to be visualized using the <code>builder</code>.
	 *  If the builder is null, no visualization is performed.
	 *  The number of <code>rows</code> and <code>columns</code>
	 *  indicate the number of {@link LightObj}s, organized as a 2D
	 *  matrix.  These are separated and surrounded by horizontal and
	 *  vertical {@link Road}s.  For example, calling the constructor with 1
	 *  row and 2 columns generates a model of the form:
	 *  <pre>
	 *     |  |
	 *   --@--@--
	 *     |  |
	 *  </pre>
	 *  where <code>@</code> is a {@link LightObj}, <code>|</code> is a
	 *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
	 *  Each road has one {@link Car}.
	 *
	 *  <p>
	 *  The {@link AnimatorBuilder} is used to set up an {@link
	 *  Animator}.
	 *  {@link AnimatorBuilder#getAnimator()} is registered as
	 *  an observer of this model.
	 *  <p>
	 */
	public AltModel(AnimatorBuilder builder, Integer rows, Integer columns) {
		_time = mp.getTimeServer();
		if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
			throw new IllegalArgumentException();
		}
		setup(builder, rows, columns);
		_animator = builder.getAnimator();
		super.addObserver(_animator);
		_time.addObserver(_animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run() {
		if (_disposed)
			throw new IllegalStateException();
		this._time.run(mp.getRunTime());
		super.setChanged();
		super.notifyObservers();
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		_animator.dispose();
		_disposed = true;
	}

	/**
	 * Construct the model, establishing correspondences with the visualizer.
	 */
	private void setup(AnimatorBuilder builder, Integer rows, Integer columns) {
		List<CarAcceptor> roads = new ArrayList<CarAcceptor>();
		CarReleaser[][] intersections = new CarReleaser[rows][columns];	
		
		// Add Lights
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				intersections[i][j] = DataFactory.makeLight();
				builder.addLight(intersections[i][j], i, j);
				_time.enqueue(_time.currentTime(), intersections[i][j].getLight());
			}
		}
		
		// Add Horizontal Roads and connect them to the source, lights and sinks
		boolean eastToWest = false;
		for (int i=0; i<rows; i++) {
			if (!eastToWest){
				CarSource carsource = DataFactory.makeSource(Direction.EW);
				for (int j=0; j<=columns; j++) {
					CarAcceptor l = DataFactory.makeRoad();
					if (j == 0) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i][j]);
					}
					else if (j == columns) {
						intersections[i][j-1].setNextRoad(l, Direction.EW);
						l.setNextRoad(DataFactory.makeSink());
					}
					else {
						intersections[i][j-1].setNextRoad(l, Direction.EW);
						l.setNextRoad(intersections[i][j]);
					}
					builder.addHorizontalRoad(l, i, j, eastToWest);
					roads.add(l);
				}
			} else {
					CarSource carsource = DataFactory.makeSource(Direction.EW);
					for (int j=columns; j>=0; j--) {
						CarAcceptor l = DataFactory.makeRoad();
						if (j == columns) {
							carsource.setNextRoad(l);
							l.setNextRoad(intersections[i][j-1]);
						}
						else if (j == 0) {
							intersections[i][j].setNextRoad(l, Direction.EW);
							l.setNextRoad(DataFactory.makeSink());
						}
						else {
							intersections[i][j].setNextRoad(l, Direction.EW);
							l.setNextRoad(intersections[i][j-1]);
						}
						builder.addHorizontalRoad(l, i, j, eastToWest);
						roads.add(l);
					}
			}
			if(mp.getIsAlternating()) {eastToWest = !eastToWest;}
		}
		
		// Add Vertical Roads and connect them to the source, lights and sinks
		boolean southToNorth = false;
		for (int j=0; j<columns; j++) {
			if (!southToNorth) {
				CarSource carsource = DataFactory.makeSource(Direction.NS);
				for (int i=0; i<=rows; i++) {
					CarAcceptor l = DataFactory.makeRoad();
					if (i == 0) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i][j]);
					}
					else if (i == rows) {
						intersections[i-1][j].setNextRoad(l, Direction.NS);
						l.setNextRoad(DataFactory.makeSink());
					}
					else {
						intersections[i-1][j].setNextRoad(l, Direction.NS);
						l.setNextRoad(intersections[i][j]);
					}

					builder.addVerticalRoad(l, i, j, southToNorth);
					roads.add(l);
				}
			} else {
				CarSource carsource = DataFactory.makeSource(Direction.NS);
				for (int i=rows; i>=0; i--) {
					CarAcceptor l = new Road();
					if (i == rows) {
						carsource.setNextRoad(l);
						l.setNextRoad(intersections[i-1][j]);
					}
					else if (i == 0) {
						intersections[i][j].setNextRoad(l, Direction.NS);
						l.setNextRoad(DataFactory.makeSink());
					}
					else {
						intersections[i][j].setNextRoad(l, Direction.NS);
						l.setNextRoad(intersections[i-1][j]);
					}

					builder.addVerticalRoad(l, i, j, southToNorth);
					roads.add(l);
				}
			}
			if(mp.getIsAlternating()) {southToNorth = !southToNorth;}
		}
	}
}
