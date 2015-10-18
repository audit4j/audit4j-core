package org.audit4j.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.layout.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>ConcurrentConfigurationContextTest</code> contains tests for the class <code>{@link ConcurrentConfigurationContext}</code>.
 *
 * @generatedBy CodePro at 2/4/15 9:28 AM
 * @author JanithB
 * @version $Revision: 1.0 $
 */
public class ConcurrentConfigurationContextTest {
    
    /**
     * Run the ConcurrentConfigurationContext() constructor test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testConcurrentConfigurationContext_1()
        throws Exception {
        ConcurrentConfigurationContext result = new ConcurrentConfigurationContext();
        assertNotNull(result);
        // add additional test code here
    }

    /**
     * Run the void addFilter(AuditEventFilter) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testAddFilter_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        AuditEventFilter filter = null;

        fixture.addFilter(filter);

        // add additional test code here
    }

    /**
     * Run the void addHandler(Handler) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testAddHandler_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        Handler handler = new ConsoleAuditHandler();

        fixture.addHandler(handler);

        // add additional test code here
    }

    /**
     * Run the void addProperty(String,String) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testAddProperty_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        String key = "";
        String value = "";

        fixture.addProperty(key, value);

        // add additional test code here
    }

    /**
     * Run the List<AuditEventFilter> getFilters() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetFilters_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        List<AuditEventFilter> result = fixture.getFilters();

        // add additional test code here
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Run the List<Handler> getHandlers() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetHandlers_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        List<Handler> result = fixture.getHandlers();

        // add additional test code here
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Run the Layout getLayout() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetLayout_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        Layout result = fixture.getLayout();

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the MetaData getMetaData() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetMetaData_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        MetaData result = fixture.getMetaData();

        // add additional test code here
        assertNotNull(result);
        assertEquals(CoreConstants.DEFAULT_ACTOR, result.getActor());
    }

    /**
     * Run the Map<String, String> getProperties() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetProperties_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        Map<String, String> result = fixture.getProperties();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Run the RunStatus getRunStatus() method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testGetRunStatus_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());

        RunStatus result = fixture.getRunStatus();

        // add additional test code here
        assertNotNull(result);
        assertEquals("DISABLED", result.name());
        assertEquals("DISABLED", result.toString());
        assertEquals(3, result.ordinal());
    }

    /**
     * Run the void setLayout(Layout) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSetLayout_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        Layout layout = new SimpleLayout();

        fixture.setLayout(layout);

        // add additional test code here
    }

    /**
     * Run the void setMetaData(MetaData) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSetMetaData_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        MetaData metaData = new DummyMetaData();

        fixture.setMetaData(metaData);

        // add additional test code here
    }

    /**
     * Run the void setRunStatus(RunStatus) method test.
     *
     * @throws Exception the exception
     * @generatedBy CodePro at 2/4/15 9:28 AM
     */
    @Test
    public void testSetRunStatus_1()
        throws Exception {
        ConcurrentConfigurationContext fixture = new ConcurrentConfigurationContext();
        fixture.setMetaData(new DummyMetaData());
        fixture.setRunStatus(RunStatus.DISABLED);
        fixture.setLayout(new SimpleLayout());
        fixture.addFilter((AuditEventFilter) null);
        fixture.addHandler(new ConsoleAuditHandler());
        RunStatus runStatus = RunStatus.DISABLED;

        fixture.setRunStatus(runStatus);

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
        new org.junit.runner.JUnitCore().run(ConcurrentConfigurationContextTest.class);
    }
}