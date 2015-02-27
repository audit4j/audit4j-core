package org.audit4j.core.layout;

import static org.junit.Assert.assertNotNull;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>SimpleLayoutTest</code> contains tests for the class <code>{@link SimpleLayout}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:28 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class SimpleLayoutTest {
    /**
     * Run the SimpleLayout() constructor test.
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSimpleLayout_1()
        throws Exception {
        SimpleLayout result = new SimpleLayout();
        assertNotNull(result);
        // add additional test code here
    }

    /**
     * Run the String format(AuditEvent) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testFormat_1()
        throws Exception {
        SimpleLayout fixture = new SimpleLayout();
        AuditEvent event = new AuditEvent("", "", "", new Field[] {});
       // event.setFields(new ReadMostlyVector());

        String result = fixture.format(event);

        // add additional test code here
        assertNotNull(result);
        //assertEquals("02/04/2015 09:28:12|||==>", result);
    }

    /**
     * Run the void init() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInit_1()
        throws Exception {
        SimpleLayout fixture = new SimpleLayout();

        fixture.init();

        // add additional test code here
    }

    /**
     * Run the void stop() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testStop_1()
        throws Exception {
        SimpleLayout fixture = new SimpleLayout();

        fixture.stop();

        // add additional test code here
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
        new org.junit.runner.JUnitCore().run(SimpleLayoutTest.class);
    }
}