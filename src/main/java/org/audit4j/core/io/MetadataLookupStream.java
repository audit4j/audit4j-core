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

import org.audit4j.core.MetadataHandler;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBatch;

/**
 * The Class MetadataLookupStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class MetadataLookupStream implements AuditOutputStream<AuditEvent> {

    /** The handler. */
    MetadataHandler handler;
    
    /** The output stream. */
    AuditOutputStream<AuditEvent> outputStream;
    
    /**
     * Instantiates a new metadata lookup stream.
     *
     * @param outputStream the output stream
     */
    public MetadataLookupStream(AuditOutputStream<AuditEvent> outputStream) {
        this.outputStream = outputStream;
        handler = new MetadataHandler();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.Event)
     *
     */
    @Override
    public AuditOutputStream<AuditEvent> write(AuditEvent event) {
         outputStream.write(handler.enhanceFromMetadata(event));
         return outputStream;
    }
    
    @Override
    public AuditOutputStream<AuditEvent> writeBatch(EventBatch batch) {
        outputStream.writeBatch(handler.enhanceFromMetadata(batch));
        return outputStream;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     *
     */
    @Override
    public void close() {
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#clone()
     *
     */
    @Override
    public Object clone() {
        return null;
    }

}
