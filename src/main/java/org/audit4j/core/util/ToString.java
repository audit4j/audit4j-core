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

package org.audit4j.core.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.exception.Audit4jRuntimeException;

/**
 * The Class ToStringGen.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public final class ToString {

    /** The Constant visited. */
    private static final ArrayList<Object> visited = new ArrayList<Object>();

    /**
     * Converts an object to a string representation that lists all fields.
     * 
     * @param object
     *            an object
     * @return a string with the object's class name and all field names and
     *         values
     */
    public final static String toString(Object object) {
        StringBuilder builder = new StringBuilder();
        if (object == null) {
            return CoreConstants.NULL;
        }
        Class<?> clazz = object.getClass();
        if (isPrimitive(object)) {
            return String.valueOf(object);
        }

        if (visited.contains(object)) {
            return "...";
        }
        visited.add(object);
        if (clazz.isArray()) {
            if (Array.getLength(object) == 0) {
                builder.append(clazz.getComponentType()).append(CoreConstants.BRACKETS);
            } else {
                builder.append(clazz.getComponentType()).append(CoreConstants.BRACKETS)
                        .append(CoreConstants.OPEN_BRACES_CHAR);
                for (int i = 0; i < Array.getLength(object); i++) {
                    if (i > 0)
                        builder.append(CoreConstants.COMMA_CHAR);
                    Object objVal = Array.get(object, i);
                    if (clazz.getComponentType().isPrimitive())
                        builder.append(objVal);
                    else
                        builder.append(toString(objVal));
                }
                return builder.append(CoreConstants.CLOSE_BRACES_CHAR).toString();
            }
        } else {
            builder.append(clazz.getName());
            do {
                builder.append(CoreConstants.OPEN_BRACKETS_CHAR);
                Field[] fields = clazz.getDeclaredFields();
                AccessibleObject.setAccessible(fields, true);

                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        if (!builder.toString().endsWith(String.valueOf(CoreConstants.OPEN_BRACKETS_CHAR))) {
                            builder.append(CoreConstants.COMMA_CHAR);
                        }
                        builder.append(field.getName()).append(CoreConstants.EQ_CHAR);
                        try {
                            Object objValue = field.get(object);
                            if (isPrimitive(object)) {
                                builder.append(String.valueOf(object));
                            } else {
                                builder.append(toString(objValue));
                            }
                        } catch (Exception e) {
                            throw new Audit4jRuntimeException(
                                    "Error due to converting object to string representation. ", e);
                        }
                    }
                }
                builder.append(CoreConstants.CLOSE_BRACKETS_CHAR);
                clazz = clazz.getSuperclass();
            } while (clazz != null);
        }
        return builder.toString();
    }

    /**
     * Checks if is primitive.
     * 
     * @param object
     *            the object
     * @return true, if is primitive
     */
    public final static boolean isPrimitive(Object object) {
        if (object instanceof String || object instanceof Number || object instanceof Boolean
                || object instanceof Character) {
            return true;
        }
        return false;
    }

    /**
     * Checks for to string.
     * 
     * @param object
     *            the object
     * @return true, if successful
     */
    public final static boolean hasToStringMethod(Object object) {
        Class<?> clazz = object.getClass();
        try {
            Method method = clazz.getDeclaredMethod("toString");
            if (method != null) {
                return true;
            }
        } catch (NoSuchMethodException e) {
            return false;
        } catch (SecurityException e) {
            return false;
        }
        return false;

    }

    /**
     * Generate to string has not.
     * 
     * @param object
     *            the object
     * @return the string
     */
    public final static String toStringIfNotImplemented(Object object) {
        String paramValue;
        if (ToString.hasToStringMethod(object)) {
            paramValue = object.toString();
        } else {
            paramValue = ToString.toString(object);
        }
        return paramValue;
    }
}
