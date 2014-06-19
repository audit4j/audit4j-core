package org.audit4j.core;

/**
 * The Class Log.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Log {

	/** The Constant AUDIT4J_INFO. */
	private static final String AUDIT4J_INFO = CoreConstants.APP_NAME + ":INFO";

	/** The Constant AUDIT4J_WARN. */
	private static final String AUDIT4J_WARN = CoreConstants.APP_NAME + ":WARN";

	/** The Constant AUDIT4J_ERROR. */
	private static final String AUDIT4J_ERROR = CoreConstants.APP_NAME + ":ERROR";

	/**
	 * Info.
	 *
	 * @param message the message
	 */
	public static void info(Object message) {
		System.out.println(AUDIT4J_INFO + message.toString());
	}

	/**
	 * Warn.
	 *
	 * @param message the message
	 */
	public static void warn(Object message) {
		System.err.println(AUDIT4J_WARN + message.toString());
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 */
	public static void error(Object message) {
		System.err.println(AUDIT4J_ERROR + message.toString());
	}
}
