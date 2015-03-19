package org.audit4j.core.jmx;

import javax.management.ObjectName;

public class JMXUtils {

    static ObjectName getObjectName(String name) {
        try {
            return new ObjectName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
