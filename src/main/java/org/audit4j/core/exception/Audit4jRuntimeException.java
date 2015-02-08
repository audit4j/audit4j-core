package org.audit4j.core.exception;

import org.audit4j.core.util.Log;

/**
 * The Class Audit4jRuntimeException.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jRuntimeException extends RuntimeException {

    /** asdas. */
    private static final long serialVersionUID = -7750687125705697432L;

    /**
     * Instantiates a new audit4j runtime exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public Audit4jRuntimeException(String message, Throwable cause) {
        super(message, cause);
        Log.error("Problem while running Audit4j: " + message, cause);
    }
}
