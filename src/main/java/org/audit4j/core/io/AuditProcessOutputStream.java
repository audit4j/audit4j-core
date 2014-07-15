package org.audit4j.core.io;

import org.audit4j.core.AuditEventProcessor;
import org.audit4j.core.AuditProcessor;
import org.audit4j.core.Context;
import org.audit4j.core.dto.AuditEvent;

public class AuditProcessOutputStream implements AuditOutputStream {
	public Context context;
	
	public AuditProcessOutputStream(Context context){
		this.context = context;
	}
	
	@Override
	public AuditProcessOutputStream write(AuditEvent event) {
		AuditProcessor<AuditEvent> processor = AuditEventProcessor.getInstance();
		processor.setConf(context.getConfig());
		processor.process(event);
		return this;
	}

	@Override
	public void close() {

	}

	@Override
	public Object clone() {
		return null;
	}
}
