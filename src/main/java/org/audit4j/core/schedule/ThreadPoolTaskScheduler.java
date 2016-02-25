package org.audit4j.core.schedule;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.audit4j.core.schedule.util.ClassUtils;
import org.audit4j.core.schedule.util.ErrorHandler;

/**
 * Implementation of Spring's {@link TaskScheduler} interface, wrapping a native.
 *
 * {@link java.util.concurrent.ScheduledThreadPoolExecutor}.
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 3.0
 * @see #setPoolSize
 * @see #setRemoveOnCancelPolicy
 * @see #setThreadFactory
 * @see #setErrorHandler
 */
@SuppressWarnings("serial")
public class ThreadPoolTaskScheduler implements AsyncTaskExecutor, SchedulingTaskExecutor, TaskScheduler {
    // ScheduledThreadPoolExecutor.setRemoveOnCancelPolicy(boolean) only
    // available on JDK 7+
    /** The Constant setRemoveOnCancelPolicyAvailable. */
    private static final boolean setRemoveOnCancelPolicyAvailable = ClassUtils.hasMethod(
            ScheduledThreadPoolExecutor.class, "setRemoveOnCancelPolicy", boolean.class);
    
    /** The pool size. */
    private volatile int poolSize = 1;
    
    /** The remove on cancel policy. */
    private volatile boolean removeOnCancelPolicy = false;
    
    /** The scheduled executor. */
    private volatile ScheduledExecutorService scheduledExecutor;
    
    /** The error handler. */
    private volatile ErrorHandler errorHandler;

    /**
     * Set the ScheduledExecutorService's pool size. Default is 1.
     * <p>
     * <b>This setting can be modified at runtime, for example through JMX.</b>
     *
     * @param poolSize the new pool size
     */
    public void setPoolSize(int poolSize) {
        // Assert.isTrue(poolSize > 0, "'poolSize' must be 1 or higher");
        this.poolSize = poolSize;
        if (this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
            ((ScheduledThreadPoolExecutor) this.scheduledExecutor).setCorePoolSize(poolSize);
        }
    }

    /**
     * Set the remove-on-cancel mode on {@link ScheduledThreadPoolExecutor} (JDK
     * 7+).
     * <p>
     * Default is {@code false}. If set to {@code true}, the target executor
     * will be switched into remove-on-cancel mode (if possible, with a soft
     * fallback otherwise).
     * <p>
     * <b>This setting can be modified at runtime, for example through JMX.</b>
     *
     * @param removeOnCancelPolicy the new removes the on cancel policy
     */
    // @UsesJava7
    public void setRemoveOnCancelPolicy(boolean removeOnCancelPolicy) {
        this.removeOnCancelPolicy = removeOnCancelPolicy;
        if (setRemoveOnCancelPolicyAvailable && this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
            ((ScheduledThreadPoolExecutor) this.scheduledExecutor).setRemoveOnCancelPolicy(removeOnCancelPolicy);
        } else if (removeOnCancelPolicy && this.scheduledExecutor != null) {
            // logger.info("Could not apply remove-on-cancel policy - not a Java 7+ ScheduledThreadPoolExecutor");
        }
    }

