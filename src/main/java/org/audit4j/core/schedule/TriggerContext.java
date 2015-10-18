package org.audit4j.core.schedule;

import java.util.Date;

/**
 * Context object encapsulating last execution times and last completion time of
 * a given task.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public interface TriggerContext {

    /**
     * Return the last <i>scheduled</i> execution time of the task, or.
     *
     * @return the date
     * {@code null} if not scheduled before.
     */
    Date lastScheduledExecutionTime();

    /**
     * Return the last <i>actual</i> execution time of the task, or {@code null}
     * if not scheduled before.
     *
     * @return the date
     */
    Date lastActualExecutionTime();

    /**
     * Return the last completion time of the task, or {@code null} if not
     * scheduled before.
     *
     * @return the date
     */
    Date lastCompletionTime();
}
