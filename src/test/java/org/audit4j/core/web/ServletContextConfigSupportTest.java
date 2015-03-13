package org.audit4j.core.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;

import org.audit4j.core.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class ServletContextConfigSupportTest {

    @Test
    public void testLoadConfig() {
        ServletContext context = mock(ServletContext.class);
        when(context.getInitParameter(ContextConfigParams.PARAM_HANDLERS)).thenReturn(
                "org.audit4j.core.handler.ConsoleAuditHandler;org.audit4j.core.handler.file.FileAuditHandler;");
        // when(context.getInitParameter(ContextConfigParams.PARAM_FILTERS)).thenReturn("");
        when(context.getInitParameter(ContextConfigParams.PARAM_LAYOUT)).thenReturn(
                "org.audit4j.core.layout.SimpleLayout");
        // when(context.getInitParameter(ContextConfigParams.PARAM_OPTIONS)).thenReturn("");
        when(context.getInitParameter(ContextConfigParams.PARAM_META_DATA))
                .thenReturn("org.audit4j.core.DummyMetaData");
        when(context.getInitParameter(ContextConfigParams.PARAM_PROPERTIES)).thenReturn(" log.file.location:user.dir");

        ServletContexConfigSupport support = new ServletContexConfigSupport();
        Assert.assertTrue(support.hasHandlers(context));

        Configuration config = support.loadConfig(context);
        Assert.assertNotNull(config.getHandlers());
    }
}
