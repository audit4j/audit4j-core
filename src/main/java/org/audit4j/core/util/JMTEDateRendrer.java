package org.audit4j.core.util;

import java.util.Date;
import java.util.Locale;

import com.floreysoft.jmte.Renderer;

/**
 * The Class JMTEDateRendrer.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class JMTEDateRendrer implements Renderer<Date> {

    /** The date format. */
    private String dateFormat;

    /**
     * {@inheritDoc}
     * 
     * @see com.floreysoft.jmte.Renderer#render(java.lang.Object, java.util.Locale)
     *
     */
    @Override
    public String render(Date date, Locale local) {
        ConcurrentDateFormatAccess dateFormatter = new ConcurrentDateFormatAccess(dateFormat);
        return dateFormatter.convertDateToString(date);
    }

    /**
     * Sets the date format.
     * 
     * @param dateFormat
     *            the new date format
     */
    public void setDateFormat(final String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
