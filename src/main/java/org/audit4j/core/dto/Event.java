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

package org.audit4j.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class AuditBase.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class Event implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8609566097133360680L;

    /** The uuid. */
    private Long uuid;

    /** The timestamp. */
    private Date timestamp;

    /** The meta. */
    private EventMeta meta;

    /**
     * Instantiates a new event.
     */
    public Event() {
        uuid = java.util.UUID.randomUUID().getMostSignificantBits();
        this.timestamp = new Date();
    }

    /**
     * Gets the uuid.
     * 
     * @return the uuid
     */
    public Long getUuid() {
        return this.uuid;
    }

    /**
     * Sets the uuid.
     * 
     * @param uuid
     *            the new uuid
     */
    public void setUuid(final Long uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the timestamp.
     * 
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     * 
     * @param timestamp
     *            the new timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the repository.
     *
     * @param repository
     *            the new repository
     */
    public void setRepository(String repository) {
        if (meta == null) {
            meta = new EventMeta();
        }
        meta.setRepository(repository);
    }

    /**
     * Sets the client.
     *
     * @param client
     *            the new client
     */
    public void setClient(String client) {
        if (meta == null) {
            meta = new EventMeta();
        }
        meta.setClient(client);
    }

    /**
     * Gets the meta.
     * 
     * @return the meta
     */
    public EventMeta getMeta() {
        return meta;
    }

    /**
     * Sets the meta.
     * 
     * @param meta
     *            the new meta
     */
    public void setMeta(EventMeta meta) {
        this.meta = meta;
    }
}
