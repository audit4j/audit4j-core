/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core;

import java.util.List;

import org.audit4j.core.annotation.AuditAnnotationAttributes;
import org.audit4j.core.annotation.AuditFieldAnnotationAttribute;
import org.audit4j.core.annotation.IgnoreAuditAnnotationAttributes;
import org.audit4j.core.annotation.SelectionType;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;

/**
 * The Class AnnotationTransformer.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.0.0
 */
public class AnnotationTransformer {

    /**
     * Transform annotation informations to Audit Event object.
     * 
     * @param annotationEvent
     *            the annotation event
     * @return the audit event
     * @since 2.0.0
     */
    public AuditEvent transformToEvent(AnnotationAuditEvent annotationEvent) {
        final AuditAnnotationAttributes auditAttributes = new AuditAnnotationAttributes();
        final IgnoreAuditAnnotationAttributes ignoreAttributes = new IgnoreAuditAnnotationAttributes();
        final AuditFieldAnnotationAttribute fieldAttributes = new AuditFieldAnnotationAttribute();
        List<Field> fields = null;
        AuditEvent event = null;
        String action = null;
        String tag = null;

        if (auditAttributes.hasAnnotation(annotationEvent.getClazz())
                && !ignoreAttributes.hasAnnotation(annotationEvent.getMethod())) {
            event = new AuditEvent();
            final SelectionType selection = auditAttributes.getSelection(annotationEvent.getClazz());
            if (selection.equals(SelectionType.ALL)) {
                fields = fieldAttributes.getAllFields(annotationEvent.getMethod(), annotationEvent.getArgs());
            } else if (selection.equals(SelectionType.MARKED)) {
                fields = fieldAttributes.getMarkedFields(annotationEvent.getMethod(), annotationEvent.getArgs());
            }
            action = auditAttributes.getAction(annotationEvent.getClazz(), annotationEvent.getMethod());
            tag = auditAttributes.getTag(annotationEvent.getClazz(), annotationEvent.getMethod());
            event.setAction(action);
            event.setTag(tag);
            event.setFields(fields);
        } else if (!auditAttributes.hasAnnotation(annotationEvent.getClazz())
                && auditAttributes.hasAnnotation(annotationEvent.getMethod())) {
            event = new AuditEvent();
            final SelectionType selection = auditAttributes.getSelection(annotationEvent.getMethod());
            if (selection.equals(SelectionType.ALL)) {
                fields = fieldAttributes.getAllFields(annotationEvent.getMethod(), annotationEvent.getArgs());
            } else if (selection.equals(SelectionType.MARKED)) {
                fields = fieldAttributes.getMarkedFields(annotationEvent.getMethod(), annotationEvent.getArgs());
            }
            action = auditAttributes.getAction(annotationEvent.getMethod());
            tag = auditAttributes.getTag(annotationEvent.getMethod());
            event.setAction(action);
            event.setTag(tag);
            event.setFields(fields);
        }
        return event;
    }
}
