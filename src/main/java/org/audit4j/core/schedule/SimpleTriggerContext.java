package org.audit4j.core.schedule;

import java.util.Date;

/**
 * Simple data holder implementation of the {@link TriggerContext} interface.
 * 
 * @author Juergen Hoeller
 * @since 3.0
 */
public class SimpleTriggerContext implements TriggerContext {
    
    /** The last scheduled execution time. */
    private volatile Date lastScheduledExecutionTime;
    
    /** The last actual execution time. */
    private volatile Date lastActualExecutionTime;
    
    /** The last completion time. */
    private volatile Date lastCompletionTime;

    /**
     * Create a SimpleTriggerContext with all time values set to {@code null}.
     */
    public SimpleTriggerContext() {
    }

    /**
     * Create a SimpleTriggerContext with the given time values.
     * 
     * @param lastScheduledExecutionTime
     *            last <i>scheduled</i> execution time
     * @param lastActualExecutionTime
     *            last <i>actual</i> execution time
     * @param lastCompletionTime
     *            last completion time
     */
    public SimpleTriggerContext(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
    }

    /**
     * Update this holder's state with the latest time values.
     * 
     * @param lastScheduledExecutionTime
     *            last <i>scheduled</i> execution time
     * @param lastActualExecutionTime
     *            last <i>actual</i> execution time
     * @param lastCompletionTime
     *            last completion time
     */
    public void update(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TriggerContext#lastScheduledExecutionTime()
     *
     */
    @Override
    public Date lastScheduledExecutionTime() {
        return this.lastScheduledExecutionTime;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TriggerContext#lastActualExecutionTime()
     *
     */
    @Override
    public Date lastActualExecutionTime() {
        return this.lastActualExecutionTime;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TriggerContext#lastCompletionTime()
     *
     */
    @Override
    public Date lastCompletionTime() {
        return this.lastCompletionTime;
    }
}