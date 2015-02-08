/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.exception;

import org.audit4j.core.util.Log;


/**
 * The Class TroubleshootException.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class TroubleshootException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7969806069690822408L;

	/**
	 * Instantiates a new troubleshoot exception.
	 * 
	 * @param message
	 *            the message
	 */
	public TroubleshootException(String message) {
		super(message);
		Log.warn("Audit4j: Unable to find a solution for error: " + message);
	}

	/**
	 * Instantiates a new troubleshoot exception.
	 *
	 * @param message the message
	 * @param t the t
	 */
	public TroubleshootException(String message, Throwable t) {
		super(message, t);
		Log.warn("Audit4j: Unable to find a solution for error: " + message, t);
	}
}
