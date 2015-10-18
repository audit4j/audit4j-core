package org.audit4j.core.schedule;

/**
 * Holder class defining a {@code Runnable} to be executed as a task, typically at a
 * scheduled time or interval. See subclass hierarchy for various scheduling approaches.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Task {

    /** The runnable. */
    private final Runnable runnable;

    /**
     * Create a new {@code Task}.
     * 
     * @param runnable
     *            the underlying task to execute.
     */
    public Task(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Gets the runnable.
     *
     * @return the runnable
     */
    public Runnable getRunnable() {
        return runnable;
    }
}
