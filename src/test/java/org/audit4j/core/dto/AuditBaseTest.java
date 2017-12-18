package org.audit4j.core.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>AuditBaseTest</code> contains tests for the class <code>{@link Event}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:27 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class AuditBaseTest {
    /**
     * Run the AuditBase() constructor test.
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditBase_1()
        throws Exception {
        Event result = new Event();
        assertNotNull(result);
        // add additional test code here
    }


    /**
     * Run the Date getTimestamp() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetTimestamp_1()
        throws Exception {
        Event fixture = new Event();
        fixture.setUuid(new Long(1L));
        fixture.setTimestamp(new Date());

        Date result = fixture.getTimestamp();

        // add additional test code here
        assertNotNull(result);
       // assertEquals(DateFormat.getInstance().format(new Date(1423022275663L)), DateFormat.getInstance().format(result));
       // assertEquals(1423022275663L, result.getTime());
    }

    /**
     * Run the Long getUuid() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetUuid_1()
        throws Exception {
        Event fixture = new Event();
        fixture.setUuid(new Long(1L));
        fixture.setTimestamp(new Date());

        Long result = fixture.getUuid();

        // add additional test code here
        assertNotNull(result);
        assertEquals("1", result.toString());
        assertEquals((byte) 1, result.byteValue());
        assertEquals((short) 1, result.shortValue());
        assertEquals(1, result.intValue());
        assertEquals(1L, result.longValue());
        assertEquals(1.0f, result.floatValue(), 1.0f);
        assertEquals(1.0, result.doubleValue(), 1.0);
    }

    /**
     * Run the void setAuditId(Integer) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetAuditId_1()
        throws Exception {
        Event fixture = new Event();
        fixture.setUuid(new Long(1L));
        fixture.setTimestamp(new Date());
        Integer auditId = new Integer(1);


        // add additional test code here
    }

    /**
     * Run the void setTimestamp(Date) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetTimestamp_1()
        throws Exception {
        Event fixture = new Event();
        fixture.setUuid(new Long(1L));
        fixture.setTimestamp(new Date());
        Date timestamp = new Date();

        fixture.setTimestamp(timestamp);

        // add additional test code here
    }

    /**
     * Run the void setUuid(Long) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetUuid_1()
        throws Exception {
        Event fixture = new Event();
        fixture.setUuid(new Long(1L));
        fixture.setTimestamp(new Date());
        Long uuid = new Long(1L);

        fixture.setUuid(uuid);

        // add additional test code here
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
        new org.junit.runner.JUnitCore().run(AuditBaseTest.class);
    }
}