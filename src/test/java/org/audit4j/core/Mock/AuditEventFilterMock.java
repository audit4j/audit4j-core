package org.audit4j.core.Mock;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.filter.AuditEventFilter;

public class AuditEventFilterMock implements AuditEventFilter<AuditEvent> {
    @Override
    public boolean accepts(AuditEvent event) {
        if (event.getAction().equals("Filter")) {
            return false;
        }
        return true;
    }
}
