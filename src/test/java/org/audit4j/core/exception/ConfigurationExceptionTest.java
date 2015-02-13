package org.audit4j.core.exception;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>ConfigurationExceptionTest</code> contains tests for the class <code>{@link ConfigurationException}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:27 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class ConfigurationExceptionTest {
    /**
     * Run the ConfigurationException(String,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testConfigurationException_1()
        throws Exception {
        String message = "";
        String id = "";

        ConfigurationException result = new ConfigurationException(message, id);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getId());
        assertEquals(null, result.getCause());
        assertEquals("org.audit4j.core.exception.ConfigurationException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the ConfigurationException(String,String,Throwable) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testConfigurationException_2()
        throws Exception {
        String message = "";
        String id = "";
        Throwable e = new Throwable();

        ConfigurationException result = new ConfigurationException(message, id, e);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getId());
        assertEquals("org.audit4j.core.exception.ConfigurationException: ", result.toString());
        assertEquals("", result.getLocalizedMessage());
        assertEquals("", result.getMessage());
    }

    /**
     * Run the String getId() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetId_1()
        throws Exception {
        ConfigurationException fixture = new ConfigurationException("", "", new Throwable());

        String result = fixture.getId();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
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
     * @generatedBy CodePro at 2/4/15 9:27 AM
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
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(ConfigurationExceptionTest.class);
    }
}