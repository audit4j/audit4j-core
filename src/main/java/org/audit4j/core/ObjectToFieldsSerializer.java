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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.DeIdentifyUtil;
import org.audit4j.core.annotation.IgnoreAudit;
import org.audit4j.core.dto.Field;
import org.audit4j.core.exception.Audit4jRuntimeException;

/**
 * The Class ToStringGen.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.1
 */
public final class ObjectToFieldsSerializer implements ObjectSerializer {

    /** The Constant visited. */
    private static final ArrayList<Object> visited = new ArrayList<Object>();

    /**
     * Converts an object to a string representation that lists all fields.
     *
     * @param auditFields the audit fields
     * @param object an object
     * @param objectName the object name
     * @param deidentify the de-identify
     */
    public final void toFields(List<Field> auditFields, Object object, String objectName, DeIdentify deidentify) {
        String localOjectName = objectName;
        if (object == null) {
            auditFields.add(new Field(localOjectName, CoreConstants.NULL));
            return;
        }
        
        Class<?> clazz = object.getClass();
        if (!visited.contains(object)) {
            visited.add(object);
            if (isPrimitive(object)) {
                String primitiveValue = String.valueOf(object);
                if (deidentify != null) {
                    primitiveValue = DeIdentifyUtil.deidentify(primitiveValue, deidentify.left(), deidentify.right(),
                            deidentify.fromLeft(), deidentify.fromRight());
                }
                auditFields.add(new Field(localOjectName, primitiveValue, object.getClass()
                        .getName()));
            } else if (clazz.isArray()) {
                if (Array.getLength(object) == 0) {
                    auditFields.add(new Field(localOjectName + CoreConstants.DOLLAR_CHAR + clazz.getName(),
                            CoreConstants.EMPTY));
                } else {
                    // String internalLocalOjectName = localOjectName + CoreConstants.DOLLAR_CHAR +
                    // clazz.getName();
                    for (int i = 0; i < Array.getLength(object); i++) {
                        Object objVal = Array.get(object, i);
                        String internalLocalOjectName = localOjectName + CoreConstants.OPEN_BRACES_CHAR + "arg"
                                + i + CoreConstants.CLOSE_BRACES_CHAR;
                        if (clazz.getComponentType().isPrimitive())
                            auditFields.add(new Field(internalLocalOjectName, String
                                    .valueOf(objVal), objVal.getClass().getName()));
                        else if (objVal != null) {
                            toFields(auditFields, objVal, internalLocalOjectName, null);
                        }
                    }
                }
            } else if (object instanceof Collection<?>) {
                Collection<?> collection = (Collection<?>) object;
                if (collection.isEmpty()) {
                    auditFields.add(new Field(localOjectName + CoreConstants.DOLLAR_CHAR + clazz.getName(),
                            CoreConstants.EMPTY));
                } else {
                    String internalLocalOjectName = localOjectName + CoreConstants.DOLLAR_CHAR + object.getClass().getName();
                int i = 0;
                for (Object collectionObject : collection) {
                    String internalLocalOjectName2 = internalLocalOjectName +  CoreConstants.OPEN_BRACES_CHAR + "arg" + i
                            + CoreConstants.CLOSE_BRACES_CHAR;
                    if (isPrimitive(collectionObject)) {
                        auditFields.add(new Field(internalLocalOjectName2, String
                                .valueOf(collectionObject), collectionObject.getClass().getName()));
                    } else if (collectionObject != null) {
                        toFields(auditFields, collectionObject, internalLocalOjectName2, null);
                    }
                    i++;
                }
                }
            } else {
                String internalLocalOjectName = localOjectName + CoreConstants.DOLLAR_CHAR + clazz.getName();
                do {
                    java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
                    AccessibleObject.setAccessible(fields, true);
                    for (java.lang.reflect.Field field : fields) {
                        if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(IgnoreAudit.class)) {
                            String internalLocalOjectName2 = internalLocalOjectName + CoreConstants.DOLLAR_CHAR + field.getName();
                            boolean deidentifyFlag = false;
                            DeIdentify deidentifyAnn = null;
                            if (field.isAnnotationPresent(DeIdentify.class)) {
                                deidentifyAnn = field.getAnnotation(DeIdentify.class);
                                deidentifyFlag = true;
                            }
                            try {
                                Object objValue = field.get(object);
                                if (isPrimitive(object)) {
                                    String paramValue = String.valueOf(object);
                                    if (deidentifyFlag) {
                                        paramValue = DeIdentifyUtil.deidentify(paramValue, deidentifyAnn.left(),
                                                deidentifyAnn.right(), deidentifyAnn.fromLeft(),
                                                deidentifyAnn.fromRight());
                                    }
                                    auditFields.add(new Field(internalLocalOjectName2, paramValue,
                                            object.getClass().getName()));
                                } else {
                                    if (objValue != null) {
                                        toFields(auditFields, objValue, internalLocalOjectName2, deidentifyAnn);
                                    }
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
                        }
                    }
                    clazz = clazz.getSuperclass();
                } while (clazz != null);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.ObjectSerializer#serialize(java.util.List, java.lang.Object, java.lang.String, org.audit4j.core.annotation.DeIdentify)
     */
    @Override
    public void serialize(List<Field> auditFields, Object object,
            String objectName,  DeIdentify deidentify) {
        visited.clear();
       toFields(auditFields, object, objectName, deidentify);
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
