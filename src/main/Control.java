package main;

import ui.UI;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;
import ui.UIError;
import ui.UIForm;
import ui.UIFormTest;
import ui.UIFormBuilder;
import model.MP;
import model.Model;

public class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int NEWMENU = 3;
	private static final int NUMSTATES = 4;
	private static final int NUMSUBSTATES = 16;
    private UIMenu[] _menus;
    private UIMenu[] _submenu;
	private int _state;
	
	private UIFormTest _IntegerTest;
	private UIFormTest _DoubleTest;
	private UIForm _setNewValue;
	private UIForm _rows;
	private UIForm _columns;
	private UIForm _alt;
	private UIForm _min;
	private UIForm _max;
	
	private Model _model;
	private UI _ui;
	private MP mp = MP.getProperties();
	
	Control(UI ui) {
		_ui = ui;
		_menus = new UIMenu[NUMSTATES];
		_submenu = new UIMenu[NUMSUBSTATES];
		_state = START;
		addSTART(START);
		addEXIT(EXIT);
		addNEWMENU(NEWMENU);
		
		_IntegerTest = new UIFormTest() {
	        public boolean run(String input) {
	          try {
	        	Integer.parseInt(input);
	            return true;
	          } catch (NumberFormatException e) {
	            return false;
	          }
	        }
	      };
	      
	      _DoubleTest = new UIFormTest() {
		        public boolean run(String input) {
		          try {
		        	Double.parseDouble(input);
		            return true;
		          } catch (NumberFormatException e) {
		            return false;
		          }
		        }
		      };
	      
		UIFormBuilder newValue = new UIFormBuilder();
		newValue.add("Enter value", _DoubleTest);
		_setNewValue = newValue.toUIForm("Enter number");
		
		UIFormBuilder rows = new UIFormBuilder();
		rows.add("Enter number of rows (default value is 1)", _IntegerTest);
		_rows = rows.toUIForm("Enter number");
		
		UIFormBuilder columns = new UIFormBuilder();
		columns.add("Enter number of columns (default value is 2)", _IntegerTest);
		_columns = columns.toUIForm("Enter number");
		
		UIFormBuilder alt = new UIFormBuilder();
		alt.add("Enter 1 for simple pattern and 2 for alternating", _IntegerTest);
		_alt = alt.toUIForm("Enter number");
		
		UIFormBuilder min = new UIFormBuilder();
		min.add("Enter a minimum", _DoubleTest);
		_min = min.toUIForm("Enter number");
		
		UIFormBuilder max = new UIFormBuilder();
		max.add("Enter a maximum", _DoubleTest);
		_max = max.toUIForm("Enter number");
		
	}
	
	void run() {
		try {
			while (_state != EXITED) {
				if (_state == NEWMENU) {
					_ui.processMenu(_submenu[_state]);
				} else _ui.processMenu(_menus[_state]);
			}
		} catch (UIError e) {
			_ui.displayError("UI closed");
		}
	}
	
	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();
		
		m.add("Default", 
			new UIMenuAction() {
				public void run() {
					_ui.displayError("doh!");
				}
			});
		
		m.add("Run simulation", 
			new UIMenuAction() {
				public void run() {	
					_model = mp.getModel();
					_model.run();
					_model.dispose();
					System.exit(0);
				}
			});
		
		m.add("Change simulation parameters", 
				new UIMenuAction() {
					public void run() {
							_state = NEWMENU;
						}
				});
		
	    m.add("Exit",
	    		new UIMenuAction() {
			        public void run() {
			          _state = EXIT;
			        }
				});
	    
	    _menus[stateNum] = m.toUIMenu("Simulation City");	
	}
	
	private void addNEWMENU(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();
		
		m.add("Default", 
			new UIMenuAction() {
				public void run() {
					_ui.displayError("doh!");
				}
			});
		
		m.add("Show current values", 
			new UIMenuAction() {
				public void run() {
					_ui.displayMessage(mp.toString());
				}
			});
		
		m.add("Simulation time step", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_setNewValue);
						mp.setTimeStep(Double.parseDouble(result[0]));
					}
				});
		
		m.add("Simulation runtime", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_setNewValue);
						mp.setRunTime(Double.parseDouble(result[0]));
					}
				});
		
		m.add("Grid size", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_rows);
						mp.setNumRows(Integer.parseInt(result[0]));
						String[] result1 = _ui.processForm(_columns);
						mp.setNumColumns(Integer.parseInt(result1[0]));
					}
				});
		
		m.add("Set traffic pattern", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_alt);
						mp.setIsAlternating(Integer.parseInt(result[0]));
						
					}
				});
		
		m.add("Set car entry rate", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setEntryRateMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setEntryRateMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set road lengths", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setRoadLengthMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setRoadLengthMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set intersection lengths", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setIntLengthMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setIntLengthMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set car length", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setCarLengthMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setCarLengthMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set max car velocity", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setVelocityMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setVelocityMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set car stop distance", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setStopDistMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setStopDistMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set car break distance", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setBrakeDistMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setBrakeDistMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set traffic light green times", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setGreenMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setGreenMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Set traffic light yellow times", 
				new UIMenuAction() {
					public void run() {
						String[] result = _ui.processForm(_min);
						mp.setYellowMin(Double.parseDouble(result[0]));
						String[] result1 = _ui.processForm(_max);
						mp.setYellowMax(Double.parseDouble(result1[0]));
					}
				});
		
		m.add("Reset simulation and return to main menu", 
				new UIMenuAction() {
					public void run() {
						mp.setDefaults();
						mp = MP.getProperties();
						_state = START;
					}
				});
		
		m.add("Return to main menu",
	    		new UIMenuAction() {
			        public void run() {
			          _state = START;
			        }
				});
	    
	    _submenu[stateNum] = m.toUIMenu("100 mph switchin' lanes like whoa");
	}
	
	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();
	    
	    m.add("Default", new UIMenuAction() { public void run() {} });
	    m.add("Yes",
	      new UIMenuAction() {
	        public void run() {
	          _state = EXITED;
	        }
	      });
	    m.add("No",
	      new UIMenuAction() {
	        public void run() {
	          _state = START;
	        }
	      });
	    
	    _menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	  }
}
