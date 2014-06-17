package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import model.CarAcceptor;
import model.CarReleaser;
import model.DataFactory;
import model.MP;
import model.Vehicle;

/** 
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
  MyPainter _painter;
  public SwingAnimatorBuilder() {
    _painter = new MyPainter();
  }
  public Animator getAnimator() {
    if (_painter == null) { throw new IllegalStateException(); }
    Animator returnValue = new SwingAnimator(_painter, "Traffic Simulator",
                                             VP.displayWidth, VP.displayHeight, VP.displayDelay);
    _painter = null;
    return returnValue;
  }
  
  private static double skipInit = VP.gap;
  private static double skipRoad = VP.gap + MP.GUIroadLength;
  private static double skipCar = VP.gap + VP.elementWidth;
  private static double skipRoadCar = skipRoad + skipCar;
  
  public void addLight(CarReleaser d, int i, int j) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = new TranslatorWE(x, y, MP.GUIcarLength, VP.elementWidth, VP.scaleFactor);
    _painter.addLight(d,t);
  }
  public void addHorizontalRoad(CarAcceptor l, int i, int j, boolean eastToWest) {
    double x = skipInit + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = eastToWest ? new TranslatorEW(x, y, MP.GUIroadLength, VP.elementWidth, VP.scaleFactor)
                              : new TranslatorWE(x, y, MP.GUIroadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }
  public void addVerticalRoad(CarAcceptor l, int i, int j, boolean southToNorth) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + i*skipRoadCar;
    Translator t = southToNorth ? new TranslatorSN(x, y, MP.GUIroadLength, VP.elementWidth, VP.scaleFactor)
                                : new TranslatorNS(x, y, MP.GUIroadLength, VP.elementWidth, VP.scaleFactor);
    _painter.addRoad(l,t);
  }


  /** Class for drawing the Model. */
  private static class MyPainter implements SwingAnimatorPainter {

    /** Pair of a model element <code>x</code> and a translator <code>t</code>. */
    private static class Element<T> {
      T x;
      Translator t;
      Element(T x, Translator t) {
        this.x = x;
        this.t = t;
      }
    }
    
    private List<Element<CarAcceptor>> _roadElements;
    private List<Element<CarReleaser>> _lightElements;
    MyPainter() {
      _roadElements = new ArrayList<Element<CarAcceptor>>();
      _lightElements = new ArrayList<Element<CarReleaser>>();
    }    
    void addLight(CarReleaser x, Translator t) {
      _lightElements.add(new Element<CarReleaser>(x,t));
    }
    void addRoad(CarAcceptor x, Translator t) {
      _roadElements.add(new Element<CarAcceptor>(x,t));
    }
    
    public void paint(Graphics g) {
      // This method is called by the swing thread, so may be called
      // at any time during execution...

      // First draw the background elements
      for (Element<CarReleaser> e : _lightElements) {
    	if (e.x.getLight().getLightState() == DataFactory.LightState.GREEN_EW)
    		g.setColor(Color.GREEN);
    	else if (e.x.getLight().getLightState() == DataFactory.LightState.YELLOW_EW)
    		g.setColor(Color.YELLOW);
    	else
    		g.setColor(Color.RED);
        XGraphics.fillOval(g, e.t, 0, 0, MP.GUIcarLength, VP.elementWidth);
      }
      g.setColor(Color.GRAY);
      for (Element<CarAcceptor> e : _roadElements) {
        XGraphics.fillRect(g, e.t, 0, 0, MP.GUIroadLength, VP.elementWidth);
      }
      
      // Then draw the foreground elements
      for (Element<CarAcceptor> e : _roadElements) {
        // iterate through a copy because e.x.getCars() may change during iteration...
        for (Vehicle d : ((CarAcceptor) e.x).getCars().toArray(new Vehicle[0])) {
          g.setColor(d.getColor());
          XGraphics.fillOval(g, e.t, ((d.getfrontPosition()/d.getCurrentRoad().getEndPosition())  -  ((d.getlength())/d.getCurrentRoad().getEndPosition()))  *  MP.GUIroadLength, 0, (d.getlength()/d.getCurrentRoad().getEndPosition())*MP.GUIroadLength, VP.elementWidth);
        }
      }
    }
  }
}

