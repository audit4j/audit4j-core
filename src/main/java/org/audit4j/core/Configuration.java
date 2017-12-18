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

package org.audit4j.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.jmx.JMXConfig;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.layout.SimpleLayout;

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
    private String commands;
    
    /** The jmx configurations. */
    private JMXConfig jmx;

    /**
     * Annotation transformer to be used
     */
    private AnnotationTransformer annotationTransformer;

    /** Return new static instance. 
     * 
     * @since 2.3.1
     * */
    public static Configuration INSTANCE = new Configuration();
    
    /** Return default configurations of audit4j. 
     * 
     * @since 2.3.1
     * */
    public static Configuration DEFAULT = getDefault();
    
    /**
     * Instantiates a new configuration.
     */
    public Configuration() {

    }
    
    /**
     * Gets the default.
     *
     * @return the default
     * 
     * @since 2.3.1
     */
    private static Configuration getDefault(){
        Configuration config = new Configuration();
        config.addHandler(new ConsoleAuditHandler());
        config.setMetaData(new DummyMetaData());
        config.setLayout(new SimpleLayout());
        config.addProperty("log.file.location", "user.dir");
        return config;
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
    public String getCommands() {
        return commands;
    }

    /**
     * Sets the options.
     *
     * @param options the new options
     * 
     * @since 2.3.0
     */
    public void setCommands(String options) {
        this.commands = options;
    }

    /**
     * Gets the jmx configurations.
     *
     * @return jmx configurations
     * 
     * @since 2.4.0
     */
    public JMXConfig getJmx() {
        return jmx;
    }

    /**
     * Sets the jmx configurations.
     *
     * @param jmx the jmx configurations
     * 
     * @since 2.4.0
     */
    public void setJmx(JMXConfig jmx) {
        this.jmx = jmx;
    }

    /**
     * Gets the annotation transformer to use when converting {@code AnnotationAuditEvent}. Allows to inject custom
     * conversion logic and custom output type.
     *
     * @return the annotation transformer
     */
    public AnnotationTransformer getAnnotationTransformer() {
        return annotationTransformer;
    }

    /**
     * Sets annotation transformer to use.
     * 
     * @param annotationTransformer the Annotation Transformer 
     * 
     */
    public void setAnnotationTransformer(AnnotationTransformer annotationTransformer) {
        this.annotationTransformer = annotationTransformer;
    }
}
