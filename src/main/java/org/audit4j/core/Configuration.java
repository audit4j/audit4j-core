/*
 * Copyright 2014 Janith Bandara, This source is a part of 
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

package org.audit4j.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;

/**
 * The Class Configuration.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public class Configuration implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3370288956459623002L;

    /** The released. */
    Date released;

    /** The version. */
    String version;

    /** The layout. */
    private Layout layout;

    /** The handlers. */
    private List<Handler> handlers;

    /** The meta data. */
    private MetaData metaData;

    /** The properties. */
    private Map<String, String> properties;

    /** The filters. */
    private List<AuditEventFilter> filters;

    /** The options. */
    private String options;

    /**
     * Instantiates a new configuration.
     */
    public Configuration() {

    }

    /**
     * Gets the released.
     * 
     * @return the released
     */
    public Date getReleased() {
        return released;
    }

    /**
     * Sets the released.
     * 
     * @param released
     *            the new released
     */
    public void setReleased(Date released) {
        this.released = released;
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the meta data.
     * 
     * @return the meta data
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the meta data.
     * 
     * @param metaData
     *            the new meta data
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the handlers.
     * 
     * @return the handlers
     */
    public List<Handler> getHandlers() {
        if (null == handlers) {
            handlers = new ArrayList<>();
        }
        return handlers;
    }

    /**
     * Sets the handlers.
     * 
     * @param handlers
     *            the new handlers
     */
    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Adds the handler.
     * 
     * @param handler
     *            the handler
     * @since 2.2.0
     */
    public void addHandler(Handler handler) {
        if (null == handlers) {
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
    }

    /**
     * Gets the layout.
     * 
     * @return the layout
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Sets the layout.
     * 
     * @param layout
     *            the new layout
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * Gets the properties.
     * 
     * @return the properties
     */
    public Map<String, String> getProperties() {
        if (null == properties) {
            properties = new HashMap<>();
        }
        return properties;
    }

    /**
     * Sets the properties.
     * 
     * @param properties
     *            the properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Adds the property.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     * @since 2.2.0
     */
    public void addProperty(String key, String value) {
        if (null == properties) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }

    /**
     * Gets the filters.
     *
     * @return the filters
     * 
     * @since 2.2.0
     */
    public List<AuditEventFilter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<AuditEventFilter>();
        }
        return filters;
    }

    /**
     * Sets the filters.
     *
     * @param filters the new filters
     * 
     * @since 2.2.0
     */
    public void setFilters(List<AuditEventFilter> filters) {
        this.filters = filters;
    }
    
    /**
     * Adds the filter.
     *
     * @param filter the filter
     * 
     * @since 2.3.0
     */
    public void addFilter(AuditEventFilter filter){
        if (filters == null) {
            filters = new ArrayList<AuditEventFilter>();
        }
        filters.add(filter);
    }

    /**
     * Gets the options.
     *
     * @return the options
     * 
     * @since 2.3.0
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the options.
     *
     * @param options the new options
     * 
     * @since 2.3.0
     */
    public void setOptions(String options) {
        this.options = options;
    }
}
