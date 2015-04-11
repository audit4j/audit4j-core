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

package org.audit4j.core.handler;

import java.io.Serializable;
import java.util.Map;

import org.audit4j.core.Initializable;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.HandlerException;

/**
 * The Class Handler.
 * 
 * @author Janith Bandara
 */
public abstract class Handler implements Initializable, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8636058037478806582L;

    /** The query. */
    private String query;

    /** The audit event. */
    private AuditEvent auditEvent;

    /** The properties. */
    private Map<String, String> properties;

    /**
     * Close.
     */
    public void close() {
        query = null;
        auditEvent = null;
    }

    /**
     * Handle.
     * 
     * @throws HandlerException
     *             the handler exception
     */
    public abstract void handle() throws HandlerException;

    /**
     * Sets the query.
     * 
     * @param query
     *            the new query
     */
    public void setQuery(final String query) {
        this.query = query;
    }

    /**
     * Gets the audit event.
     * 
     * @return the audit event
     */
    public AuditEvent getAuditEvent() {
        return auditEvent;
    }

    /**
     * Sets the audit event.
     * 
     * @param auditEvent
     *            the new audit event
     */
    public void setAuditEvent(AuditEvent auditEvent) {
        this.auditEvent = auditEvent;
    }

    /**
     * Gets the query.
     * 
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Gets the property.
     * 
     * @param key
     *            the key
     * @return the property
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Sets the properties.
     * 
     * @param properties
     *            the properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
