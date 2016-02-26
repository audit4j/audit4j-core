package org.audit4j.core;

/**
 * The Class ContextLifeCycle.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public final class LifeCycleContext {

    /** The status. */
    private RunStatus status = RunStatus.READY;
    
    /** The start up time. */
    private Long startUpTime;

    /** The instance. */
    private static LifeCycleContext instance;

    /**
     * Instantiates a new context life cycle.
     */
    private LifeCycleContext() {
    }
    
    /**
     * Gets the status.
     * 
     * @return the status
     */
    public RunStatus getStatus() {
        return status;
    }

    /**
     * Checks if is alive.
     *
     * @return true, if is alive
     */
    public boolean isAlive() {
        if (LifeCycleContext.getInstance().getStatus() == RunStatus.RUNNING) {
            return true;
        }
        return false;
    }

    /**
     * Gets the start up time.
     *
     * @return the start up time
     */
    public Long getStartUpTime() {
        return startUpTime;
    }

    /**
     * Sets the start up time.
     *
     * @param startUpTime the new start up time
     */
    public void setStartUpTime(Long startUpTime) {
        this.startUpTime = startUpTime;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            the new status
     */
    void setStatus(RunStatus status) {
        this.status = status;
    }

    /**
     * Gets the single instance of ContextLifeCycle.
     *
     * @return single instance of ContextLifeCycle
     */
    public static LifeCycleContext getInstance() {
        synchronized (LifeCycleContext.class) {
            if (instance == null) {
                instance = new LifeCycleContext();
            }
        }
        return instance;
    }
}
