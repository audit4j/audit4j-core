package org.audit4j.core.web;

import javax.servlet.ServletContext;

public final class WebContext {

    private static ServletContext servletContext;

    private static boolean initialized = false;

    static void init() {
        initialized = true;
    }

    static void stop() {
        initialized = false;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    static void setServletContext(ServletContext servletContext) {
        WebContext.servletContext = servletContext;
    }

    public static boolean isInitialized() {
        return initialized;
    }

}
