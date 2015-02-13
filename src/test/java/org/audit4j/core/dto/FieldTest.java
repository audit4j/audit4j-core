package org.audit4j.core.dto;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FieldTest</code> contains tests for the class <code>{@link Field}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:27 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class FieldTest {
    /**
     * Run the Field() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testField_1()
        throws Exception {

        Field result = new Field();

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getName());
        assertEquals(null, result.getValue());
        assertEquals(null, result.getType());
    }

    /**
     * Run the Field(String,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testField_2()
        throws Exception {
        String name = "";
        String value = "";

        Field result = new Field(name, value);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals("", result.getValue());
        assertEquals("java.lang.String", result.getType());
    }

    /**
     * Run the Field(String,String,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testField_3()
        throws Exception {
        String name = "";
        String value = "";
        String type = "";

        Field result = new Field(name, value, type);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals("", result.getValue());
        assertEquals("", result.getType());
    }

    /**
     * Run the String getName() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetName_1()
        throws Exception {
        Field fixture = new Field("", "", "");

        String result = fixture.getName();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the String getType() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetType_1()
        throws Exception {
        Field fixture = new Field("", "", "");

        String result = fixture.getType();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the String getValue() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testGetValue_1()
        throws Exception {
        Field fixture = new Field("", "", "");

        String result = fixture.getValue();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the void setName(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetName_1()
        throws Exception {
        Field fixture = new Field("", "", "");
        String name = "";

        fixture.setName(name);

        // add additional test code here
    }

    /**
     * Run the void setType(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetType_1()
        throws Exception {
        Field fixture = new Field("", "", "");
        String type = "";

        fixture.setType(type);

        // add additional test code here
    }

    /**
     * Run the void setValue(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 2/4/15 9:27 AM
     */
    @Test
    public void testSetValue_1()
        throws Exception {
        Field fixture = new Field("", "", "");
        String value = "";

        fixture.setValue(value);

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
        new org.junit.runner.JUnitCore().run(FieldTest.class);
    }
}