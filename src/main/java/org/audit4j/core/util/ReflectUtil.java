package org.audit4j.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The Class ReflectUtil.
 *
 * @param <T> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ReflectUtil<T> {

    /**
     * Gets the new instance.
     *
     * @param clazz the clazz
     * @return the new instance
     */
    public T getNewInstance(Class<T> clazz) {
        Constructor<T> ctor;
        try {
            ctor = clazz.getConstructor();
            return ctor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the new instance.
     *
     * @param className the class name
     * @return the new instance
     */
    public T getNewInstance(String className) {
        try {
            return getNewInstance((Class<T>) Class.forName(className));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
