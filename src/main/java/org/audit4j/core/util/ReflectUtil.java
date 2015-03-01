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
