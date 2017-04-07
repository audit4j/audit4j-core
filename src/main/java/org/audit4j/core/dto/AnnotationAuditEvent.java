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

package org.audit4j.core.dto;

import java.lang.reflect.Method;

/**
 * The Class AnnotationAuditEvent.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class AnnotationAuditEvent extends AuditEvent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2830800466963787273L;

    /** The clazz. */
    private Class<?> clazz;

    /** The method. */
    Method method;

    /** The args. */
    Object[] args;

    /** The return of hte method, may be null */
    private Object methodCallResult;

    /**
     * Instantiates a new annotation audit event.
     */
    public AnnotationAuditEvent() {

    }

    /**
     * Instantiates a new annotation audit event.
     * 
     * @param clazz
     *            the clazz
     * @param method
     *            the method
     * @param args
     *            the args
     */
    public AnnotationAuditEvent(Class<?> clazz, Method method, Object[] args) {
        super();
        this.clazz = clazz;
        this.method = method;
        this.args = args;
    }

    public AnnotationAuditEvent(Class<?> clazz, Method method, Object[] args, Object methodCallResult) {
        this(clazz, method, args);
        this.methodCallResult = methodCallResult;
    }

    /**
     * Gets the clazz.
     * 
     * @return the clazz
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Sets the clazz.
     * 
     * @param clazz
     *            the new clazz
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Gets the method.
     * 
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Sets the method.
     * 
     * @param method
     *            the new method
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * Gets the args.
     * 
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Sets the args.
     * 
     * @param args
     *            the new args
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
     * Gets the return object of method call. May be {@code null} in the case the method was {@code void} or in the case
     * the @Before aspect was used and therefore no return type was available.
     *
     * @return the {@code Object} returned by the audited method, if captured
     */
    public Object getMethodCallResult() {
        return methodCallResult;
    }
}
