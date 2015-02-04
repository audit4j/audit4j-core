package org.audit4j.core.filter.impl;

import java.util.HashSet;
import java.util.Set;

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.filter.AuditAnnotationFilter;

public class ScanAnnotatedFilter implements AuditAnnotationFilter {

    public Set<Class<?>> classes = new HashSet<>();
    
    @Override
    public boolean accepts(AnnotationAuditEvent auditDto) {
        return classes.contains(auditDto.getClazz());
    }

    public void addClass(Class<?> clazz){
        classes.add(clazz);
    }
}
