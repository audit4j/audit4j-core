package org.audit4j.core.schedule;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.audit4j.core.schedule.util.ErrorHandler;
 
/**
 * Internal adapter that reschedules an underlying {@link Runnable} according to
 * the next execution time suggested by a given {@link Trigger}.
 * 
 * <p>
 * Necessary because a native {@link ScheduledExecutorService} supports
 * delay-driven execution only. The flexibility of the {@link Trigger} interface
 * will be translated onto a delay for the next execution time (repeatedly).
 * 
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 3.0
 */
class ReschedulingRunnable extends DelegatingErrorHandlingRunnable implements ScheduledFuture<Object> {
    
    /** The trigger. */
    private final Trigger trigger;
    
    /** The trigger context. */
    private final SimpleTriggerContext triggerContext = new SimpleTriggerContext();
    
    /** The executor. */
    private final ScheduledExecutorService executor;
    
    /** The current future. */
    private ScheduledFuture<?> currentFuture;
    
    /** The scheduled execution time. */
    private Date scheduledExecutionTime;
    
    /** The trigger context monitor. */
    private final Object triggerContextMonitor = new Object();

    /**
     * Instantiates a new rescheduling runnable.
     *
     * @param delegate the delegate
     * @param trigger the trigger
     * @param executor the executor
     * @param errorHandler the error handler
     */
    public ReschedulingRunnable(Runnable delegate, Trigger trigger, ScheduledExecutorService executor,
            ErrorHandler errorHandler) {
        super(delegate, errorHandler);
        this.trigger = trigger;
        this.executor = executor;
    }

    /**
     * Schedule.
     *
     * @return the scheduled future
     */
    public ScheduledFuture<?> schedule() {
        synchronized (this.triggerContextMonitor) {
            this.scheduledExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
            if (this.scheduledExecutionTime == null) {
                return null;
            }
            long initialDelay = this.scheduledExecutionTime.getTime() - System.currentTimeMillis();
            this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
            return this;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.DelegatingErrorHandlingRunnable#run()
     *
     */
    @Override
    public void run() {
        Date actualExecutionTime = new Date();
        super.run();
        Date completionTime = new Date();
        synchronized (this.triggerContextMonitor) {
            this.triggerContext.update(this.scheduledExecutionTime, actualExecutionTime, completionTime);
            if (!this.currentFuture.isCancelled()) {
                schedule();
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Future#cancel(boolean)
     *
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (this.triggerContextMonitor) {
            return this.currentFuture.cancel(mayInterruptIfRunning);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Future#isCancelled()
     *
     */
    @Override
    public boolean isCancelled() {
        synchronized (this.triggerContextMonitor) {
            return this.currentFuture.isCancelled();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Future#isDone()
     *
     */
    @Override
    public boolean isDone() {
        synchronized (this.triggerContextMonitor) {
            return this.currentFuture.isDone();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Future#get()
     *
     */
    @Override
    public Object get() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.get();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
     *
     */
    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.get(timeout, unit);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
     *
     */
    @Override
    public long getDelay(TimeUnit unit) {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.getDelay(unit);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     *
     */
    @Override
    public int compareTo(Delayed other) {
        if (this == other) {
            return 0;
        }
        long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
        return diff == 0 ? 0 : (diff < 0) ? -1 : 1;
    }
}