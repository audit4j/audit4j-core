package org.audit4j.core.exception;

import org.audit4j.core.Log;
import org.audit4j.core.handler.Handler;

/**
 * The Class HandlerException.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class HandlerException  extends Exception{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4852580383596831745L;

	/**
	 * Instantiates a new handler exception.
	 *
	 * @param message the message
	 */
	public HandlerException(String message){
		super(message);
		Log.warn("Handler Exception Occured: " + message);
	}
	
	/**
	 * Instantiates a new handler exception.
	 *
	 * @param message the message
	 * @param e the e
	 */
	public HandlerException(String message, Class<? extends Handler> clazz, Exception e){
		super(message, e);
		Log.warn("Handler Exception Occured in " + clazz.getName() + ":"+ message);
	}
}
