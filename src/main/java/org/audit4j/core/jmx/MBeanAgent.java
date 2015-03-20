package org.audit4j.core.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;

import org.audit4j.core.Initializable;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.Log;

public class MBeanAgent implements Initializable {

    private MBeanServer mbeanServer;

    @Override
    public void init() throws InitializationException {
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public void registerMbeans() {
        ServerAdminMBean serverAdministrationBean = new ServerAdmin();
        try {
            Log.info("Starting Mbeans");
            mbeanServer.registerMBean(serverAdministrationBean, JMXUtils.getObjectName(serverAdministrationBean));
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

    @Override
    public void stop() {
        // mbeanServer.unregisterMBean(name)
    }

}
