package states;

//import static org.junit.Assert.*;
import states.timer.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class IdleTest {

	private Context context;
	
	@BeforeEach
	public void setUp() {
		// reset the initial values of timer to avoid inferences between different consecutive tests
		context = new Context();
		context.setCurrentState(IdleTimer.Instance()); // because we are testing the IdleTimer state here...
		AbstractTimer.resetInitialValues();
	}

	//The following "regression test" was added after having found a bug in the code
	@Test
	public void testSingletonDP() {
		// the initial state of the statechart should be an IdleTimer object
		// that is exactly the same object as the "singleton" instance of the IdleTimer state
		assertSame(IdleTimer.Instance(),context.getCurrentState());
	}

	@Test
	public void testUpNoTransition() {
		/* test whether the up event leaves us in the IdleTimer state.
		   (upon creation of IdleTimer state, memTimer is initialised to 0,
		   while memTimer > 0 in order to transition to ActiveTimer */
		assertEquals(0, AbstractTimer.getTimer());
		assertEquals(0, AbstractTimer.getMemTimer());
		assertSame(context.getCurrentState(), context.getCurrentState().up());
	}

	@Test
	public void testUpWithTransition() {		
		/* test whether a series of events (and the corresponding transitions)
		 * brings us to the ActiveTimer state,
		 * more specifically, to its initial state RunningTimer*/

		//go to SetTimer state
		context.right();
		context.tick();
		
		//go back to IdleTimer state
		context.right();
		context.tick();
		//check that value of memTimer is no longer 0 (value of timer is still 0)
		assertEquals(0, AbstractTimer.getTimer());
		assertEquals(1, AbstractTimer.getMemTimer());
		}

	@Test
	public void testRight() {		
		// test whether the right() event brings us to the SetTimer state
		assertSame(SetTimer.Instance(), context.getCurrentState().right());
	}

}
