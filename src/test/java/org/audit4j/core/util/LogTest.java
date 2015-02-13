package org.audit4j.core.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>LogTest</code> contains tests for the class <code>{@link Log}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:26 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class LogTest {
    /**
     * Run the Log() constructor test.
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testLog_1()
        throws Exception {
        Log result = new Log();
        assertNotNull(result);
        // add additional test code here
    }

    /**
     * Run the void error(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testError_1()
        throws Exception {
        Object message = new Object();

        Log.error(message);

        // add additional test code here
    }

    /**
     * Run the void error(Object,Throwable) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testError_2()
        throws Exception {
        Object message = new Object();
        Throwable t = new Throwable();

        Log.error(message, t);

        // add additional test code here
    }

    /**
     * Run the void info(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testInfo_1()
        throws Exception {
        Object message = new Object();

        Log.info(message);

        // add additional test code here
    }

    /**
     * Run the void warn(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testWarn_1()
        throws Exception {
        Object message = new Object();

        Log.warn(message);

        // add additional test code here
    }

    /**
     * Run the void warn(Object,Throwable) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testWarn_2()
        throws Exception {
        Object message = new Object();
        Throwable t = new Throwable();

        Log.warn(message, t);

        // add additional test code here
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
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
     * @generatedBy CodePro at 2/4/15 9:26 AM
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
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(LogTest.class);
    }
}