package org.audit4j.core.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.audit4j.core.AuditManager;

/**
 * The listener interface for receiving auditContext events. The class that is
 * interested in processing a auditContext event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addAuditContextListener<code> method. When
 * the auditContext event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see AuditContextEvent
 */
public class AuditContextListener implements ServletContextListener {

    /** The config support. */
    private ServletContexConfigSupport configSupport = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        configSupport = new ServletContexConfigSupport();
        if (configSupport.hasHandlers(contextEvent.getServletContext())) {
            AuditManager.startWithConfiguration(configSupport.loadConfig(contextEvent.getServletContext()));
        } else {
            AuditManager.startWithConfiguration(getConfFilePath(contextEvent.getServletContext()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        AuditManager.getInstance().shutdown();
    }

    /**
     * Gets the conf file path.
     *
     * @param context the context
     * @return the conf file path
     */
    private String getConfFilePath(ServletContext context) {
        return context.getRealPath("/WEB-INF/classes");
    }

}
