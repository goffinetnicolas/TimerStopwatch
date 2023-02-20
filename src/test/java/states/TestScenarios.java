package states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import states.stopwatch.AbstractStopwatch;
import states.stopwatch.LaptimeStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class TestScenarios {

	Context c;
	
    @BeforeEach
    public void setup() {
    	c = new Context();
     	//before each test, reset the timer values to avoid interference between tests:
    	AbstractTimer.resetInitialValues();
    	AbstractStopwatch.resetInitialValues();
    }
    
  //This is more a kind of integration test than a real unit test	
  @Test
  public void completeScenario() {
	  assertEquals(IdleTimer.Instance(),c.getCurrentState());
	  assertEquals(0,AbstractTimer.getMemTimer());
	  
	  c.right(); // start incrementing the memTimer variable
	  c.tick();
	  assertSame(SetTimer.Instance(),c.getCurrentState());
	  assertEquals(1,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());

	  c.right(); // stop incrementing the memTimer variable
	  c.tick();
	  assertEquals(2,AbstractTimer.getMemTimer());
	  assertEquals(0,AbstractTimer.getTimer());
	  
	  c.up(); // start running the timer
	  assertEquals(2, AbstractTimer.getTimer());
	  c.tick();
	  assertEquals(2, AbstractTimer.getMemTimer());
	  assertEquals(1, AbstractTimer.getTimer());
	  
	  
	  c.up(); // pause the timer
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.getCurrentState());
	  assertEquals(2, AbstractTimer.getMemTimer());
	  assertEquals(1, AbstractTimer.getTimer());
	  
	  c.left(); // go to stopwatch mode
	  c.tick();
	  assertSame(ResetStopwatch.Instance(), c.getCurrentState());
	  assertEquals(0, AbstractStopwatch.getTotalTime());
	  assertEquals(0, AbstractStopwatch.getLapTime());
	  
	  c.up(); //start running the stopwatch
	  c.tick();
	  assertSame(RunningStopwatch.Instance(), c.getCurrentState());
	  assertEquals(1, AbstractStopwatch.getTotalTime());
	  assertEquals(0, AbstractStopwatch.getLapTime());
	 
	  c.up(); // record stopwatch laptime
	  c.tick();
	  assertSame(LaptimeStopwatch.Instance(), c.getCurrentState());
	  assertEquals(2, AbstractStopwatch.getTotalTime());
	  assertEquals(1, AbstractStopwatch.getLapTime());
	  
	  c.left(); // go back to timer mode (remembering history state)
	  c.tick();
	  assertSame(PausedTimer.Instance(), c.getCurrentState());
	  assertEquals(2, AbstractTimer.getMemTimer());
	  assertEquals(1, AbstractTimer.getTimer());
	  
	  c.up(); // continue running timer
	  assertSame(RunningTimer.Instance(), c.getCurrentState());
	  c.tick();
	  //automatic switch to ringing timer since timer has reached 0...
	  assertSame(RingingTimer.Instance(), c.getCurrentState());
	  assertEquals(2, AbstractTimer.getMemTimer());
	  assertEquals(0, AbstractTimer.getTimer());
	  
	  c.right(); // return to idle timer state
	  c.tick();
	  assertSame(IdleTimer.Instance(), c.getCurrentState());
	  assertEquals(2, AbstractTimer.getMemTimer());
	  assertEquals(0, AbstractTimer.getTimer());
	  }

}
