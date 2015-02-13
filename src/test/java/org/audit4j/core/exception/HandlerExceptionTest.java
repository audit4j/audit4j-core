package org.audit4j.core.exception;

import org.audit4j.core.handler.Handler;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>HandlerExceptionTest</code> contains tests for the class <code>{@link HandlerException}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:28 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class HandlerExceptionTest {
    /**
     * Run the HandlerException(String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testHandlerException_1()
        throws Exception {
        String message = "";

        HandlerException result = new HandlerException(message);

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getCause());
        assertEquals("org.audit4j.core.exception.HandlerException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the HandlerException(String,Class<? extends Handler>,Throwable) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testHandlerException_2()
        throws Exception {
        String message = "";
        Class<? extends Handler> clazz = Handler.class;
        Throwable e = new Throwable();

        HandlerException result = new HandlerException(message, clazz, e);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(HandlerExceptionTest.class);
    }
}