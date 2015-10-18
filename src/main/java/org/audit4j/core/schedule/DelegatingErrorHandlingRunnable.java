package org.audit4j.core.schedule;

import java.lang.reflect.UndeclaredThrowableException;

import org.audit4j.core.schedule.util.ErrorHandler;

/**
 * Runnable wrapper that catches any exception or error thrown from its delegate
 * Runnable and allows an {@link ErrorHandler} to handle it.
 * 
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 3.0
 */
public class DelegatingErrorHandlingRunnable implements Runnable {
    
    /** The delegate. */
    private final Runnable delegate;
    
    /** The error handler. */
    private final ErrorHandler errorHandler;

    /**
     * Create a new DelegatingErrorHandlingRunnable.
     * 
     * @param delegate
     *            the Runnable implementation to delegate to
     * @param errorHandler
     *            the ErrorHandler for handling any exceptions
     */
    public DelegatingErrorHandlingRunnable(Runnable delegate, ErrorHandler errorHandler) {
     //   Assert.notNull(delegate, "Delegate must not be null");
       // Assert.notNull(errorHandler, "ErrorHandler must not be null");
        this.delegate = delegate;
        this.errorHandler = errorHandler;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Runnable#run()
     *
     */
    @Override
    public void run() {
        try {
            this.delegate.run();
        } catch (UndeclaredThrowableException ex) {
            this.errorHandler.handleError(ex.getUndeclaredThrowable());
        } catch (Throwable ex) {
            this.errorHandler.handleError(ex);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     *
     */
    @Override
    public String toString() {
        return "DelegatingErrorHandlingRunnable for " + this.delegate;
    }
}