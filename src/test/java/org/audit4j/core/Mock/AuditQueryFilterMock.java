package org.audit4j.core.Mock;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.filter.AuditEventFilter;

public class AuditQueryFilterMock implements AuditEventFilter<AuditEvent>{
    @Override
    public boolean accepts(AuditEvent event) {
        return query.from(event).with("action").eq("Filter").evaluate();
    }
}
