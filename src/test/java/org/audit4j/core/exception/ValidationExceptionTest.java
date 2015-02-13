package org.audit4j.core.exception;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>ValidationExceptionTest</code> contains tests for the class <code>{@link ValidationException}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:26 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class ValidationExceptionTest {
    /**
     * Run the ValidationException(String,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testValidationException_1()
        throws Exception {
        String message = "";
        String level = "";

        ValidationException result = new ValidationException(message, level);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getLevel());
        assertEquals(null, result.getCause());
        assertEquals("org.audit4j.core.exception.ValidationException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the String getLevel() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:26 AM
     */
    @Test
    public void testGetLevel_1()
        throws Exception {
        ValidationException fixture = new ValidationException("", "");

        String result = fixture.getLevel();

        // add additional test code here
        assertEquals("", result);
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
        new org.junit.runner.JUnitCore().run(ValidationExceptionTest.class);
    }
}