package org.audit4j.core.jmx;

import javax.management.ObjectName;

public class JMXUtils {

    static ObjectName getObjectName(String type, String name) {
        try {
            return new ObjectName(JMXUtils.class.getPackage().getName() + ":type=" + type + ",name=" + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
