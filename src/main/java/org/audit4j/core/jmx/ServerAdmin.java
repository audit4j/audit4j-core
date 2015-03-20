package org.audit4j.core.jmx;

import org.audit4j.core.AuditManager;
import org.audit4j.core.ContextLifeCycle;

/**
 * The Class ServerAdminImpl.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @since
 */
public class ServerAdmin implements ServerAdminMBean {
    
    /** The life cycle. */
    private final ContextLifeCycle lifeCycle;

    /**
     * Instantiates a new server admin impl.
     */
    ServerAdmin() {
        this.lifeCycle = ContextLifeCycle.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#isAlive()
     *
     */
    @Override
    public boolean isAlive() {
        return lifeCycle.isAlive();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#serverStatus()
     *
     */
    @Override
    public String getServerStatus() {
        return ContextLifeCycle.getInstance().getStatus().getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#start()
     *
     */
    @Override
    public void start() {
        AuditManager.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#stop()
     *
     */
    @Override
    public void stop() {
        AuditManager.getInstance().shutdown();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#restart()
     *
     */
    @Override
    public void restart() {
        AuditManager.getInstance().shutdown();
        AuditManager.getInstance();
    }

    @Override
    public Long getStartupTime() {
       return lifeCycle.getStartUpTime();
    }


}
