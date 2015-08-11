package org.audit4j.core;

import java.net.URL;

import javax.servlet.ServletContext;

/**
 * The Interface URLProvider.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @since
 */
public class URLProvider {

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public URL getClasspathURL() {
        return URLProvider.class.getProtectionDomain().getCodeSource().getLocation();
    }

    public String getServletContextClasspath(ServletContext context) {
        return context.getRealPath("/WEB-INF/classes");
    }
}
