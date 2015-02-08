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

/**
 * The Class ConfigurationException.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConfigurationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1297395824544190647L;
	
	/** The id. */
	private final String id;

	/**
	 * Instantiates a new configuration exception.
	 *
	 * @param message the message
	 * @param id the id
	 */
	public ConfigurationException(String message, String id) {
		super(message);
		this.id = id;
	}
	
	/**
	 * Instantiates a new configuration exception.
	 *
	 * @param message the message
	 * @param id the id
	 * @param e the e
	 */
	public ConfigurationException(String message, String id, Throwable e) {
		super(message, e);
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}
