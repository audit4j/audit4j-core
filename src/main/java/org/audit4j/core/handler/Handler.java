/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
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

import org.audit4j.core.dto.AuditEvent;

/**
 * The Class Handler.
 * 
 * @author Janith Bandara
 */
public abstract class Handler {

	/** The query. */
	private String query;

	/** The audit event. */
	private AuditEvent auditEvent;
	
	/**
	 * Close.
	 */
	public void close(){
		query = null;
		auditEvent = null;
	}

	/**
	 * Inits the.
	 *
	 * @return true, if successful
	 */
	public abstract boolean init();
	
	/**
	 * Handle.
	 */
	public abstract void handle();
	

	/**
	 * Gets the user identifier.
	 * 
	 * @return the user identifier
	 */
	public abstract String getUserIdentifier();

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public abstract String getUserName();

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
}
