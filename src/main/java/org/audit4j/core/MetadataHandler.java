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

/**
 * The Class MetadataHandler. This class is used to enhance audit event with
 * metadata gatherd from external sources. Metadata implementation is getting
 * from configurations.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class MetadataHandler {

    /**
     * Enhance from metadata.
     * 
     * @param event
     *            the event
     * @return the audit event
     */
    public AuditEvent enhanceFromMetadata(final AuditEvent event) {
        if (event.getActor() == null && Context.getConfigContext().hasMetadata()) {
            event.setActor(Context.getConfigContext().getMetaData().getActor());
        } else if (event.getActor() == null) {
            event.setActor(CoreConstants.DEFAULT_ACTOR);
        }

        if (event.getOrigin() == null && Context.getConfigContext().hasMetadata()) {
            event.setOrigin(Context.getConfigContext().getMetaData().getOrigin());
        } else if (event.getOrigin() == null) {
            event.setOrigin(CoreConstants.DEFAULT_ORIGIN);
        }
        return event;
    }
    
    /**
     * Enhance from metadata.
     * 
     * @param batch
     *            the Event Batch
     * @return the Event Batch
     */
    public EventBatch enhanceFromMetadata(final EventBatch<AuditEvent> batch) {
        for (AuditEvent event : batch) {
            enhanceFromMetadata((AuditEvent)event);
        }
        return batch;
    }
}
