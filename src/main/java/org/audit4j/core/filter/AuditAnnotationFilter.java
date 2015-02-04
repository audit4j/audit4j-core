package org.audit4j.core.filter;

import org.audit4j.core.dto.AnnotationAuditEvent;

public interface AuditAnnotationFilter {

    boolean accepts(AnnotationAuditEvent annotationEvent);

}
