package org.audit4j.core.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.enterprise.concurrent.ManagedExecutors;
import javax.enterprise.concurrent.ManagedTask;


/**
 * Adapter that takes a {@code java.util.concurrent.Executor} and exposes a
 * Spring {@link org.audit4j.schedule.springframework.core.task.TaskExecutor} for it. Also
 * detects an extended {@code java.util.concurrent.ExecutorService}, adapting
 * the {@link org.audit4j.schedule.springframework.core.task.AsyncTaskExecutor} interface
 * accordingly.
 * 
 * <p>
 * Autodetects a JSR-236
 * {@link javax.enterprise.concurrent.ManagedExecutorService} in order to expose
 * {@link javax.enterprise.concurrent.ManagedTask} adapters for it, exposing a
 * long-running hint based on {@link SchedulingAwareRunnable} and an identity
 * name based on the given Runnable/Callable's {@code toString()}. For JSR-236
 * style lookup in a Java EE 7 environment, consider using
 * {@link DefaultManagedTaskExecutor}.
 * 
 * <p>
 * Note that there is a pre-built {@link ThreadPoolTaskExecutor} that allows for
 * defining a {@link java.util.concurrent.ThreadPoolExecutor} in bean style,
 * exposing it as a Spring {@link org.audit4j.schedule.springframework.core.task.TaskExecutor}
 * directly. This is a convenient alternative to a raw ThreadPoolExecutor
 * definition with a separate definition of the present adapter class.
 * 
 * @author Juergen Hoeller
 * @since 2.0
 * @see java.util.concurrent.Executor
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see java.util.concurrent.Executors
 * @see DefaultManagedTaskExecutor
 * @see ThreadPoolTaskExecutor
 */
public class ConcurrentTaskExecutor implements AsyncTaskExecutor, SchedulingTaskExecutor {
    
    /** The managed executor service class. */
    private static Class<?> managedExecutorServiceClass;
    static {
        try {
            managedExecutorServiceClass = Class.forName("javax.enterprise.concurrent.ManagedExecutorService");
        } catch (ClassNotFoundException ex) {
            // JSR-236 API not available...
            managedExecutorServiceClass = null;
        }
    }
    
    /** The concurrent executor. */
    private Executor concurrentExecutor;
    
    /** The adapted executor. */
    private TaskExecutorAdapter adaptedExecutor;

    /**
     * Create a new ConcurrentTaskExecutor, using a single thread executor as
     * default.
     * 
     * @see java.util.concurrent.Executors#newSingleThreadExecutor()
     */
    public ConcurrentTaskExecutor() {
        setConcurrentExecutor(null);
    }

    /**
     * Create a new ConcurrentTaskExecutor, using the given.
     *
     * @param concurrentExecutor the {@link java.util.concurrent.Executor} to delegate to
     * {@link java.util.concurrent.Executor}.
     * <p>
     * Autodetects a JSR-236
     * {@link javax.enterprise.concurrent.ManagedExecutorService} in order to
     * expose {@link javax.enterprise.concurrent.ManagedTask} adapters for it.
     */
    public ConcurrentTaskExecutor(Executor concurrentExecutor) {
        setConcurrentExecutor(concurrentExecutor);
    }

    /**
     * Specify the {@link java.util.concurrent.Executor} to delegate to.
     * <p>
     * Autodetects a JSR-236
     *
     * @param concurrentExecutor the new concurrent executor
     * {@link javax.enterprise.concurrent.ManagedExecutorService} in order to
     * expose {@link javax.enterprise.concurrent.ManagedTask} adapters for it.
     */
    public final void setConcurrentExecutor(Executor concurrentExecutor) {
        if (concurrentExecutor != null) {
            this.concurrentExecutor = concurrentExecutor;
            if (managedExecutorServiceClass != null && managedExecutorServiceClass.isInstance(concurrentExecutor)) {
                this.adaptedExecutor = new ManagedTaskExecutorAdapter(concurrentExecutor);
            } else {
                this.adaptedExecutor = new TaskExecutorAdapter(concurrentExecutor);
            }
        } else {
            this.concurrentExecutor = Executors.newSingleThreadExecutor();
            this.adaptedExecutor = new TaskExecutorAdapter(this.concurrentExecutor);
        }
    }

    /**
     * Return the {@link java.util.concurrent.Executor} that this adapter
     * delegates to.
     *
     * @return the concurrent executor
     */
    public final Executor getConcurrentExecutor() {
        return this.concurrentExecutor;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.TaskExecutor#execute(java.lang.Runnable)
     *
     */
    @Override
    public void execute(Runnable task) {
        this.adaptedExecutor.execute(task);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#execute(java.lang.Runnable, long)
     *
     */
    @Override
    public void execute(Runnable task, long startTimeout) {
        this.adaptedExecutor.execute(task, startTimeout);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#submit(java.lang.Runnable)
     *
     */
    @Override
    public Future<?> submit(Runnable task) {
        return this.adaptedExecutor.submit(task);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.schedule.AsyncTaskExecutor#submit(java.util.concurrent.Callable)
     *
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.adaptedExecutor.submit(task);
    }

    /**
     * This task executor prefers short-lived work units.
     *
     * @return true, if successful
     */
    @Override
    public boolean prefersShortLivedTasks() {
        return true;
    }

    /**
     * TaskExecutorAdapter subclass that wraps all provided Runnables and
     * Callables with a JSR-236 ManagedTask, exposing a long-running hint based
     * on {@link SchedulingAwareRunnable} and an identity name based on the
     * task's {@code toString()} representation.
     *
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    private static class ManagedTaskExecutorAdapter extends TaskExecutorAdapter {
        
        /**
         * Instantiates a new managed task executor adapter.
         *
         * @param concurrentExecutor the concurrent executor
         */
        public ManagedTaskExecutorAdapter(Executor concurrentExecutor) {
            super(concurrentExecutor);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.audit4j.core.schedule.TaskExecutorAdapter#execute(java.lang.Runnable)
         *
         */
        @Override
        public void execute(Runnable task) {
            super.execute(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.audit4j.core.schedule.TaskExecutorAdapter#submit(java.lang.Runnable)
         *
         */
        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.audit4j.core.schedule.TaskExecutorAdapter#submit(java.util.concurrent.Callable)
         *
         */
        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }
    }

    /**
     * Delegate that wraps a given Runnable/Callable with a JSR-236 ManagedTask,
     * exposing a long-running hint based on {@link SchedulingAwareRunnable} and
     * a given identity name.
     *
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    protected static class ManagedTaskBuilder {
        
        /**
         * Builds the managed task.
         *
         * @param task the task
         * @param identityName the identity name
         * @return the runnable
         */
        public static Runnable buildManagedTask(Runnable task, String identityName) {
            Map<String, String> properties = new HashMap<String, String>(2);
            if (task instanceof SchedulingAwareRunnable) {
                properties.put(ManagedTask.LONGRUNNING_HINT,
                        Boolean.toString(((SchedulingAwareRunnable) task).isLongLived()));
            }
            properties.put(ManagedTask.IDENTITY_NAME, identityName);
            return ManagedExecutors.managedTask(task, properties, null);
        }

        /**
         * Builds the managed task.
         *
         * @param <T> the generic type
         * @param task the task
         * @param identityName the identity name
         * @return the callable
         */
        public static <T> Callable<T> buildManagedTask(Callable<T> task, String identityName) {
            Map<String, String> properties = new HashMap<String, String>(1);
            properties.put(ManagedTask.IDENTITY_NAME, identityName);
            return ManagedExecutors.managedTask(task, properties, null);
        }
    }
}
