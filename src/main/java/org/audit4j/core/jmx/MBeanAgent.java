package org.audit4j.core.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;

import org.audit4j.core.Initializable;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.Log;

/**
 * The Class MBeanAgent.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class MBeanAgent implements Initializable {

    /** The mbean server. */
    private MBeanServer mbeanServer;

    /** The jmx config. */
    private JMXConfig jmxConfig;

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#init()
     *
     */
    @Override
    public void init() throws InitializationException {
        Log.info("Initializing JMX...");
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    /**
     * Register mbeans.
     */
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

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#stop()
     *
     */
    @Override
    public void stop() {
        // mbeanServer.unregisterMBean(name)
    }

    /**
     * Sets the jmx config.
     *
     * @param jmxConfig the new jmx config
     */
    public void setJmxConfig(JMXConfig jmxConfig) {
        this.jmxConfig = jmxConfig;
    }
}
