package org.audit4j.core.schedule;

/**
 * The Class IntervalTask.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class IntervalTask extends Task {

    /** The interval. */
    private final long interval;
    
    /** The initial delay. */
    private final long initialDelay;

    /**
     * Create a new {@code IntervalTask}.
     * 
     * @param runnable
     *            the underlying task to execute
     * @param interval
     *            how often in milliseconds the task should be executed
     * @param initialDelay
     *            initial delay before first execution of the task
     */
    public IntervalTask(Runnable runnable, long interval, long initialDelay) {
        super(runnable);
        this.interval = interval;
        this.initialDelay = initialDelay;
    }

    /**
     * Create a new {@code IntervalTask} with no initial delay.
     * 
     * @param runnable
     *            the underlying task to execute
     * @param interval
     *            how often in milliseconds the task should be executed
     */
    public IntervalTask(Runnable runnable, long interval) {
        this(runnable, interval, 0);
    }

    /**
     * Gets the interval.
     *
     * @return the interval
     */
    public long getInterval() {
        return this.interval;
    }

    /**
     * Gets the initial delay.
     *
     * @return the initial delay
     */
    public long getInitialDelay() {
        return this.initialDelay;
    }

}
