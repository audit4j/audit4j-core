package org.audit4j.core.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import jsr166e.extra.ReadMostlyVector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>AuditEventTest</code> contains tests for the class
 * <code>{@link AuditEvent}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:27 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class AuditEventTest {
    /**
     * Run the AuditEvent() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditEvent_1() throws Exception {

        AuditEvent result = new AuditEvent();

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getOrigin());
        assertEquals(null, result.getAction());
        assertEquals(null, result.getActor());
        assertNotNull(result.getUuid());
    }

    /**
     * Run the AuditEvent(String,String,Field[]) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditEvent_2() throws Exception {
        String actor = "";
        String action = "";

        AuditEvent result = new AuditEvent(actor, action);

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getOrigin());
        assertEquals("", result.getAction());
        assertEquals("", result.getActor());
        assertNotNull(result.getUuid());
    }

    /**
     * Run the AuditEvent(String,String,Field[]) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditEvent_3() throws Exception {
        String actor = "";
        String action = "";

        AuditEvent result = new AuditEvent(actor, action);

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getOrigin());
        assertEquals("", result.getAction());
        assertEquals("", result.getActor());
        assertNotNull(result.getUuid());    }

    /**
     * Run the AuditEvent(String,String,String,Field[]) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditEvent_4() throws Exception {
        String actor = "";
        String action = "";
        String origin = "";

        AuditEvent result = new AuditEvent(actor, action, origin);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getOrigin());
        assertEquals("", result.getAction());
        assertEquals("", result.getActor());
        assertNotNull(result.getUuid());
    }

    /**
     * Run the AuditEvent(String,String,String,Field[]) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAuditEvent_5() throws Exception {
        String actor = "";
        String action = "";
        String origin = "";

        AuditEvent result = new AuditEvent(actor, action, origin);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getOrigin());
        assertEquals("", result.getAction());
        assertEquals("", result.getActor());
        assertNotNull(result.getUuid());
    }

    /**
     * Run the void addField(Field) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAddField_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        Field field = new Field();

        fixture.addField(field);

        // add additional test code here
    }

    /**
     * Run the void addField(String,Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAddField_2() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String name = "";
        Object value = new Object();

        fixture.addField(name, value);

        // add additional test code here
    }

    /**
     * Run the void addField(String,Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAddField_3() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String name = "";
        Object value = null;

        fixture.addField(name, value);

        // add additional test code here
    }

    /**
     * Run the void addField(String,Object,Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAddField_4() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String name = "";
        Object value = new Object();
        Object type = new Object();

        fixture.addField(name, value, type);

        // add additional test code here
    }

    /**
     * Run the void addField(String,Object,Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testAddField_5() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String name = "";
        Object value = null;
        Object type = new Object();

        fixture.addField(name, value, type);

        // add additional test code here
    }

    /**
     * Run the String getAction() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetAction_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());

        String result = fixture.getAction();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the String getActor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetActor_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());

        String result = fixture.getActor();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the List<Field> getFields() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetFields_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());

        List<Field> result = fixture.getFields();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Run the String getOrigin() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetOrigin_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());

        String result = fixture.getOrigin();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the void setAction(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetAction_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String action = "";

        fixture.setAction(action);

        // add additional test code here
    }

    /**
     * Run the void setActor(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetActor_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String actor = "";

        fixture.setActor(actor);

        // add additional test code here
    }

    /**
     * Run the void setFields(List<Field>) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetFields_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        List<Field> fields = new ReadMostlyVector();

        fixture.setFields(fields);

        // add additional test code here
    }

    /**
     * Run the void setOrigin(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetOrigin_1() throws Exception {
        AuditEvent fixture = new AuditEvent("", "", "", new Field[] {});
        fixture.setFields(new ReadMostlyVector());
        String origin = "";

        fixture.setOrigin(origin);

        // add additional test code here
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *             if the initialization fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Before
    public void setUp() throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *             if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @After
    public void tearDown() throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args
     *            the command line arguments
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(AuditEventTest.class);
    }
}