package org.audit4j.core.smoke;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.Int.TestHandler;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.audit4j.core.io.BatchProcessStream;
import org.audit4j.core.io.MockOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BatchSmokeTest extends Audit4jTestBase{

    TestHandler testHandler;
    
    @Before
    public void setup() {
        Configuration config = getDefaultConfiguration();
        config.setCommands("-batchSize=4");
        testHandler = new TestHandler();
        config.addHandler(testHandler);
        AuditManager.startWithConfiguration(config);
    }

    
    @Test
    public void testSingleBatch() throws InterruptedException {     
       IAuditManager instance = AuditManager.getInstance();
       instance.audit(getSampleAuditEvent());
       instance.audit(getSampleAuditEvent());
       instance.audit(getSampleAuditEvent());
       instance.audit(getSampleAuditEvent());
        //Assert.assertTrue(!testHandler.isWriteEventCalled());
      //  Assert.assertTrue(testHandler.isWriteBatchCalled());
      ///  Assert.assertEquals(1, testHandler.getBatchCount());
        
        TimeUnit.SECONDS.sleep(4);
    }
    
    
    @Test
    public void testMultipleBatches() {     
        IAuditManager instance = AuditManager.getInstance();
        instance.audit(getSampleAuditEvent("action1"));
        instance.audit(getSampleAuditEvent("action2"));
        instance.audit(getSampleAuditEvent("action3"));
        instance.audit(getSampleAuditEvent("action4"));
        instance.audit(getSampleAuditEvent("action5"));
        instance.audit(getSampleAuditEvent("action6"));  
        instance.audit(getSampleAuditEvent("action7"));  
        instance.audit(getSampleAuditEvent("action8"));  
        instance.audit(getSampleAuditEvent("action9"));  
        instance.audit(getSampleAuditEvent("action10"));  
        instance.audit(getSampleAuditEvent("action11"));  
        instance.audit(getSampleAuditEvent("action12"));  
        
    }
    
  /*  @Test
    public void testCloseStream() {     
        MockOutputStream mockStream = new MockOutputStream();
        BatchProcessStream stream = new BatchProcessStream(mockStream, 4);
        instance.audit(getSampleAuditEvent());
        instance.audit(getSampleAuditEvent());
        instance.audit(getSampleAuditEvent());
        Assert.assertTrue(!mockStream.isWriteBatchCalled());
        Assert.assertEquals(0, mockStream.getBatchCount());
        
        stream.close();
        
        Assert.assertTrue(!mockStream.isWriteCalled());
        Assert.assertTrue(mockStream.isWriteBatchCalled());
        Assert.assertEquals(1, mockStream.getBatchCount());
    }*/
    
    
    @After
    public void after() {
        AuditManager.shutdown();
    }
}
