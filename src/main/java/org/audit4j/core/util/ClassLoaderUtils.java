package org.audit4j.core.util;

/**
 * The Class ClassLoaderUtils.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public final class ClassLoaderUtils {

    /**
     * Instantiates a new class loader utils.
     */
    private ClassLoaderUtils() {
        super();
    }

    /**
     * Gets the class loader.
     *
     * @param clazz the clazz
     * @return the class loader
     */
    public static ClassLoader getClassLoader(final Class<?> clazz) {
        // Context class loader can be null
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        if (clazz != null) {
            // The class loader for a specific class can also be null
            final ClassLoader clazzClassLoader = clazz.getClassLoader();
            if (clazzClassLoader != null) {
                return clazzClassLoader;
            }
        }
        // The only class loader we can rely on for not being null is the system
        // one
        return ClassLoader.getSystemClassLoader();
    }

}
