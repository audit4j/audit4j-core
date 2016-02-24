package org.audit4j.core.Int.configuration;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.Int.IntTestBase;
import org.junit.After;
import org.junit.Test;

public class InjectConfigurationIntTest extends IntTestBase{

    @Test
    public void testStartWithConfiguration() {
        AuditManager.startWithConfiguration(Configuration.DEFAULT);
        AuditManager.getInstance().audit(getSampleAuditEvent());
    }

    @After
    public void after() {
        AuditManager.shutdown();
    }
}
