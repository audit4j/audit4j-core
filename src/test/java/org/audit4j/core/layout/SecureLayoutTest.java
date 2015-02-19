package org.audit4j.core.layout;

import static org.junit.Assert.assertNotNull;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.dto.AuditEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>SecureLayoutTest</code> contains tests for the class <code>{@link SecureLayout}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:28 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class SecureLayoutTest extends Audit4jTestBase{
    /**
     * Run the SecureLayout() constructor test.
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSecureLayout_1()
        throws Exception {
        SecureLayout result = new SecureLayout();
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
        SecureLayout fixture = new SecureLayout();
        fixture.init();
        fixture.setSalt("232332324");
        fixture.setKey("Aud1T4jSecureKey");
        AuditEvent event = new AuditEvent();

        String result = fixture.format(event);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String format(AuditEvent) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testFormat_2()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.init();
        fixture.setSalt("232332324");
        fixture.setKey("Aud1T4jSecureKey");
        AuditEvent event = new AuditEvent();

        String result = fixture.format(event);

        assertNotNull(result);
    }

    /**
     * Run the String getKey() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetKey_1()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.init();
        fixture.setSalt("232332324");
        fixture.setKey("Aud1T4jSecureKey");

        String result = fixture.getKey();

        assertNotNull(result);
    }

    /**
     * Run the String getSalt() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetSalt_1()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.init();
        fixture.setSalt("232332324");
        fixture.setKey("Aud1T4jSecureKey");

        String result = fixture.getSalt();

        assertNotNull(result);
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
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("232332324");
        fixture.setKey("Aud1T4jSecureKey");

        fixture.init();

        // add additional test code here
    }

    /**
     * Run the void init() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInit_2()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt((String) null);
        fixture.setKey("");

        fixture.init();

        // add additional test code here
    }

    /**
     * Run the void init() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInit_3()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("");
        fixture.setKey((String) null);

        fixture.init();

        // add additional test code here
    }

    /**
     * Run the void init() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testInit_4()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("");
        fixture.setKey("");

        fixture.init();

        // add additional test code here
    }

    /**
     * Run the void setKey(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSetKey_1()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("");
        fixture.setKey("");
        String key = "";

        fixture.setKey(key);

        // add additional test code here
    }

    /**
     * Run the void setSalt(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSetSalt_1()
        throws Exception {
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("");
        fixture.setKey("");
        String salt = "";

        fixture.setSalt(salt);

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
        SecureLayout fixture = new SecureLayout();
        fixture.setSalt("");
        fixture.setKey("");

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
        new org.junit.runner.JUnitCore().run(SecureLayoutTest.class);
    }
}