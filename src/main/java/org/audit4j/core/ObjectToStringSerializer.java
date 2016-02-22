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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.DeIdentifyUtil;
import org.audit4j.core.annotation.IgnoreAudit;
import org.audit4j.core.exception.Audit4jRuntimeException;

/**
 * The Class ObjectToStringSerializer.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public final class ObjectToStringSerializer implements ObjectSerializer {

    /** The Constant visited. */
    private static final ArrayList<Object> visited = new ArrayList<Object>();

    /* (non-Javadoc)
     * @see org.audit4j.core.ObjectSerializer#serialize(java.util.List, java.lang.Object, java.lang.String, org.audit4j.core.annotation.DeIdentify)
     */
    @Override
    public void serialize(List<org.audit4j.core.dto.Field> auditFields, Object object,
            String objectName, DeIdentify deidentify) {
        visited.clear();
        String text = toString(object, deidentify);
        auditFields.add(new org.audit4j.core.dto.Field(objectName, text, object.getClass().getName()));
    }

    /**
     * Converts an object to a string representation that lists all fields.
     *
     * @param object            an object
     * @param deidentify the deidentify
     * @return a string with the object's class name and all field names and
     *         values
     */
    public final static String toString(Object object, DeIdentify deidentify) {
        StringBuilder builder = new StringBuilder();
        if (object == null) {
            return CoreConstants.NULL;
        }
        Class<?> clazz = object.getClass();
        if (isPrimitive(object)) {
            String primitiveValue = String.valueOf(object);
            if (deidentify != null) {
                primitiveValue = DeIdentifyUtil.deidentify(primitiveValue, deidentify.left(), deidentify.right(),
                        deidentify.fromLeft(), deidentify.fromRight());
            }
            return primitiveValue;
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
                        builder.append(toString(objVal, null));
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
                    if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(IgnoreAudit.class)) {
                        if (!builder.toString().endsWith(String.valueOf(CoreConstants.OPEN_BRACKETS_CHAR))) {
                            builder.append(CoreConstants.COMMA_CHAR);
                        }
                        builder.append(field.getName()).append(CoreConstants.EQ_CHAR);

                        String paramValue;
                        try {
                            Object objValue = field.get(object);
                            if (isPrimitive(object)) {
                                paramValue = String.valueOf(object);
                            } else {
                                paramValue = toString(objValue, null);
                            }
                        } catch (IllegalArgumentException e) {
                            throw new Audit4jRuntimeException(
                                    "Error due to converting object to string representation. ", e);
                        } catch (IllegalAccessException e) {
                            throw new Audit4jRuntimeException(
                                    "Error due to converting object to string representation. ", e);
                        } catch (Exception e) {
                            throw new Audit4jRuntimeException(
                                    "Error due to converting object to string representation. ", e);
                        }

                        if (field.isAnnotationPresent(DeIdentify.class)) {
                            DeIdentify deidentifyAnn = field.getAnnotation(DeIdentify.class);
                            paramValue = DeIdentifyUtil.deidentify(paramValue, deidentifyAnn.left(), deidentifyAnn.right(),
                                    deidentifyAnn.fromLeft(), deidentifyAnn.fromRight());
                        }
                        builder.append(paramValue);
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
}
