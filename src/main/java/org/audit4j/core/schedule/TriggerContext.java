package org.audit4j.core.schedule;

import java.util.Date;

/**
 * Context object encapsulating last execution times and last completion time of
 * a given task.
 */
public interface TriggerContext {

    /**
     * Return the last <i>scheduled</i> execution time of the task, or
     * {@code null} if not scheduled before.
     */
    Date lastScheduledExecutionTime();

    /**
     * Return the last <i>actual</i> execution time of the task, or {@code null}
     * if not scheduled before.
     */
    Date lastActualExecutionTime();

    /**
     * Return the last completion time of the task, or {@code null} if not
     * scheduled before.
     */
    Date lastCompletionTime();
}
