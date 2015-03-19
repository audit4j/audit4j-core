package org.audit4j.core.jmx;

import org.audit4j.core.AuditManager;
import org.audit4j.core.ContextLifeCycle;
import org.audit4j.core.RunStatus;

/**
 * The Class ServerAdminImpl.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @since
 */
public class ServerAdminImpl implements ServerAdmin {

    @Override
    public String getMXBeanType() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /** The life cycle. */
    private final ContextLifeCycle lifeCycle;

    /**
     * Instantiates a new server admin impl.
     */
    ServerAdminImpl() {
        this.lifeCycle = ContextLifeCycle.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#isAlive()
     *
     */
    @Override
    public boolean isAlive() {
        return lifeCycle.isAlive();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#serverStatus()
     *
     */
    @Override
    public RunStatus serverStatus() {
        return ContextLifeCycle.getInstance().getStatus();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#start()
     *
     */
    @Override
    public void start() {
        AuditManager.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#stop()
     *
     */
    @Override
    public void stop() {
        AuditManager.getInstance().shutdown();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#restart()
     *
     */
    @Override
    public void restart() {
        AuditManager.getInstance().shutdown();
        AuditManager.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdmin#startupTime()
     *
     */
    @Override
    public Long startupTime() {
        return lifeCycle.getStartUpTime();
    }

}
