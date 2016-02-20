/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.exception.InitializationException;

/**
 * The Class ReflectUtil.
 *
 * @param <I> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.1
 */
public class ReflectUtil<I> {

    /**
     * Gets the new instance.
     *
     * @param clazz the clazz
     * @return the new instance
     */
    public I getNewInstance(Class<I> clazz) {
        Constructor<I> ctor;
        try {
            ctor = clazz.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InitializationException("Given class not found", e);
        }
    }
    
    /**
     * Gets the new instance.
     *
     * @param className the class name
     * @return the new instance
     */
    @SuppressWarnings("unchecked")
    public I getNewInstance(String className) {
        try {
            return getNewInstance((Class<I>) Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new InitializationException("Given class not found", e);
        }
    }
    
    /**
     * Gets the new instance list.
     *
     * @param clsssList the clsss list
     * @return the new instance list
     */
    public List<I> getNewInstanceList(String[] clsssList) {
        List<I> instances = new ArrayList<>();
        for (String className : clsssList) {
            I instance = new ReflectUtil<I>().getNewInstance(className);
            instances.add(instance);
        }
        return instances;
    }
}
