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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.audit4j.core.util.annotation.ThreadSafe;

/**
 * The Class ConcurrentDateFormatAccess.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
@ThreadSafe
public class ConcurrentDateFormatAccess {

    /** The format. */
    private final String format;

    /** The date format. */
    private final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat get() {
            return super.get();
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(format);
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public void set(DateFormat value) {
            super.set(value);
        }

    };

    /**
     * Instantiates a new concurrent date format access.
     *
     * @param format the format
     */
    public ConcurrentDateFormatAccess(String format) {
        super();
        this.format = format;
    }

    /**
     * Convert string to date.
     * 
     * @param dateString
     *            the date string
     * @return the date
     * @throws ParseException
     *             the parse exception
     */
    public Date convertStringToDate(String dateString) throws ParseException {
        return dateFormat.get().parse(dateString);
    }

    /**
     * Convert date to string.
     *
     * @param date the date
     * @return the string
     */
    public String convertDateToString(final Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.get().format(date);
    }
}
