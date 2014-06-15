package org.audit4j.core.dto;

import java.io.Serializable;

import org.audit4j.core.Configuration;

public class AsyncAuditMessage implements Serializable{

	/**
	 * asdas
	 */
	private static final long serialVersionUID = 8106654656897552808L;

	private AuditEvent event;
	
	private Configuration conf;

	public AuditEvent getEvent() {
		return event;
	}

	public void setEvent(AuditEvent event) {
		this.event = event;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}
}
