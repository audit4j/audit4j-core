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
    private static Class<?> managedExecutorServiceClass;
    static {
        try {
            managedExecutorServiceClass = Class.forName("javax.enterprise.concurrent.ManagedExecutorService");
        } catch (ClassNotFoundException ex) {
            // JSR-236 API not available...
            managedExecutorServiceClass = null;
        }
    }
    private Executor concurrentExecutor;
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
     * Create a new ConcurrentTaskExecutor, using the given
     * {@link java.util.concurrent.Executor}.
     * <p>
     * Autodetects a JSR-236
     * {@link javax.enterprise.concurrent.ManagedExecutorService} in order to
     * expose {@link javax.enterprise.concurrent.ManagedTask} adapters for it.
     * 
     * @param concurrentExecutor
     *            the {@link java.util.concurrent.Executor} to delegate to
     */
    public ConcurrentTaskExecutor(Executor concurrentExecutor) {
        setConcurrentExecutor(concurrentExecutor);
    }

    /**
     * Specify the {@link java.util.concurrent.Executor} to delegate to.
     * <p>
     * Autodetects a JSR-236
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
     */
    public final Executor getConcurrentExecutor() {
        return this.concurrentExecutor;
    }

    @Override
    public void execute(Runnable task) {
        this.adaptedExecutor.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        this.adaptedExecutor.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.adaptedExecutor.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.adaptedExecutor.submit(task);
    }

    /**
     * This task executor prefers short-lived work units.
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
     */
    private static class ManagedTaskExecutorAdapter extends TaskExecutorAdapter {
        public ManagedTaskExecutorAdapter(Executor concurrentExecutor) {
            super(concurrentExecutor);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ManagedTaskBuilder.buildManagedTask(task, task.toString()));
        }
    }

    /**
     * Delegate that wraps a given Runnable/Callable with a JSR-236 ManagedTask,
     * exposing a long-running hint based on {@link SchedulingAwareRunnable} and
     * a given identity name.
     */
    protected static class ManagedTaskBuilder {
        public static Runnable buildManagedTask(Runnable task, String identityName) {
            Map<String, String> properties = new HashMap<String, String>(2);
            if (task instanceof SchedulingAwareRunnable) {
                properties.put(ManagedTask.LONGRUNNING_HINT,
                        Boolean.toString(((SchedulingAwareRunnable) task).isLongLived()));
            }
            properties.put(ManagedTask.IDENTITY_NAME, identityName);
            return ManagedExecutors.managedTask(task, properties, null);
        }

        public static <T> Callable<T> buildManagedTask(Callable<T> task, String identityName) {
            Map<String, String> properties = new HashMap<String, String>(1);
            properties.put(ManagedTask.IDENTITY_NAME, identityName);
            return ManagedExecutors.managedTask(task, properties, null);
        }
    }
}
