package org.audit4j.core.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AuditWebContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebContext.setServletContext(event.getServletContext());
        WebContext.init();
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        WebContext.stop();
    }

}
