package org.audit4j.core.schedule;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

import org.audit4j.core.schedule.util.ErrorHandler;

/**
 * Adapter that takes a {@code java.util.concurrent.ScheduledExecutorService}
 * and exposes a Spring {@link org.springframework.scheduling.TaskScheduler} for
 * it. Extends {@link ConcurrentTaskExecutor} in order to implement the
 * {@link org.springframework.scheduling.SchedulingTaskExecutor} interface as
 * well.
 * 
 * <p>
 * Autodetects a JSR-236
 * {@link javax.enterprise.concurrent.ManagedScheduledExecutorService} in order
 * to use it for trigger-based scheduling if possible, instead of Spring's local
 * trigger management which ends up delegating to regular delay-based scheduling
 * against the {@code java.util.concurrent.ScheduledExecutorService} API. For
 * JSR-236 style lookup in a Java EE 7 environment, consider using
 * {@link DefaultManagedTaskScheduler}.
 * 
 * <p>
 * Note that there is a pre-built {@link ThreadPoolTaskScheduler} that allows
 * for defining a {@link java.util.concurrent.ScheduledThreadPoolExecutor} in
 * bean style directly. This is a
 * convenient alternative to a raw ScheduledThreadPoolExecutor definition with a
 * separate definition of the present adapter class.
 * 
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 3.0
 * @see java.util.concurrent.ScheduledExecutorService
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.util.concurrent.Executors
 * @see ThreadPoolTaskScheduler
 */
public class ConcurrentTaskScheduler extends ConcurrentTaskExecutor implements TaskScheduler {

    /** The managed scheduled executor service class. */
    private static Class<?> managedScheduledExecutorServiceClass;
    static {
        try {
            managedScheduledExecutorServiceClass = Class
                    .forName("javax.enterprise.concurrent.ManagedScheduledExecutorService");
        } catch (ClassNotFoundException ex) {
            // JSR-236 API not available...
            managedScheduledExecutorServiceClass = null;
        }
    }

    /** The scheduled executor. */
    private ScheduledExecutorService scheduledExecutor;

    /** The enterprise concurrent scheduler. */
    private boolean enterpriseConcurrentScheduler = false;

    /** The error handler. */
    private ErrorHandler errorHandler;

    /**
     * Create a new ConcurrentTaskScheduler, using a single thread executor as
     * default.
     * 
     * @see java.util.concurrent.Executors#newSingleThreadScheduledExecutor()
     */
    public ConcurrentTaskScheduler() {
        super();
        setScheduledExecutor(null);
    }

    /**
     * Create a new ConcurrentTaskScheduler, using the given.
     * 
     * @param scheduledExecutor
     *            the {@link java.util.concurrent.ScheduledExecutorService} to
     *            delegate to for
     *            {@link java.util.concurrent.ScheduledExecutorService} as
     *            shared delegate.
     *            <p>
     *            Autodetects a JSR-236
     *            {@link javax.enterprise.concurrent.ManagedScheduledExecutorService}
     *            in order to use it for trigger-based scheduling if possible,
     *            instead of Spring's local trigger management.
     *            as well as {@link TaskScheduler} invocations
     */
    public ConcurrentTaskScheduler(ScheduledExecutorService scheduledExecutor) {
        super(scheduledExecutor);
        setScheduledExecutor(scheduledExecutor);
    }

    /**
     * Create a new ConcurrentTaskScheduler, using the given.
     * 
     * @param concurrentExecutor
     *            the {@link java.util.concurrent.Executor} to delegate to for
     * @param scheduledExecutor
     *            the {@link java.util.concurrent.ScheduledExecutorService} to
     *            delegate to for {@link TaskScheduler} invocations
     *            {@link java.util.concurrent.Executor} and
     *            {@link java.util.concurrent.ScheduledExecutorService} as
     *            delegates.
     *            <p>
     *            Autodetects a JSR-236
     *            {@link javax.enterprise.concurrent.ManagedScheduledExecutorService}
     *            in order to use it for trigger-based scheduling if possible,
     */
    public ConcurrentTaskScheduler(Executor concurrentExecutor, ScheduledExecutorService scheduledExecutor) {
        super(concurrentExecutor);
        setScheduledExecutor(scheduledExecutor);
    }

