package org.audit4j.core.schedule.util;

public interface ErrorHandler {
    /**
     * Handle the given error, possibly rethrowing it as a fatal exception.
     */
    void handleError(Throwable t);
}