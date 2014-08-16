package org.audit4j.core.handler.file;


public abstract class AuditFileWriter {
	public abstract AuditFileWriter write(String event);
	
	public abstract void init();
}
