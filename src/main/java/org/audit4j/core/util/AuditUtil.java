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

package org.audit4j.core.util;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * The Class AuditUtil.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0
 */
public final class AuditUtil {

    /**
     * Instantiates a new audit util.
     */
    private AuditUtil() {
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
     * Date to string.
     * 
     * @param date
     *            the date
     * @param format
     *            the format
     * @return the string
     */
    public static String dateToString(final Date date, final String format) {
        if (date == null) {
            return null;
        }
        final DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(date);
    }
    
    /**
     * Convert string to date.
     *
     * @param dateString the date string
     * @param format the format
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date stringTodate(String dateString, String format) throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.parse(dateString);
    }

    /**
     * Time stamp to string.
     * 
     * @param timestamp
     *            the timestamp
     * @param format
     *            the format
     * @return the string
     */
    public static String timeStampToString(final Timestamp timestamp, final String format) {
        return dateToString(new Date(timestamp.getTime()), format);
    }

    /**
     * Checks if is file exists.
     *
     * @param filePathString the file path string
     * @return true, if is file exists
     */
    public static boolean isFileExists(String filePathString) {
        File file = new File(filePathString);
        if (file.exists() && !file.isDirectory())
            return true;
        return false;
    }

}
