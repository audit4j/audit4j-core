package org.audit4j.core.io;

import org.audit4j.core.dto.AuditEvent;

public interface AuditOutputStream {

    public AuditOutputStream write(AuditEvent event);
    
    public void close();
}
