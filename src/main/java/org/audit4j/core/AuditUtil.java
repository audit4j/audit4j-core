/*
 * Copyright 2014 Janith Bandara
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

package org.audit4j.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Class AuditUtil.
 * 
 * @author Janith Bandara
 */
public final class AuditUtil {

	/**
	 * Instantiates a new audit util.
	 */
	private AuditUtil() {
	}

	/**
	 * Builds the query.
	 * 
	 * @param paramMap
	 *            the param map
	 * @param action
	 *            the action
	 * @return the string
	 */
	public static String buildQuery(final Map<String, String> paramMap, String action) {
		if (paramMap != null) {
			final StringBuilder buff = new StringBuilder();
			if (action != null) {
				buff.append(action).append("==>");
			}
			for (final Map.Entry<String, String> entry : paramMap.entrySet()) {
				buff.append(entry.getKey()).append(":").append(entry.getValue()).append(" , ");
			}
			return buff.toString();
		} else {
			return "No data for selectred audit criteria";
		}
	}

	/**
	 * Transform map.
	 * 
	 * @param paramMap
	 *            the param map
	 * @return the map
	 */
	public static Map<String, String> transformMap(final Map<String, Object> paramMap) {
		final Map<String, String> paramStrMap = new LinkedHashMap<String, String>();
		for (final Map.Entry<String, Object> entry : paramMap.entrySet()) {
			paramStrMap.put(entry.getKey(), entry.getValue().toString());
		}

		return paramStrMap;
	}

	/**
	 * Compute target stream.
	 * 
	 * @param logFile
	 *            the log file
	 * @return the prints the stream
	 */
	private static PrintStream computeTargetStream(String logFile) {
		if ("System.err".equalsIgnoreCase(logFile))
			return System.err;
		else if ("System.out".equalsIgnoreCase(logFile)) {
			return System.out;
		} else {
			try {
				FileOutputStream fos = new FileOutputStream(logFile);
				PrintStream printStream = new PrintStream(fos);
				return printStream;
			} catch (FileNotFoundException e) {
				AuditUtil.report("Could not open [" + logFile + "]. Defaulting to System.err", e);
				return System.err;
			}
		}
	}

	/**
	 * Gets the uuid.
	 * 
	 * @return the uuid
	 */
	protected Long getUUID() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	/**
	 * Report.
	 * 
	 * @param msg
	 *            the msg
	 * @param t
	 *            the t
	 */
	static final public void report(String msg, Throwable t) {
		System.err.println(msg);
		System.err.println("Reported exception:");
		t.printStackTrace();
	}

	/**
	 * Report.
	 * 
	 * @param msg
	 *            the msg
	 */
	static final public void report(String msg) {
		System.err.println("AUDIT4J: " + msg);
	}
}
