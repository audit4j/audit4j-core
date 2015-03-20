package org.audit4j.core.jmx;

import javax.management.ObjectName;

public class JMXUtils {

    static ObjectName getObjectName(ServerAdminMBean serverAdministrationBean) {
        try {
            return new ObjectName(JMXUtils.class.getPackage().getName() + ":type=admin,name=ServerAdmin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
