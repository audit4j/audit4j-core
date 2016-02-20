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

package org.audit4j.core.schedule.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class SecureLayout.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class ClassUtils {
	
	/**
     * private constructor to avoid instantiation of this class
     */
    private ClassUtils(){
    	
    }

    /**
     * Determine whether the given class has a public method with the given
     * signature.
     * <p>
     * Essentially translates {@code NoSuchMethodException} to "false".
     * 
     * @param clazz
     *            the clazz to analyze
     * @param methodName
     *            the name of the method
     * @param paramTypes
     *            the parameter types of the method
     * @return whether the class has a corresponding method
     * @see Class#getMethod
     */
    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        return getMethodIfAvailable(clazz, methodName, paramTypes) != null;
    }

    /**
     * Determine whether the given class has a public method with the given
     * signature, and return it if available (else return {@code null}).
     * <p>
     * In case of any signature specified, only returns the method if there is a
     * unique candidate, i.e. a single public method with the specified name.
     * <p>
     * Essentially translates {@code NoSuchMethodException} to {@code null}.
     * 
     * @param clazz
     *            the clazz to analyze
     * @param methodName
     *            the name of the method
     * @param paramTypes
     *            the parameter types of the method (may be {@code null} to
     *            indicate any signature)
     * @return the method, or {@code null} if not found
     * @see Class#getMethod
     */
    public static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        // Assert.notNull(clazz, "Class must not be null");
        // Assert.notNull(methodName, "Method name must not be null");
        if (paramTypes != null) {
            try {
                return clazz.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException ex) {
                return null;
            }
        } else {
            Set<Method> candidates = new HashSet<Method>(1);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    candidates.add(method);
                }
            }
            if (candidates.size() == 1) {
                return candidates.iterator().next();
            }
            return null;
        }
    }

    public static String getShortName(Class<? extends CustomizableThreadCreator> class1) {
        // TODO Auto-generated method stub
        return null;
    }
}
