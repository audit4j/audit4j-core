package org.audit4j.core.io;

import java.util.List;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.junit.Assert;
import org.junit.Test;

public class BatchProcessStreamTest extends Audit4jTestBase {

    @Test
    public void testSingleBatch() {     
        MockOutputStream mockStream = new MockOutputStream();
        BatchProcessStream stream = new BatchProcessStream(mockStream, 4);
        stream.write(getSampleAuditEvent());
        stream.write(getSampleAuditEvent());
        stream.write(getSampleAuditEvent());
        stream.write(getSampleAuditEvent());
        Assert.assertTrue(!mockStream.isWriteCalled());
        Assert.assertTrue(mockStream.isWriteBatchCalled());
        Assert.assertEquals(1, mockStream.getBatchCount());
    }
    
    
    @Test
    public void testMultipleBatches() {     
        MockOutputStream mockStream = new MockOutputStream();
        BatchProcessStream stream = new BatchProcessStream(mockStream, 4);
        stream.write(getSampleAuditEvent("action1"));
        stream.write(getSampleAuditEvent("action2"));
        stream.write(getSampleAuditEvent("action3"));
        stream.write(getSampleAuditEvent("action4"));
        stream.write(getSampleAuditEvent("action5"));
        stream.write(getSampleAuditEvent("action6"));  
        stream.write(getSampleAuditEvent("action7"));  
        stream.write(getSampleAuditEvent("action8"));  
        stream.write(getSampleAuditEvent("action9"));  
        stream.write(getSampleAuditEvent("action10"));  
        stream.write(getSampleAuditEvent("action11"));  
        stream.write(getSampleAuditEvent("action12"));  
        Assert.assertEquals(3, mockStream.getBatchCount());
        
        List<EventBatch> batches = mockStream.getWrittenBatches();
        Assert.assertEquals("action1", ((AuditEvent) batches.get(0).get(0)).getAction());
        Assert.assertEquals("action2", ((AuditEvent) batches.get(0).get(1)).getAction());
        Assert.assertEquals("action3", ((AuditEvent) batches.get(0).get(2)).getAction());
        Assert.assertEquals("action4", ((AuditEvent) batches.get(0).get(3)).getAction());
        Assert.assertEquals("action5", ((AuditEvent) batches.get(1).get(0)).getAction());
        Assert.assertEquals("action6", ((AuditEvent) batches.get(1).get(1)).getAction());
    }
    
    @Test
    public void testCloseStream() {     
        MockOutputStream mockStream = new MockOutputStream();
        BatchProcessStream stream = new BatchProcessStream(mockStream, 4);
        stream.write(getSampleAuditEvent());
        stream.write(getSampleAuditEvent());
        stream.write(getSampleAuditEvent());
        Assert.assertTrue(!mockStream.isWriteBatchCalled());
        Assert.assertEquals(0, mockStream.getBatchCount());
        
        stream.close();
        
        Assert.assertTrue(!mockStream.isWriteCalled());
        Assert.assertTrue(mockStream.isWriteBatchCalled());
        Assert.assertEquals(1, mockStream.getBatchCount());
    }
}
