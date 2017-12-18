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

import org.audit4j.core.dto.Event;
import org.audit4j.core.dto.EventBatch;

/**
 * The Interface AuditOutputStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0.0
 */
public interface AuditOutputStream<T extends Event> {

    /**
     * Write event.
     *
     * @param event audit event
     * 
     * @return the audit output stream
     */
    AuditOutputStream<T> write(T event);
    
    /**
     * Write batch of events.
     *
     * @param batch the Event Batch
     * 
     * @return the audit output stream
     */
    AuditOutputStream<T> writeBatch(EventBatch<T> batch);
    
    /**
     * Close.
     */
    void close();
    
    /**
     * Clone.
     *
     * @return the object
     */
    Object clone();
}
