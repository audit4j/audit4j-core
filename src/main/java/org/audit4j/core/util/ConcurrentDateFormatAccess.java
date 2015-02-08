package org.audit4j.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class ConcurrentDateFormatAccess.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConcurrentDateFormatAccess {

    /** The format. */
    private final String format;

    /**
     * Instantiates a new concurrent date format access.
     *
     * @param format the format
     */
    public ConcurrentDateFormatAccess(String format) {
        super();
        this.format = format;
    }

    /** The df. */
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
