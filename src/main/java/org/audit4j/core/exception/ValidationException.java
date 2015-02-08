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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ValidationException.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ValidationException extends Exception{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3273908609152516053L;
	
	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(ValidationException.class);

	/** The Constant VALIDATION_LEVEL_WARN. */
	public static final String VALIDATION_LEVEL_WARN = "WARN";
	
	/** The Constant VALIDATION_LEVEL_INVALID. */
	public static final String VALIDATION_LEVEL_INVALID = "INVALID";
			
	/** The level. */
	private final String level;
	
	/**
	 * Instantiates a new validation exception.
	 *
	 * @param message the message
	 * @param level the level
	 */
	public ValidationException(final String message, final String level){
		super(message);
		LOG.warn("Audit4j: Validation Exception: " + message);
		this.level = level;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

}
