package org.audit4j.core.Int;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.handler.Handler;

public class TestHandler extends Handler<AuditEvent>{

    private int eventCount = 0;
    
    private int batchCount = 0;
    
    private EventBatch<AuditEvent> lastBatch;
    
    private AuditEvent lastevent;
    
    private List<EventBatch<AuditEvent>> writtenBatches = new ArrayList<>();
    
    private List<AuditEvent> writtenEvents = new ArrayList<>();
    
    private boolean writeEventCalled = false;
    
    private boolean writeBatchCalled = false;
    
    @Override
    public void init() throws InitializationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
       eventCount = 0;
       batchCount = 0;
       writeEventCalled = false;
       writeBatchCalled = false;
       lastevent = null;
       lastBatch = null;
       writtenEvents.clear();
       writtenBatches.clear();
    }

    @Override
    public void handle() throws HandlerException {
        
    }

    public void handle(String formattedEvent) throws HandlerException {
        
    }

    @Override
    public void handle(AuditEvent event) throws HandlerException {
        eventCount++;
        writeEventCalled = true;
        lastevent = event;
        writtenEvents.add(event);
    }

    @Override
    public void handle(EventBatch<AuditEvent> batch) throws HandlerException {
        System.out.println("+++++++++++++++++++++++++++");
       batchCount++;
       writeBatchCalled = true;
       lastBatch = batch;
       writtenBatches.add(batch);
       System.out.println(writeBatchCalled);
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getBatchCount() {
        return batchCount;
    }

    public EventBatch<AuditEvent> getLastBatch() {
        return lastBatch;
    }

    public AuditEvent getLastevent() {
        return lastevent;
    }

    public List<EventBatch<AuditEvent>> getWrittenBatches() {
        return writtenBatches;
    }

    public List<AuditEvent> getWrittenEvents() {
        return writtenEvents;
    }

    public boolean isWriteEventCalled() {
        return writeEventCalled;
    }

    public boolean isWriteBatchCalled() {
        return writeBatchCalled;
    }
}
