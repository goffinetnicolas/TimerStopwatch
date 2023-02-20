package states.stopwatch;

import junit.framework.TestCase;
import org.junit.Assert;
import states.ClockState;

public class LaptimeStopwatchTest extends TestCase {

    private ClockState test;

    public void testInstance() {
        test = LaptimeStopwatch.Instance();
        assertNotNull(test);
    }

    public void testUp() {
        test = LaptimeStopwatch.Instance();
        ClockState a = test.up();
        Assert.assertEquals(a,RunningStopwatch.Instance());
    }

    public void testGetUpText() {
        test = LaptimeStopwatch.Instance();
        String a = test.getUpText();
        Assert.assertEquals(a,"unsplit");
    }

    public void testDoIt() {
    }

    public void testGetDisplayString() {
    }
}