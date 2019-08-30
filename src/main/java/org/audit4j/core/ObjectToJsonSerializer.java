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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.DeIdentifyUtil;
import org.audit4j.core.annotation.IgnoreAudit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * The Class ObjectToJsonSerializer.
 *
 * @author Ziwen Yang
 */
public final class ObjectToJsonSerializer implements ObjectSerializer {

    /* (non-Javadoc)
     * @see org.audit4j.core.ObjectSerializer#serialize(java.util.List, java.lang.Object, java.lang.String, org.audit4j.core.annotation.DeIdentify)
     */
    @Override
    public void serialize(List<org.audit4j.core.dto.Field> auditFields, Object object,
            String objectName, DeIdentify deidentify) {
        String name = '"' + objectName + '"';
        String json = toJson(object, deidentify);
        String fieldType = object == null ? CoreConstants.NULL : object.getClass().getName();
        auditFields.add(new org.audit4j.core.dto.Field(name, json, fieldType));
    }

    /**
     * Converts an object to json.
     *
     * @param object        an object
     * @param deidentify    the deidentify
     * @return a json string of the object
     */
    public final static String toJson(Object object, DeIdentify deidentify) {
        if (isPrimitive(object)) {
            Object deidentifiedObj = deidentifyObject(object, deidentify);
            String primitiveValue = String.valueOf(deidentifiedObj);
            if (object instanceof String || object instanceof Character || !object.equals(deidentifiedObj)) {
                primitiveValue = '"' + primitiveValue + '"';
            }
            return primitiveValue;
        }
        return JSON.toJSONString(object, JsonFilter.INSTANCE);
    }

    /**
     * Checks if is primitive.
     *
     * @param object        the object
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
     * Deidentify object
     *
     * @param object        the object to deidentify
     * @param deidentify    deidentify definition
     * @return Object
     */
    public final static Object deidentifyObject(Object object, DeIdentify deidentify) {
        if (object == null || deidentify == null) {
            return object;
        }
        return DeIdentifyUtil.deidentify(String.valueOf(object),
                deidentify.left(), deidentify.right(),
                deidentify.fromLeft(), deidentify.fromRight());
    }


    /**
     * The Class JsonFilter
     *
     * It helps to mask or exclude object fields which are marked with certain annotations
     */
    static class JsonFilter implements PropertyFilter, ValueFilter {

        private static final ConcurrentMap<String, Map<String, DeIdentify>> DEIDENTIFY_CACHE =
                new ConcurrentHashMap<String, Map<String, DeIdentify>>();

        private static final ConcurrentMap<String, Map<String, Boolean>> IGNORE_AUDIT_CACHE =
                new ConcurrentHashMap<String, Map<String, Boolean>>();

        static final JsonFilter INSTANCE = new JsonFilter();

        private JsonFilter() {}

        /**
         * Value will be processed if there is a DeIdentify annotation on top of the field
         *
         * @param object    an object
         * @param name      name of the object field
         * @param value     value of the object field
         * @return the processed value of the object field
         */
        @Override
        public Object process(Object object, String name, Object value) {
            Class<?> clazz = object.getClass();
            String key = clazz.getName();
            Map<String, DeIdentify> deidentifyMap = DEIDENTIFY_CACHE.get(key);
            if (deidentifyMap == null) {
                DEIDENTIFY_CACHE.putIfAbsent(key, createDeIdentifyMapping(clazz));
                deidentifyMap = DEIDENTIFY_CACHE.get(key);
            }
            return deidentifyObject(value, deidentifyMap.get(name));
        }

        /**
         * Field will be excluded if there is an IgnoreAudit annotation on top of the field
         *
         * @param object    an object
         * @param name      name of the object field
         * @param value     value of the object field
         * @return true if this field should be serialized, otherwise it will be ignored
         */
        @Override
        public boolean apply(Object object, String name, Object value) {
            Class<?> clazz = object.getClass();
            String key = clazz.getName();
            Map<String, Boolean> ignoreAuditMap = IGNORE_AUDIT_CACHE.get(key);
            if (ignoreAuditMap == null) {
                IGNORE_AUDIT_CACHE.putIfAbsent(key, createIgnoreAuditMapping(clazz));
                ignoreAuditMap = IGNORE_AUDIT_CACHE.get(key);
            }
            return !Boolean.TRUE.equals(ignoreAuditMap.get(name));
        }

        private static Map<String, DeIdentify> createDeIdentifyMapping(Class<?> clazz) {
            Map<String, DeIdentify> mapping = new HashMap<String, DeIdentify>();
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.isAnnotationPresent(DeIdentify.class)) {
                    mapping.put(field.getName(), field.getAnnotation(DeIdentify.class));
                }
            }
            if (mapping.isEmpty()) {
                mapping = Collections.emptyMap();
            }
            return mapping;
        }

        private static Map<String, Boolean> createIgnoreAuditMapping(Class<?> clazz) {
            Map<String, Boolean> mapping = new HashMap<String, Boolean>();
            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (!field.isAnnotationPresent(IgnoreAudit.class)) {
                    continue;
                }
                mapping.put(field.getName(), Boolean.TRUE);
            }
            if (mapping.isEmpty()) {
                mapping = Collections.emptyMap();
            }
            return mapping;
        }

    }

}
