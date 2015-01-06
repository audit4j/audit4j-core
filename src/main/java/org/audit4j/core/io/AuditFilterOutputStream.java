package org.audit4j.core.io;

import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.filter.AuditEventFilter;

public class AuditFilterOutputStream implements AuditOutputStream {

	private final List<AuditEventFilter> filters;

	private final AuditOutputStream outputStream;

	public AuditFilterOutputStream(AuditOutputStream outputStream, List<AuditEventFilter> filters) {
		this.outputStream = outputStream;
		this.filters = filters;
	}

	@Override
	public AuditOutputStream write(AuditEvent event) {
		for (AuditEventFilter filter : filters) {
			if (filter.filter(event)) {
				return this;
			}
		}
		outputStream.write(event);
		return this;
	}

	@Override
	public void close() {
		outputStream.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		return null;
	}
}
