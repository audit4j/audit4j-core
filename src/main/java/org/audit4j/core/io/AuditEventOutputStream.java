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

import org.audit4j.core.TroubleshootManager;
import org.audit4j.core.ValidationManager;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.exception.ValidationException;

/**
 * The Class AuditEventOutputStream.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0.0
 */
public class AuditEventOutputStream implements AuditOutputStream {

    /** The output stream. */
    AuditOutputStream outputStream;

    /**
     * Instantiates a new audit event output stream.
     * 
     * @param outputStream
     *            the output stream
     */
    public AuditEventOutputStream(AuditOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
     * )
     */
    @Override
    public AuditEventOutputStream write(AuditEvent event) {
        try {
            ValidationManager.validateEvent(event);
            outputStream.write(event);
        } catch (ValidationException e) {
            try {
                TroubleshootManager.troubleshootEvent(event);
                outputStream.write(event);
            } catch (TroubleshootException e2) {

            }
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    @Override
    public void close() {
        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
    }

    @Override
    public Object clone() {
        return null;
    }

}
