package org.audit4j.core.exception;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>InitializationExceptionTest</code> contains tests for the class <code>{@link InitializationException}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:28 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class InitializationExceptionTest {
    /**
     * Run the InitializationException(String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInitializationException_1()
        throws Exception {
        String message = "";

        InitializationException result = new InitializationException(message);

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getCause());
        assertEquals("org.audit4j.core.exception.InitializationException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the InitializationException(String,Throwable) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInitializationException_2()
        throws Exception {
        String message = "";
        Throwable t = new Throwable();

        InitializationException result = new InitializationException(message, t);

        // add additional test code here
        assertNotNull(result);
        assertEquals("org.audit4j.core.exception.InitializationException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
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
        new org.junit.runner.JUnitCore().run(InitializationExceptionTest.class);
    }
}