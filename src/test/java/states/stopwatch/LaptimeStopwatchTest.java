package states.stopwatch;

import states.ClockState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LaptimeStopwatchTest  {

    private ClockState test;

    public void testInstance() {
        test = LaptimeStopwatch.Instance();
        assertNotNull(test);
    }

    public void testUp() {
        test = LaptimeStopwatch.Instance();
        ClockState a = test.up();
        assertEquals(a,RunningStopwatch.Instance());
    }

    public void testGetUpText() {
        test = LaptimeStopwatch.Instance();
        String a = test.getUpText();
        assertEquals(a,"unsplit");
    }
}