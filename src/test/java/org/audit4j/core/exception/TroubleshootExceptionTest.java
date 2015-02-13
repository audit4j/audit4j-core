package org.audit4j.core.exception;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>TroubleshootExceptionTest</code> contains tests for the class <code>{@link TroubleshootException}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:26 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class TroubleshootExceptionTest {
    /**
     * Run the TroubleshootException(String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testTroubleshootException_1()
        throws Exception {
        String message = "";

        TroubleshootException result = new TroubleshootException(message);

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getCause());
        assertEquals("org.audit4j.core.exception.TroubleshootException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the TroubleshootException(String,Throwable) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testTroubleshootException_2()
        throws Exception {
        String message = "";
        Throwable t = new Throwable();

        TroubleshootException result = new TroubleshootException(message, t);

        // add additional test code here
        assertNotNull(result);
        assertEquals("org.audit4j.core.exception.TroubleshootException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
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
        new org.junit.runner.JUnitCore().run(TroubleshootExceptionTest.class);
    }
}