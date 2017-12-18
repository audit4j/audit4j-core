package org.audit4j.core.io;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;

public class BatchProcessStream implements AuditOutputStream<AuditEvent>{

    AuditOutputStream<AuditEvent> outputStream;
    
    private int batchSize;
    private EventBatch batch = new EventBatch();
    
    
    public BatchProcessStream(AuditOutputStream<AuditEvent> outputStream, int batchSize) {
        this.outputStream = outputStream;
        this.batchSize = batchSize;
    }
    
    @Override
    public AuditOutputStream<AuditEvent> write(AuditEvent event) {
        if (batch.size() < batchSize - 1) {
            batch.addEvent(event);
        } else {
            batch.addEvent(event);
            writeBatch(batch);
            batch = new EventBatch();
        }
        
        return this;
    }

    @Override
    public AuditOutputStream<AuditEvent> writeBatch(EventBatch batch) {
         outputStream.writeBatch(batch);
         return this;
    }

    @Override
    public void close() {
        if (!batch.isEmpty()) {
            writeBatch(batch);
        }
    }

    @Override
    public Object clone() {
        return null;
    }
}
