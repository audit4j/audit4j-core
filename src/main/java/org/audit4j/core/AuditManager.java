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

import java.lang.reflect.Method;
import java.util.List;

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.filter.AuditAnnotationFilter;

/**
 * The AuditManager. This class is used to submit audit events as well as
 * annotations. This is the only audit submition end point of the Audit4j.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public final class AuditManager {

    /**
     * Instantiates a new audit manager.
     */
    private AuditManager() {
    }

    /** The audit manager. */
    private static AuditManager auditManager;

    /**
     * Initializing context and streams.
     * 
     * @since 2.0.0
     */
    private static void init() {
        Context.init();
    }

    /**
     * Audit.
     * 
     * @param event
     *            the event
     * @return true, if successful
     */
    public boolean audit(AuditEvent event) {
        Context.getAuditStream().write(event);
        return true;
    }

    /**
     * Audit with annotation.
     * 
     * @param clazz
     *            the clazz
     * @param method
     *            the method
     * @param args
     *            the args
     * @return true, if successful
     * 
     *         TODO FIXME not purely asynchronous. should moved to the logic in
     *         to asynchronous stream.
     */
    public boolean audit(Class<?> clazz, Method method, Object[] args) {
        return audit(new AnnotationAuditEvent(clazz, method, args));
    }

    /**
     * Audit.
     * 
     * @param annotationEvent
     *            the annotation event
     * @return true, if successful
     */
    public boolean audit(AnnotationAuditEvent annotationEvent) {
        List<AuditAnnotationFilter> filters = Context.getConfigContext().getAnnotationFilters();
        if (!filters.isEmpty()) {
            for (AuditAnnotationFilter filter : filters) {
                if (!filter.accepts(annotationEvent)) {
                    return false;
                }
            }
        }
        Context.getAnnotationStream().write(annotationEvent);
        return true;
    }

    /**
     * Gets the single instance of AuditHelper.
     * 
     * @return single instance of AuditHelper
     */
    public static AuditManager getInstance() {
        init();
        synchronized (AuditManager.class) {

            if (auditManager == null) {
                auditManager = new AuditManager();
            }
        }
        return auditManager;
    }

    /**
     * This method allows to external plugins can inject the configurations.
     * Since the security reasons, this allows to create one time configuration
     * setting to Audit4j.
     * 
     * @param configuration
     *            the configuration
     * @return the configuration instance
     * 
     * @since 2.1.0
     * 
     * @deprecated
     */
    @Deprecated
    public static AuditManager getConfigurationInstance(Configuration configuration) {
        Context.setConfig(configuration);
        return getInstance();
    }

    /**
     * Inits the with configuration.
     * 
     * @param configuration
     *            the configuration
     * @return the audit manager
     * @deprecated This method allows to external plugins can inject the
     *             configurations. Since the security reasons, this allows to
     *             create one time configuration setting to Audit4j.
     * @since 2.3.0
     */
    @Deprecated
    public static AuditManager initWithConfiguration(Configuration configuration) {
        Context.setConfig(configuration);
        return getInstance();
    }

    /**
     * This method allows to external plugins can inject the configurations.
     * Since the security reasons, this allows to create one time configuration
     * setting to Audit4j.
     * 
     * @param configuration
     *            the configuration
     * @return the audit manager
     * 
     * @since 2.3.1
     */
    public static AuditManager startWithConfiguration(Configuration configuration) {
        Context.setConfig(configuration);
        return getInstance();
    }

    /**
     * Initialize audit4j with external configuration file.
     * 
     * This method allows to external plugins can inject the configurations.
     * Since the security reasons, this allows to create one time configuration
     * setting to Audit4j.
     * 
     * @param configFilePath
     *            the config file path
     * @return the audit manager
     * @since 2.3.1
     */
    public static AuditManager startWithConfiguration(String configFilePath) {
        Context.setConfigFilePath(configFilePath);
        return getInstance();
    }

    /**
     * Shutdown.
     */
    public void shutdown() {
        Context.stop();
    }
    
    /**
     * Enable.
     */
    public void enable() {
        Context.enable();
    }
    
    /**
     * Disable.
     */
    public void disable() {
        Context.disable();
    }
}
