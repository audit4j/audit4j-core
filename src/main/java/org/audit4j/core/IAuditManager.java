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

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import java.lang.reflect.Method;

/**
 * IAuditManager. This interface describes actions available for AuditManager
 * implementations.
 *
 * @since 2.5.0
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
