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

    private JMXConfig jmxConfig;

    @Override
    public void init() throws InitializationException {
        Log.info("Initializing JMX monitoring...");
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public void registerMbeans() {
        ServerAdminMBean serverAdministrationBean = new ServerAdmin();
        try {

            mbeanServer.registerMBean(serverAdministrationBean,
                    JMXUtils.getObjectName(jmxConfig.getContextName(), "ServerAdmin"));
        } catch (InstanceAlreadyExistsException e) {
            throw new InitializationException("Could not initialize the MBean.!", e);
        } catch (MBeanRegistrationException e) {
            throw new InitializationException("Could not initialize the MBean.!", e);
        } catch (NotCompliantMBeanException e) {
            throw new InitializationException("Could not initialize the MBean.!", e);
        }

    }

    @Override
    public void stop() {
        // mbeanServer.unregisterMBean(name)
    }

    public void setJmxConfig(JMXConfig jmxConfig) {
        this.jmxConfig = jmxConfig;
    }
}
