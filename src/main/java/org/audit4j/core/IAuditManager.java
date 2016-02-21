package org.audit4j.core;

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import java.lang.reflect.Method;

/**
 * IAuditManager. This interface describes actions available for AuditManager
 * implementations.
 *
 * @since ?.?.?
 */
public interface IAuditManager {

    /**
     * Audit.
     *
     * @param event
     *            the event
     * @return true, if successful
     */
    boolean audit(AuditEvent event);

    /**
     * Audit with annotation.
     *
     * @param clazz
     *            the clazz
     * @param method
     *            the method
     * @param args
     *            the args
     * @return true, if successful
     *
     */
    boolean audit(Class<?> clazz, Method method, Object[] args);

    /**
     * Audit.
     *
     * @param annotationEvent
     *            the annotation event
     * @return true, if successful
     */
    boolean audit(AnnotationAuditEvent annotationEvent);

//    /**
//     * Enable.
//     */
//    void enable();
//
//    /**
//     * Disable.
//     */
//    void disable();
//
//    /**
//     * Shutdown.
//     */
//    void shutdown();
}
