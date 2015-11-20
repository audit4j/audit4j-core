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

package org.audit4j.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The Class AuditAnnotationAttributes.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
@Deprecated
public class AuditAnnotationAttributes {

    /** The Constant ACTION. */
    private final static String ACTION = "action";

    /** The Constant DEFAULT_REPOSITORY. */
    private final static String DEFAULT_REPOSITORY = "default";

    /**
     * Checks for annotation.
     * 
     * @param clazz
     *            the clazz
     * @return the boolean
     */
    public Boolean hasAnnotation(final Class<?> clazz) {
        return clazz.isAnnotationPresent(Audit.class);
    }

    /**
     * Checks for annotation.
     * 
     * @param method
     *            the method
     * @return the boolean
     */
    public Boolean hasAnnotation(final Method method) {
        return method.isAnnotationPresent(Audit.class);
    }

    public Audit getAnnotation(final Class<?> clazz){
        return clazz.getAnnotation(Audit.class);
    }
    
    public Audit getAnnotation(final Method method) {
        return method.getAnnotation(Audit.class);
    }
    /**
     * Gets the selection.
     * 
     * @param clazz
     *            the clazz
     * @return the selection
     */
    public SelectionType getSelection(final Class<?> clazz) {
        final Annotation[] annotations = clazz.getAnnotations();
        return getSelection(annotations);
    }

    /**
     * Gets the selection.
     * 
     * @param method
     *            the method
     * @return the selection
     */
    public SelectionType getSelection(final Method method) {
        final Annotation[] annotations = method.getAnnotations();
        return getSelection(annotations);
    }

    /**
     * Gets the selection.
     * 
     * @param annotations
     *            the annotations
     * @return the selection
     */
    private SelectionType getSelection(final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof Audit) {
                final Audit audit = (Audit) annotation;
                return audit.selection();
            }
        }
        return null;
    }

    /**
     * Gets the action.
     * 
     * @param clazz
     *            the clazz
     * @param method
     *            the method
     * @return the action
     */
    public String getAction(final Class<?> clazz, final Method method) {
        final Annotation[] annotations = clazz.getAnnotations();
        return this.getAction(annotations, method);
    }

    /**
     * Gets the action.
     * 
     * @param method
     *            the method
     * @return the action
     */
    public String getAction(final Method method) {
        final Annotation[] annotations = method.getAnnotations();
        return this.getAction(annotations, method);
    }
    
    /**
     * Gets the repository.
     *
     * @param clazz the clazz
     * @param method the method
     * @return the repository
     */
    public String getTag(final Class<?> clazz, final Method method) {
        final Annotation[] annotations = clazz.getAnnotations();
        return this.getTag(annotations, method);
    }

    /**
     * Gets the repository.
     *
     * @param method the method
     * @return the repository
     */
    public String getTag(final Method method) {
        final Annotation[] annotations = method.getAnnotations();
        return this.getTag(annotations, method);
    }

    /**
     * Gets the action.
     * 
     * @param annotations
     *            the annotations
     * @param method
     *            the method
     * @return the action
     */
    private String getAction(final Annotation[] annotations, final Method method) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof Audit) {
                final Audit audit = (Audit) annotation;
                String action = audit.action();
                if (ACTION.equals(action)) {
                    return method.getName();
                } else {
                    return action;
                }
            }
        }
        return null;
    }

    /**
     * Gets the repository.
     *
     * @param annotations the annotations
     * @param method the method
     * @return the repository
     */
    private String getTag(final Annotation[] annotations, final Method method) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof Audit) {
                final Audit audit = (Audit) annotation;
                return audit.tag();
            }
        }
        return null;
    }
}
