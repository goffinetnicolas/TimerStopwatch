package states;

import states.stopwatch.AbstractStopwatch;
import states.timer.AbstractTimer;

public class Context implements EventListener {
	
    //private JLabel display;
  	    
    private ClockState currentState;

    public ClockState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ClockState state) {
        this.currentState = state;
    }

    public Context() {
    	setCurrentState(AbstractTimer.Instance()); // set initial state
        AbstractTimer.historyState = AbstractTimer.Instance();
        	// set history state of Timer composite statechart
        AbstractStopwatch.historyState = AbstractStopwatch.Instance();
    	// set history state of Stopwatch composite statechart        
    }
    
    public void left() { setCurrentState(getCurrentState().left()); }
    public void up() { setCurrentState(getCurrentState().up()); }
    public void right() { setCurrentState(getCurrentState().right());}
    
    public void tick() { setCurrentState(getCurrentState().doIt());}
   
    
    public String getDisplayText() {
    	return getCurrentState().getDisplayString();
    	//The substring is used to remove the prefix "multiChrono.states."
    	}
    
    public String getStateText() {
    	return getCurrentState().getClass().getSimpleName();
    	// uses Java reflection mechanism; 
    }
    
    public String getModeText() { 
    	return getCurrentState().getMode().name();
    	}
    
    public String getLeftText() {
    	return getCurrentState().getLeftText();
    }
    
    public String getUpText() {
    	return getCurrentState().getUpText();
    }
    
    public String getRightText() {
    	return getCurrentState().getRightText();
    }
}
