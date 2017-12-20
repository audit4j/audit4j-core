package org.audit4j.core.io;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;

public class MockOutputStream implements AuditOutputStream<AuditEvent>{

    private int eventCount = 0;
    
    private int batchCount = 0;
    
    private EventBatch lastBatch;
    
    private List<EventBatch> writtenBatches = new ArrayList<>();
    
    private boolean writeCalled = false;
    
    private boolean writeBatchCalled = false;
    
    @Override
    public AuditOutputStream<AuditEvent> write(AuditEvent event) {
        eventCount++;
        writeCalled = true;
        return this;
    }

    @Override
    public AuditOutputStream<AuditEvent> writeBatch(EventBatch batch) {
       System.out.println(batch.toString());
       lastBatch = batch;
       writtenBatches.add(batch);
       writeBatchCalled = true;
       batchCount++;
        return this;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }
    
    public boolean isWriteCalled() {
        return writeCalled;
    }

    public boolean isWriteBatchCalled() {
        return writeBatchCalled;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getBatchCount() {
        return batchCount;
    }

    public EventBatch getLastBatch() {
        return lastBatch;
    }

    public List<EventBatch> getWrittenBatches() {
        return writtenBatches;
    }

    @Override
    public Object clone() {
        return null;
    }

}
