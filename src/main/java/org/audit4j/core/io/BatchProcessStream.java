/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.io;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;

/**
 * This Stream used to incorporate audit events in to batches.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.6.0
 */
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
            batch = new EventBatch<AuditEvent>(); 
        }
        
        return this;
    }

    @Override
    public AuditOutputStream<AuditEvent> writeBatch(EventBatch<AuditEvent> batch) {
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
