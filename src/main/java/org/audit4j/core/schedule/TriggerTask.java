package org.audit4j.core.schedule;


/**
 * The Class TriggerTask.
 *
 * {@link Task} implementation defining a {@code Runnable} to be executed according to a
 * given {@link Trigger}.
 */
public class TriggerTask extends Task {

    /** The trigger. */
    private final Trigger trigger;

    /**
     * Create a new {@link TriggerTask}.
     * 
     * @param runnable
     *            the underlying task to execute
     * @param trigger
     *            specifies when the task should be executed
     */
    public TriggerTask(Runnable runnable, Trigger trigger) {
        super(runnable);
        this.trigger = trigger;
    }

    /**
     * Gets the trigger.
     *
     * @return the trigger
     */
    public Trigger getTrigger() {
        return trigger;
    }
}
