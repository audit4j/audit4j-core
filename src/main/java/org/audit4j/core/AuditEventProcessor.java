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

package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.util.Log;

/**
 * This class is used to process audit events. Processing includes, formatting,
 * validating and execute handlers.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0
 */
public class AuditEventProcessor {

    /** The conf. */
    private ConcurrentConfigurationContext configContext;

    /**
     * Process the audit event.
     * 
     * @param event
     *            the event
     */
    public void process(AuditEvent event) {
            executeHandlers(event);
    }

    
    /**
     * Process the audit event.
     * 
     * @param batch the Event Batch
     */
    public void processBatch(EventBatch batch) {
            executeHandlers(batch);
    }
    
    /**
     * Execute handlers.
     * 
     * @param event
     *            the event
     */
    void executeHandlers(AuditEvent event) {
        String formattedEvent = configContext.getLayout().format(event);
        for (final Handler handler : configContext.getHandlers()) {
            handler.setAuditEvent(event);
            handler.setQuery(formattedEvent);
            try {
                handler.handle();
                handler.handle(event);
                handler.handle(formattedEvent);
            } catch (HandlerException e) {
                Log.warn("Failed to submit audit event.", e);
            }
        }
    }
    
    /**
     * Execute handlers.
     * 
     * @param batch 
     *           the Event Batch
     */
    void executeHandlers(EventBatch<AuditEvent> batch) {
        String formattedEvent = "";
        for (AuditEvent auditEvent : batch) {
            formattedEvent = formattedEvent + configContext.getLayout().format(auditEvent) + '\n';
        }
        for (final Handler handler : configContext.getHandlers()) {
            handler.setEventBatch(batch);
            handler.setQuery(formattedEvent);
            try {
                //handler.handle();
                handler.handle(batch);
                handler.handle(formattedEvent);
            } catch (HandlerException e) {
                Log.warn("Failed to submit audit event.", e);
            }
        }
    }


    /**
     * Sets the config context.
     * 
     * @param configContext
     *            the new config context
     */
    public void setConfigContext(ConcurrentConfigurationContext configContext) {
        this.configContext = configContext;
    }
}
