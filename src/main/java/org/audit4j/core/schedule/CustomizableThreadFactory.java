package org.audit4j.core.schedule;

import java.util.concurrent.ThreadFactory;

import org.audit4j.core.schedule.util.CustomizableThreadCreator;

/**
 * Implementation of the {@link java.util.concurrent.ThreadFactory} interface,
 * allowing for customizing the created threads (name, priority, etc).
 * 
 * <p>
 * See the base class {@link org.springframework.util.CustomizableThreadCreator}
 * for details on the available configuration options.
 * 
 * @author Juergen Hoeller
 * @since 2.0.3
 * @see #setThreadNamePrefix
 * @see #setThreadPriority
 */
@SuppressWarnings("serial")
public class CustomizableThreadFactory extends CustomizableThreadCreator implements ThreadFactory {
    /**
     * Create a new CustomizableThreadFactory with default thread name prefix.
     */
    public CustomizableThreadFactory() {
        super();
    }

    /**
     * Create a new CustomizableThreadFactory with the given thread name prefix.
     * 
     * @param threadNamePrefix
     *            the prefix to use for the names of newly created threads
     */
    public CustomizableThreadFactory(String threadNamePrefix) {
        super(threadNamePrefix);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return createThread(runnable);
    }
}