/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
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

package org.audit4j.core.handler;

import java.io.PrintStream;

import org.audit4j.core.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConsoleAuditHandler.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConsoleAuditHandler extends Handler {

	/**
	 * asdas
	 */
	private static final long serialVersionUID = -4570535029942402303L;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ConsoleAuditHandler.class);

	/** The Constant AUDIT4J_USER. */
	private static final String AUDIT4J_USER = "AUDIT4J";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#handle()
	 */
	@Override
	public void handle() {
		final String logText = getQuery();
		PrintStream stream = System.out;
		stream.println(logText);
	}

	@Override
	public void init() throws InitializationException {
		// TODO Auto-generated method stub

	}
}
