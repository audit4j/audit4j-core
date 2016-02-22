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

package org.audit4j.core.web;

import javax.servlet.ServletContext;

import org.audit4j.core.Configuration;
import org.audit4j.core.MetaData;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.util.ReflectUtil;

/**
 * The Class ServletContexConfigSupport.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.1
 */
class ServletContexConfigSupport {

    /**
     * Load config.
     * 
     * @param servletContext
     *            the servlet context
     * @return the configuration
     */
    Configuration loadConfig(ServletContext servletContext) {
        String handlers = servletContext.getInitParameter(ContextConfigParams.PARAM_HANDLERS);
        String layout = servletContext.getInitParameter(ContextConfigParams.PARAM_LAYOUT);
        String filters = servletContext.getInitParameter(ContextConfigParams.PARAM_FILTERS);
        String options = servletContext.getInitParameter(ContextConfigParams.PARAM_OPTIONS);
        String metaData = servletContext.getInitParameter(ContextConfigParams.PARAM_META_DATA);
        String properties = servletContext.getInitParameter(ContextConfigParams.PARAM_PROPERTIES);

        Configuration config = Configuration.INSTANCE;
        if (handlers != null && !handlers.equals("")) {
            config.setHandlers(new ReflectUtil<Handler>().getNewInstanceList(handlers.split(";")));
        }
        config.setLayout(new ReflectUtil<Layout>().getNewInstance(layout));
        if (filters != null && !filters.equals("")) {
            config.setFilters(new ReflectUtil<AuditEventFilter>().getNewInstanceList(filters.split(";")));
        }
        config.setCommands(options);
        config.setMetaData(new ReflectUtil<MetaData>().getNewInstance(metaData));
        if (properties != null && !properties.equals("")) {
            String[] propertiesList = properties.split(";");
            for (String property : propertiesList) {
                String[] keyValue = property.split(":");
                config.addProperty(keyValue[0], keyValue[1]);
            }
        }
        return config;
    }

    /**
     * Can search config file.
     *
     * @param servletContext the servlet context
     * @return true, if successful
     */
    boolean canSearchConfigFile(ServletContext servletContext) {
        String searchConfigFile = servletContext.getInitParameter("searchConfigFile");
        if (searchConfigFile == null || searchConfigFile.equals("")) {
            return false;
        } else if ("true".equals(searchConfigFile)) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks for handlers.
     *
     * @param servletContext the servlet context
     * @return true, if successful
     */
    boolean hasHandlers(ServletContext servletContext) {
        String handlers = servletContext.getInitParameter("handlers");
        return !(handlers == null || handlers.equals(""));
    }
}
