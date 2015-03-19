package org.audit4j.core.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.audit4j.core.Initializable;
import org.audit4j.core.exception.InitializationException;

public class MBeanAgent implements Initializable {

    private static final int DEFAULT_NO_THREADS = 10;
    private static final String DEFAULT_SCHEMA = "default";

    private MBeanServer mbeanServer;

    @Override
    public void init() throws InitializationException {
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
        // register the MBean
        // SystemConfig mBean = new SystemConfig(DEFAULT_NO_THREADS,
        // DEFAULT_SCHEMA);
        ObjectName name = null;
        try {
            name = new ObjectName("com.journaldev.jmx:type=SystemConfig");

        } catch (MalformedObjectNameException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            mbs.registerMBean(null, name);
        } catch (InstanceAlreadyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void registerMbeans() {
        try {
            ObjectName name = new ObjectName("com.journaldev.jmx:type=SystemConfig");
            
        } catch (MalformedObjectNameException e) {
            throw new InitializationException("Could not obtain object name.", e);
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

}
