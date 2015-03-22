package org.audit4j.core.jmx;


/**
 * The Interface ServerAdminMBean.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public interface ServerAdminMBean {

    /**
     * Gets the startup time.
     *
     * @return the startup time
     */
    Long getStartupTime();

    /**
     * Checks if is alive.
     *
     * @return true, if is alive
     */
    boolean isAlive();

    /**
     * Gets the server status.
     *
     * @return the server status
     */
    String getServerStatus();

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();

    /**
     * Restart.
     */
    void restart();

    /**
     * Disable.
     */
    void disable();

    /**
     * Enable.
     */
    void enable();
}
