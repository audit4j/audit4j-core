package org.audit4j.core.jmx;

import javax.management.ObjectName;

/**
 * The Class JMXUtils.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class JMXUtils {

    /**
     * Gets the object name.
     *
     * @param type the type
     * @param name the name
     * @return the object name
     */
    static ObjectName getObjectName(String type, String name) {
        try {
            return new ObjectName(JMXUtils.class.getPackage().getName() + ":type=" + type + ",name=" + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
