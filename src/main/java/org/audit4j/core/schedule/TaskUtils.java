package org.audit4j.core.schedule;

import org.audit4j.core.schedule.util.ErrorHandler;


/**
 * Utility methods for decorating tasks with error handling.
 * 
 * <p>
 * <b>NOTE:</b> This class is intended for internal use by Spring's scheduler
 * implementations. It is only public so that it may be accessed from impl
 * classes within other packages. It is <i>not</i> intended for general use.
 * 
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0
 */
public abstract class TaskUtils {
	
    /**
     * An ErrorHandler strategy that will log the Exception but perform no
     * further handling. This will suppress the error so that subsequent
     * executions of the task will not be prevented.
     */
    public static final ErrorHandler LOG_AND_SUPPRESS_ERROR_HANDLER = new LoggingErrorHandler();
    /**
     * An ErrorHandler strategy that will log at error level and then re-throw
     * the Exception. Note: this will typically prevent subsequent execution of
     * a scheduled task.
     */
    public static final ErrorHandler LOG_AND_PROPAGATE_ERROR_HANDLER = new PropagatingErrorHandler();

    /**
     * private constructor to avoid instantiation of this class
     */
    private TaskUtils(){
    	
    }

    /**
     * Decorate the task for error handling. If the provided
     *
     * @param task the task
     * @param errorHandler the error handler
     * @param isRepeatingTask the is repeating task
     * @return the delegating error handling runnable
     * {@link ErrorHandler} is not {@code null}, it will be used. Otherwise,
     * repeating tasks will have errors suppressed by default whereas one-shot
     * tasks will have errors propagated by default since those errors may be
     * expected through the returned {@link Future}. In both cases, the errors
     * will be logged.
     */
    public static DelegatingErrorHandlingRunnable decorateTaskWithErrorHandler(Runnable task,
            ErrorHandler errorHandler, boolean isRepeatingTask) {
        if (task instanceof DelegatingErrorHandlingRunnable) {
            return (DelegatingErrorHandlingRunnable) task;
        }
        ErrorHandler eh = errorHandler != null ? errorHandler : getDefaultErrorHandler(isRepeatingTask);
        return new DelegatingErrorHandlingRunnable(task, eh);
    }

    /**
     * Return the default {@link ErrorHandler} implementation based on the
     * boolean value indicating whether the task will be repeating or not. For
     * repeating tasks it will suppress errors, but for one-time tasks it will
     * propagate. In both cases, the error will be logged.
     *
     * @param isRepeatingTask the is repeating task
     * @return the default error handler
     */
    public static ErrorHandler getDefaultErrorHandler(boolean isRepeatingTask) {
        return isRepeatingTask ? LOG_AND_SUPPRESS_ERROR_HANDLER : LOG_AND_PROPAGATE_ERROR_HANDLER;
    }

    /**
     * An {@link ErrorHandler} implementation that logs the Throwable at error
     * level. It does not perform any additional error handling. This can be
     * useful when suppression of errors is the intended behavior.
     *
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    private static class LoggingErrorHandler implements ErrorHandler {
        // private final Log logger =
        // LogFactory.getLog(LoggingErrorHandler.class);
        //
        /**
         * {@inheritDoc}
         * 
         * @see org.audit4j.core.schedule.util.ErrorHandler#handleError(java.lang.Throwable)
         *
         */
        @Override
        public void handleError(Throwable t) {
            // if (logger.isErrorEnabled()) {
            // logger.error("Unexpected error occurred in scheduled task.", t);
            // }
        }
    }

    /**
     * An {@link ErrorHandler} implementation that logs the Throwable at error
     * level and then propagates it.
     *
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     * @since
     */
    private static class PropagatingErrorHandler extends LoggingErrorHandler {
        
        /**
         * {@inheritDoc}
         * 
         * @see org.audit4j.core.schedule.TaskUtils.LoggingErrorHandler#handleError(java.lang.Throwable)
         *
         */
        @Override
        public void handleError(Throwable t) {
            super.handleError(t);
            // ReflectionUtils.rethrowRuntimeException(t);
        }
    }
}