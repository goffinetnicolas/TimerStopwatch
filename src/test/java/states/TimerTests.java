package states;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import states.timer.*;
import states.stopwatch.AbstractStopwatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TimerTests {

	private static Context context;
	private ClockState current;

	@BeforeEach
	public void setup() {
        context = new Context(); // create the state machine context
        AbstractTimer.resetInitialValues();
	}
		
	@Test
	public void testInitialState() {
		/* When initialising the context (see setup() method above)
		 * its currentState will be initialised with the initial state
		 * of timer, i.e. the IdleTimer state.
		 */
		current = context.getCurrentState();
		
	    assertEquals(Mode.timer, current.getMode());
	    assertSame(IdleTimer.Instance(), current);
	    assertEquals(0, AbstractTimer.getTimer());
	    assertEquals(0, AbstractTimer.getMemTimer());
	}
	
	@org.junit.jupiter.api.Test
	public void testInitialAbstractTimer() {
		// The initial state of composite state AbstractTimer should be IdleTimer
		assertSame(AbstractTimer.Instance(), IdleTimer.Instance());
	}
	
	@org.junit.jupiter.api.Test
	public void testInitialActiveTimer() {
		// The initial state of composite state ActiveTimer should be RunningTimer
		assertSame(ActiveTimer.Instance(), RunningTimer.Instance());
	}
	
	@org.junit.jupiter.api.Test
	public void testHistoryState() {		
		current = AbstractTimer.Instance();
		// after processing the left() event, we should arrive in the initial state of AbstractStopwatch
		ClockState newState = current.left();
		assertEquals(AbstractStopwatch.Instance(), newState);
		/* after another occurrence of the left() event, we should return to the original state
		 * because we used history states		
		 */
		assertEquals(current, newState.left());
	}

}
