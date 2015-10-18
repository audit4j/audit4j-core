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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.audit4j.core.filter.AuditAnnotationFilter;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.util.annotation.ThreadSafe;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;

/**
 * Configuration item store. All configuration items which are used by the
 * running components store here. Items loaded by the context.
 * 
 * <p>
 * Below Items store here:
 * </p>
 * <ul>
 * <li>Available handlers.</li>
 * <li>Audit event filters.</li>
 * <li>Configured layout.</li>
 * <li>Meta data, If available</li>
 * <li>Additional configuration properties.</li>
 * </ul>
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
@ThreadSafe
public final class ConcurrentConfigurationContext {

    /** The handlers. */
    private final List<Handler> handlers = new CopyOnWriteArrayList<Handler>();

    /** The properties. */
    private final Map<String, String> properties = new ConcurrentHashMap<String, String>();

    /** The Constant filters. */
    private final List<AuditEventFilter> filters = new CopyOnWriteArrayList<AuditEventFilter>();

    /** The Constant filters. */
    private final List<AuditAnnotationFilter> annotationFilters = new CopyOnWriteArrayList<AuditAnnotationFilter>();

    /** The layout. */
    private Layout layout;

    /** The meta data. */
    private MetaData metaData;

    /**
     * The run status.
     * 
     * @deprecated see {@link LifeCycleContext#getStatus()} instead.
     * */
    @Deprecated
    private RunStatus runStatus = RunStatus.READY;

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
        return handlers;
    }

    /**
     * Gets the properties.
     * 
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Gets the filters.
     * 
     * @return the filters
     */
    public List<AuditEventFilter> getFilters() {
        return filters;
    }

    /**
     * Adds the handler.
     * 
     * @param handler
     *            the handler
     */
    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    /**
     * Adds the property.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    /**
     * Adds the filter.
     * 
     * @param filter
     *            the filter
     */
    public void addFilter(AuditEventFilter filter) {
        filters.add(filter);
    }

    /**
     * Gets the annotation filters.
     * 
     * @return the annotation filters
     */
    public List<AuditAnnotationFilter> getAnnotationFilters() {
        return annotationFilters;
    }

    /**
     * Adds the annotation filter.
     * 
     * @param annotationFilter
     *            the annotation filter
     */
    public void addAnnotationFilter(AuditAnnotationFilter annotationFilter) {
        annotationFilters.add(annotationFilter);
    }

    /**
     * Checks for meta data available in configuration.
     *
     * @return true, if successful
     */
    public boolean hasMetadata(){
        if(Context.getConfigContext().getMetaData() == null){
            return false;
        }
        return true;
    }
    
    /**
     * Gets the run status.
     * 
     * @return the run status
     * 
     * @deprecated see {@link LifeCycleContext#getStatus()} instead.
     */
    @Deprecated
    public RunStatus getRunStatus() {
        return runStatus;
    }

    /**
     * Sets the run status.
     * 
     * @param runStatus
     *            the new run status
     * @deprecated see {@link LifeCycleContext#getStatus()} instead.
     */
    @Deprecated
    public void setRunStatus(RunStatus runStatus) {
        this.runStatus = runStatus;
    }
}
