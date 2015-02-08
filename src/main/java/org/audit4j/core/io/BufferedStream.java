package org.audit4j.core.io;

import java.util.List;

import org.audit4j.core.dto.AuditEvent;

public class BufferedStream {

    private List<AuditEvent> events;
    
    public boolean write(AuditEvent event){
        events.add(event);
        return false;
    }
}
