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

package org.audit4j.core;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
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
	
    /**
     * Date to string.
     * 
     * @param date the date
     * @param format the format
     * @return the string
     */
    public static String dateToString(final Date date, final String format) {
        if (date == null) {
            return null;
        }
        final DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(date);
    }
    

    public static String timeStampToString(final Timestamp timestamp, final String format) {
        return dateToString(new Date(timestamp.getTime()), format);
    }
}