    /**
     * Specify the {@link java.util.concurrent.ScheduledExecutorService} to
     * delegate to.
     * <p>
     * Autodetects a JSR-236
     * 
     * @param scheduledExecutor
     *            the new scheduled executor
     *            {@link javax.enterprise.concurrent.ManagedScheduledExecutorService}
     *            in order to use it for trigger-based scheduling if possible,
     *            instead of Spring's local trigger management.
     *            <p>
     *            Note: This will only apply to {@link TaskScheduler}
     *            invocations. If you want the given executor to apply to
     *            {@link org.springframework.scheduling.SchedulingTaskExecutor}
     *            invocations as well, pass the same executor reference to
     *            {@link #setConcurrentExecutor}.
     * @see #setConcurrentExecutor
     */
    public final void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        if (scheduledExecutor != null) {
            this.scheduledExecutor = scheduledExecutor;
            this.enterpriseConcurrentScheduler = managedScheduledExecutorServiceClass != null && managedScheduledExecutorServiceClass
                    .isInstance(scheduledExecutor);
        } else {
            this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            this.enterpriseConcurrentScheduler = false;
        }
    }

    /**
     * Provide an {@link ErrorHandler} strategy.
     * 
     * @param errorHandler
     *            the new error handler
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        // Assert.notNull(errorHandler, "'errorHandler' must not be null");
        this.errorHandler = errorHandler;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#schedule(java.lang.Runnable,
     *      org.audit4j.core.schedule.Trigger)
     * 
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        try {
            if (this.enterpriseConcurrentScheduler) {
                return new EnterpriseConcurrentTriggerScheduler().schedule(decorateTask(task, true), trigger);
            } else {
                ErrorHandler errorHandlerLocal = this.errorHandler != null ? this.errorHandler : TaskUtils
                        .getDefaultErrorHandler(true);
                return new ReschedulingRunnable(task, trigger, this.scheduledExecutor, errorHandlerLocal).schedule();
            }
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#schedule(java.lang.Runnable,
     *      java.util.Date)
     * 
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return this.scheduledExecutor.schedule(decorateTask(task, false), initialDelay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleAtFixedRate(java.lang.Runnable,
     *      java.util.Date, long)
     * 
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return this.scheduledExecutor.scheduleAtFixedRate(decorateTask(task, true), initialDelay, period,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleAtFixedRate(java.lang.Runnable,
     *      long)
     * 
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        try {
            return this.scheduledExecutor.scheduleAtFixedRate(decorateTask(task, true), 0, period,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleWithFixedDelay(java.lang.Runnable,
     *      java.util.Date, long)
     * 
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return this.scheduledExecutor.scheduleWithFixedDelay(decorateTask(task, true), initialDelay, delay,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleWithFixedDelay(java.lang.Runnable,
     *      long)
     * 
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        try {
            return this.scheduledExecutor.scheduleWithFixedDelay(decorateTask(task, true), 0, delay,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task,
                    ex);
        }
    }

    /**
     * Decorate task.
     * 
     * @param task
     *            the task
     * @param isRepeatingTask
     *            the is repeating task
     * @return the runnable
     */
    private Runnable decorateTask(Runnable task, boolean isRepeatingTask) {
        Runnable result = TaskUtils.decorateTaskWithErrorHandler(task, this.errorHandler, isRepeatingTask);
        if (this.enterpriseConcurrentScheduler) {
            result = ManagedTaskBuilder.buildManagedTask(result, task.toString());
        }
        return result;
    }

    /**
     * Delegate that adapts a Spring Trigger to a JSR-236 Trigger. Separated
     * into an inner class in order to avoid a hard dependency on the JSR-236
     * API.
     * 
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    private class EnterpriseConcurrentTriggerScheduler {

        /**
         * Schedule.
         * 
         * @param task
         *            the task
         * @param trigger
         *            the trigger
         * @return the scheduled future
         */
        public ScheduledFuture<?> schedule(Runnable task, final Trigger trigger) {
            ManagedScheduledExecutorService executor = (ManagedScheduledExecutorService) scheduledExecutor;
            return executor.schedule(task, new javax.enterprise.concurrent.Trigger() {
                @Override
                public Date getNextRunTime(LastExecution le, Date taskScheduledTime) {
                    return trigger.nextExecutionTime(le != null ? new SimpleTriggerContext(le.getScheduledStart(), le
                            .getRunStart(), le.getRunEnd()) : new SimpleTriggerContext());
                }

                @Override
                public boolean skipRun(LastExecution lastExecution, Date scheduledRunTime) {
                    return false;
                }
            });
        }
    }
}