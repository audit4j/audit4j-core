package org.audit4j.core.schedule;

import java.util.Date;
import java.util.TimeZone;

/**
 * The Class CronTrigger.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class CronTrigger implements Trigger {

    /** The sequence generator. */
    private final CronSequenceGenerator sequenceGenerator;

    /**
     * Build a {@link CronTrigger} from the pattern provided in the default time
     * zone.
     * 
     * @param cronExpression
     *            a space-separated list of time fields, following cron
     *            expression conventions
     */
    public CronTrigger(String cronExpression) {
        this.sequenceGenerator = new CronSequenceGenerator(cronExpression);
    }

    /**
     * Build a {@link CronTrigger} from the pattern provided.
     * 
     * @param cronExpression
     *            a space-separated list of time fields, following cron
     *            expression conventions
     * @param timeZone
     *            a time zone in which the trigger times will be generated
     */
    public CronTrigger(String cronExpression, TimeZone timeZone) {
        this.sequenceGenerator = new CronSequenceGenerator(cronExpression, timeZone);
    }

    /**
     * Determine the next execution time according to the given trigger context.
     * <p>
     * Next execution times are calculated based on the
     * 
     * @param triggerContext
     *            the trigger context
     * @return the date {@linkplain TriggerContext#lastCompletionTime completion
     *         time} of the previous execution; therefore, overlapping
     *         executions won't occur.
     */
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date date = triggerContext.lastCompletionTime();
        if (date != null) {
            Date scheduled = triggerContext.lastScheduledExecutionTime();
            if (scheduled != null && date.before(scheduled)) {
                // Previous task apparently executed too early...
                // Let's simply use the last calculated execution time then,
                // in order to prevent accidental re-fires in the same second.
                date = scheduled;
            }
        } else {
            date = new Date();
        }
        return this.sequenceGenerator.next(date);
    }

    /**
     * Gets the expression.
     * 
     * @return the expression
     */
    public String getExpression() {
        return this.sequenceGenerator.getExpression();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof CronTrigger && this.sequenceGenerator
                .equals(((CronTrigger) obj).sequenceGenerator));
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     * 
     */
    @Override
    public int hashCode() {
        return this.sequenceGenerator.hashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     * 
     */
    @Override
    public String toString() {
        return this.sequenceGenerator.toString();
    }
}