    /**
     * Set a custom {@link ErrorHandler} strategy.
     *
     * @param errorHandler the new error handler
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    // @UsesJava7
    // @Override
    /**
     * Initialize executor.
     *
     * @param threadFactory the thread factory
     * @param rejectedExecutionHandler the rejected execution handler
     * @return the executor service
     */
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory,
            RejectedExecutionHandler rejectedExecutionHandler) {
        this.scheduledExecutor = createExecutor(this.poolSize, threadFactory, rejectedExecutionHandler);
        if (this.removeOnCancelPolicy) {
            if (setRemoveOnCancelPolicyAvailable && this.scheduledExecutor instanceof ScheduledThreadPoolExecutor) {
                ((ScheduledThreadPoolExecutor) this.scheduledExecutor).setRemoveOnCancelPolicy(true);
            } else {
                // logger.info("Could not apply remove-on-cancel policy - not a Java 7+ ScheduledThreadPoolExecutor");
            }
        }
        return this.scheduledExecutor;
    }

    /**
     * Create a new {@link ScheduledExecutorService} instance.
     * <p>
     * The default implementation creates a {@link ScheduledThreadPoolExecutor}.
     * Can be overridden in subclasses to provide custom
     *
     * @param poolSize the specified pool size
     * @param threadFactory the ThreadFactory to use
     * @param rejectedExecutionHandler the RejectedExecutionHandler to use
     * @return a new ScheduledExecutorService instance
     * {@link ScheduledExecutorService} instances.
     * @see #afterPropertiesSet()
     * @see java.util.concurrent.ScheduledThreadPoolExecutor
     */
    protected ScheduledExecutorService createExecutor(int poolSize, ThreadFactory threadFactory,
            RejectedExecutionHandler rejectedExecutionHandler) {
        return new ScheduledThreadPoolExecutor(poolSize, threadFactory, rejectedExecutionHandler);
    }

    /**
     * Return the underlying ScheduledExecutorService for native access.
     * 
     * @return the underlying ScheduledExecutorService (never {@code null})
     * @throws IllegalStateException
     *             if the ThreadPoolTaskScheduler hasn't been initialized yet
     */
    public ScheduledExecutorService getScheduledExecutor() throws IllegalStateException {
        // Assert.state(this.scheduledExecutor != null,
        // "ThreadPoolTaskScheduler not initialized");
        return this.scheduledExecutor;
    }

    /**
     * Return the underlying ScheduledThreadPoolExecutor, if available.
     * 
     * @return the underlying ScheduledExecutorService (never {@code null})
     * @throws IllegalStateException
     *             if the ThreadPoolTaskScheduler hasn't been initialized yet or
     *             if the underlying ScheduledExecutorService isn't a
     *             ScheduledThreadPoolExecutor
     * @see #getScheduledExecutor()
     */
    public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() throws IllegalStateException {
        // Assert.state(this.scheduledExecutor instanceof
        // ScheduledThreadPoolExecutor,
        // "No ScheduledThreadPoolExecutor available");
        return (ScheduledThreadPoolExecutor) this.scheduledExecutor;
    }

    /**
     * Return the current pool size.
     * <p>
     * Requires an underlying {@link ScheduledThreadPoolExecutor}.
     *
     * @return the pool size
     * @see #getScheduledThreadPoolExecutor()
     * @see java.util.concurrent.ScheduledThreadPoolExecutor#getPoolSize()
     */
    public int getPoolSize() {
        if (this.scheduledExecutor == null) {
            // Not initialized yet: assume initial pool size.
            return this.poolSize;
        }
        return getScheduledThreadPoolExecutor().getPoolSize();
    }

    /**
     * Return the current setting for the remove-on-cancel mode.
     * <p>
     * Requires an underlying {@link ScheduledThreadPoolExecutor}.
     *
     * @return true, if is removes the on cancel policy
     */
    // @UsesJava7
    public boolean isRemoveOnCancelPolicy() {
        if (!setRemoveOnCancelPolicyAvailable) {
            return false;
        }
        if (this.scheduledExecutor == null) {
            // Not initialized yet: return our setting for the time being.
            return this.removeOnCancelPolicy;
        }
        return getScheduledThreadPoolExecutor().getRemoveOnCancelPolicy();
    }

    /**
     * Return the number of currently active threads.
     * <p>
     * Requires an underlying {@link ScheduledThreadPoolExecutor}.
     *
     * @return the active count
     * @see #getScheduledThreadPoolExecutor()
     * @see java.util.concurrent.ScheduledThreadPoolExecutor#getActiveCount()
     */
    public int getActiveCount() {
        if (this.scheduledExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return getScheduledThreadPoolExecutor().getActiveCount();
    }

    // SchedulingTaskExecutor implementation
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskExecutor#execute(java.lang.Runnable)
     *
     */
    @Override
    public void execute(Runnable task) {
        Executor executor = getScheduledExecutor();
        try {
            executor.execute(errorHandlingTask(task, false));
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#execute(java.lang.Runnable, long)
     *
     */
    @Override
    public void execute(Runnable task, long startTimeout) {
        execute(task);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#submit(java.lang.Runnable)
     *
     */
    @Override
    public Future<?> submit(Runnable task) {
        ExecutorService executor = getScheduledExecutor();
        try {
            return executor.submit(errorHandlingTask(task, false));
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#submit(java.util.concurrent.Callable)
     *
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        ExecutorService executor = getScheduledExecutor();
        try {
            Callable<T> taskToUse = task;
            if (this.errorHandler != null) {
                taskToUse = new DelegatingErrorHandlingCallable<T>(task, this.errorHandler);
            }
            return executor.submit(taskToUse);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.SchedulingTaskExecutor#prefersShortLivedTasks()
     *
     */
    @Override
    public boolean prefersShortLivedTasks() {
        return true;
    }

    // TaskScheduler implementation
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#schedule(java.lang.Runnable, org.audit4j.core.schedule.Trigger)
     *
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            ErrorHandler errorHandlerLocal = this.errorHandler != null ? this.errorHandler : TaskUtils
                    .getDefaultErrorHandler(true);
            return new ReschedulingRunnable(task, trigger, executor, errorHandlerLocal).schedule();
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#schedule(java.lang.Runnable, java.util.Date)
     *
     */
    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        ScheduledExecutorService executor = getScheduledExecutor();
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.schedule(errorHandlingTask(task, false), initialDelay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleAtFixedRate(java.lang.Runnable, java.util.Date, long)
     *
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        ScheduledExecutorService executor = getScheduledExecutor();
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.scheduleAtFixedRate(errorHandlingTask(task, true), initialDelay, period,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleAtFixedRate(java.lang.Runnable, long)
     *
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            return executor.scheduleAtFixedRate(errorHandlingTask(task, true), 0, period, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleWithFixedDelay(java.lang.Runnable, java.util.Date, long)
     *
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor();
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.scheduleWithFixedDelay(errorHandlingTask(task, true), initialDelay, delay,
                    TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskScheduler#scheduleWithFixedDelay(java.lang.Runnable, long)
     *
     */
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            return executor.scheduleWithFixedDelay(errorHandlingTask(task, true), 0, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * Error handling task.
     *
     * @param task the task
     * @param isRepeatingTask the is repeating task
     * @return the runnable
     */
    private Runnable errorHandlingTask(Runnable task, boolean isRepeatingTask) {
        return TaskUtils.decorateTaskWithErrorHandler(task, this.errorHandler, isRepeatingTask);
    }

    /**
     * The Class DelegatingErrorHandlingCallable.
     *
     * @param <V> the value type
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    private static class DelegatingErrorHandlingCallable<V> implements Callable<V> {
        
        /** The delegate. */
        private final Callable<V> delegate;
        
        /** The error handler. */
        private final ErrorHandler errorHandler;

        /**
         * Instantiates a new delegating error handling callable.
         *
         * @param delegate the delegate
         * @param errorHandler the error handler
         */
        public DelegatingErrorHandlingCallable(Callable<V> delegate, ErrorHandler errorHandler) {
            this.delegate = delegate;
            this.errorHandler = errorHandler;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.concurrent.Callable#call()
         *
         */
        @Override
        public V call() throws Exception {
            try {
                return this.delegate.call();
            } catch (Throwable t) {
                this.errorHandler.handleError(t);
                return null;
            }
        }
    }
}