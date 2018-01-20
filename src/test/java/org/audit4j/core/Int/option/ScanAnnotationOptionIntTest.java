package org.audit4j.core.Int.option;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.Int.IntTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//test class without @Test method put it in ignore until there is a test
@Ignore
public class ScanAnnotationOptionIntTest extends IntTestBase {

    @Before
    public void before() {
        Configuration config = getDefaultConfiguration();
        config.setCommands("-scanAnnotated=org.audit4j.core.Mock");
        AuditManager.startWithConfiguration(config);
    }

   /* @Test
    public void testFilter_filter_accepts() throws InterruptedException {
        watchStart("testFilter_filter_accepts");
        IAuditManager manager = AuditManager.getInstance();
        boolean status = manager.audit(getSampleAnnotationEvent());
        watchStop();
        assertTrue(status);
        TimeUnit.SECONDS.sleep(2);
    }*/
/*
    @Test
    public void testFilter_filter_denied() throws InterruptedException {
        watchStart("testFilter_filter_denied");
        IAuditManager manager = AuditManager.getInstance();
        boolean status = manager.audit(getSampleNullAnnotationEvent());
        watchStop();
        // assertFalse(status);
        TimeUnit.SECONDS.sleep(2);
    }*/

    @After
    public void after() {
        AuditManager.shutdown();
    }
}