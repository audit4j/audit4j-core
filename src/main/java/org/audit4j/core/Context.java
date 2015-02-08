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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.audit4j.core.command.CommandProcessor;
import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.exception.ValidationException;
import org.audit4j.core.filter.AuditAnnotationFilter;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.io.AnnotationAuditOutputStream;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;
import org.audit4j.core.util.EnvUtil;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;

/**
 * The Audit4j Context. This will load and execute required resources in to the
 * memory when initializing audit4j, Context makes sure necessary resources
 * provide when running audit4j. And this will release the memory allocated by
 * audit4j when shutdown.
 * 
 * <p>
 * Available public methods:
 * </p>
 * <ul>
 * <li>{@link #init()} Initialize the Audit4j context.</li>
 * <li>{@link #stop()} Shutdown Audit4j. This will free memory allocated by the
 * Audit4j</li>
 * <li>{@link #enable()} Enable audit4j.</li>
 * <li>{@link #disable()} Disable audit4j.</li>
 * <li>{@link #terminate()} Terminate audit4j.</li>
 * <li>{@link #getConfigContext()} get initialized configurations.</li>
 * <li>{@link #setConfig(Configuration conf)} set initial and external configurations in to
 * context.</li>
 * </ul>
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public final class Context {

    /** The initialize lock. */
    private static boolean initialized = false;

    /** The Configuration instance. One time initialize. */
    private static Configuration conf;

    /** The config file path. */
    private static String configFilePath;

    /** The audit output stream. */
    private static AuditOutputStream auditStream;

    /** The audit annotation stream. */
    private static AnnotationAuditOutputStream annotationAuditStream;

    /** The config context. */
    private static ConcurrentConfigurationContext configContext;

    /** The Constant INIT_FAILED. */
    private static final String INIT_FAILED = "initialization failed.!!";

    /**
     * Initialize the Audit4j instance. This will ensure the single audit4j
     * instance and single Configuration repository load in to the memory.
     */
    final static void init() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Audit4jInit");
        configContext = new ConcurrentConfigurationContext();
        if (!initialized
                && (configContext.getRunStatus().equals(RunStatus.READY) || configContext.getRunStatus().equals(
                        RunStatus.STOPPED))) {
            Log.info("Initializing Audit4j...");
            // Check system environment;
            checkEnvironment();
            Log.info("Loading Configurations...");
            
            if (conf == null) {
                loadConfig();
            }
            Log.info("Validating Configurations...");
            if (conf == null) {
                terminate();
                throw new InitializationException(INIT_FAILED);
            }

            try {
                ValidationManager.validateConfigurations(conf);
            } catch (ValidationException e1) {
                terminate();
                throw new InitializationException(INIT_FAILED, e1);
            }

            // Extract options.
            Map<String, String> options = processOptions(conf.getOptions());
            // Execute commands.
            if (options != null) {
                CommandProcessor.getInstance().process(options);
            }

            // Load Registry configurations.
            loadRegistry();

            if (conf.getProperties() != null) {
                for (Map.Entry<String, String> entry : conf.getProperties().entrySet()) {
                    if (System.getProperties().containsKey(entry.getValue())) {
                        conf.getProperties().put(entry.getKey(), System.getProperty(entry.getValue()));
                    }
                }
            }

            // Initialize handlers.
            initHandlers();

            // Initialize layouts.
            initLayout();

            // Initialize IO streams.
            initStreams();

            for (AuditEventFilter filter : conf.getFilters()) {
                configContext.addFilter(filter);
            }

            configContext.setMetaData(conf.getMetaData());

            initialized = true;
            configContext.setRunStatus(RunStatus.RUNNING);

            stopWatch.stop();
            Long initializationTime = stopWatch.getLastTaskTimeMillis();
            Log.info("Audit4j initialized. Total time: ", initializationTime, "ms");
        }
    }

    /**
     * The Audit4j context shutdown method. This will release the all allocated
     * resources by the Audit4j Context initialization.
     * 
     * @since 2.2.0
     */
    final static void stop() {
        if (configContext.getRunStatus().equals(RunStatus.RUNNING)) {
            Log.info("Preparing to shutdown Audit4j...");

            Log.info("Closing Streams...");
            auditStream.close();
            annotationAuditStream.close();

            Log.info("Shutdown handlers...");
            for (Handler handler : configContext.getHandlers()) {
                handler.stop();
                Log.info(handler.getClass().getName() + " shutdown.");
            }

            Log.info("Disposing configurations...");
            initialized = false;
            configContext.setRunStatus(RunStatus.STOPPED);
            Log.info("Audit4j shutdown completed.");
        } else {
            Log.info("No active Audit4j instance. Cancelling shutdown request.");
        }
    }

    /**
     * Enable Audit4j core services.
     * 
     * @since 2.2.0
     */
    final static void enable() {
        if (configContext.getRunStatus().equals(RunStatus.READY)
                || configContext.getRunStatus().equals(RunStatus.STOPPED)) {
            init();
        } else if (configContext.getRunStatus().equals(RunStatus.DISABLED)) {
            configContext.setRunStatus(RunStatus.RUNNING);
        }
    }

    /**
     * Disable Audit4j core services.
     * 
     * @since 2.2.0
     */
    final static void disable() {
        Log.warn("Audit4j Disabled.!!");
        configContext.setRunStatus(RunStatus.DISABLED);
    }

    /**
     * Terminate Audit4j core services.
     * 
     * @since 2.3.0
     */
    final static void terminate() {
        Log.warn("Audit4j Terminated due to critical error.");
        configContext.setRunStatus(RunStatus.TERMINATED);
    }

    /**
     * Gets the config context.
     * 
     * @return the config context
     */
    static ConcurrentConfigurationContext getConfigContext() {
        return configContext;
    }

    /**
     * Check environment.
     * 
     * @since 2.3.0
     */
    private final static void checkEnvironment() {
        // Check java support.!
        boolean javaSupport = EnvUtil.isJDK7OrHigher();
        if (!javaSupport) {
            Log.error("Your Java version (", EnvUtil.getJavaersion(), ") is not supported for Audit4j. ",
                    ErrorGuide.getGuide(ErrorGuide.JAVA_VERSION_ERROR));
            throw new InitializationException("Java version is not supported.");
        }
    }

    /**
     * Load configuration items.
     */
    private final static void loadConfig() {
        if (null == configFilePath) {
            configFilePath = CoreConstants.CONFIG_FILE_NAME;
        }
        try {
            conf = ConfigUtil.readConfig(configFilePath);
        } catch (ConfigurationException e) {
            try {
                TroubleshootManager.troubleshootConfiguration(e);
                conf = ConfigUtil.readConfig(configFilePath);
            } catch (TroubleshootException e2) {
                terminate();
                throw new InitializationException(INIT_FAILED);
            } catch (ConfigurationException e1) {
                terminate();
                throw new InitializationException(INIT_FAILED);
            }
        }
    }

    /**
     * Process options.
     * 
     * @param optionText
     *            the option text
     * @return the map
     * @since 2.3.0
     */
    private static Map<String, String> processOptions(String optionText) {
        if (optionText == null || optionText.isEmpty()) {
            return null;
        }
        Map<String, String> options = new HashMap<String, String>();
        String[] args = extractOptions(optionText);
        for (String arg : args) {
            String[] option = StringUtils.split(arg, CoreConstants.EQ_CHAR);
            if (!PreConfigurationContext.getOptions().contains(option[0])) {
                Log.warn("Invalid option: ", option[0], " Please check your configurations. ",
                        ErrorGuide.getGuide(ErrorGuide.INVALID_OPTION));
            }
            options.put(option[0], option[1]);
        }
        return options;
    }

    /**
     * Extract options.
     * 
     * @param optionText
     *            the option text
     * @return the string[]
     */
    private static String[] extractOptions(String optionText) {
        return StringUtils.split(optionText);
    }

    /**
     * Load initial configurations from Registry.
     * 
     * @since 2.3.0
     */
    private static void loadRegistry() {
        // Load audit filters to runtime configurations.
        for (AuditEventFilter filter : PreConfigurationContext.getPrefilters()) {
            configContext.addFilter(filter);
        }
        // Load audit annotation filters to runtime configurations.
        for (AuditAnnotationFilter annotationFilter : PreConfigurationContext.getPreannotationfilters()) {
            configContext.addAnnotationFilter(annotationFilter);
        }
    }

    /**
     * Initialize handlers.
     */
    private static void initHandlers() {
        Log.info("Initializing Handlers...");
        for (Handler handler : conf.getHandlers()) {
            try {
                if (!configContext.getHandlers().contains(handler)) {
                    handler.setProperties(conf.getProperties());
                    handler.init();
                    configContext.addHandler(handler);
                }
                Log.info(handler.getClass().getName() + " Initialized.");
            } catch (InitializationException e) {
                Log.error("There is a problem in the hander: ", handler.getClass().getName(),
                        ErrorGuide.getGuide(ErrorGuide.HANDLER_ERROR));
                terminate();
                throw new InitializationException(INIT_FAILED, e);
            }
        }
    }

    /**
     * Initialize layout.
     */
    private static void initLayout() {
        Log.info("Initializing Layout...");
        try {
            conf.getLayout().init();
            configContext.setLayout(conf.getLayout());
            Log.info(conf.getLayout().getClass().getName() + " Initialized.");
        } catch (InitializationException e) {
            Log.error("There is a problem in the layout: ", conf.getLayout().getClass().getName(), " you configured.",
                    ErrorGuide.getGuide(ErrorGuide.LAYOUT_ERROR));
            terminate();
            throw new InitializationException(INIT_FAILED);
        }

    }

    /**
     * Initialize streams.
     */
    private static void initStreams() {
        Log.info("Initializing Streams...");
        AsyncAuditOutputStream asyncStream = new AsyncAuditOutputStream(new AuditProcessOutputStream(configContext));
        auditStream = new AuditEventOutputStream(asyncStream);
        annotationAuditStream = new AnnotationAuditOutputStream(auditStream);
        Log.info("Audit Streams Initialized.");
    }

    /**
     * Sets the config.
     * 
     * @param conf
     *            the new config
     */
    final static void setConfig(Configuration conf) {
        Context.conf = conf;
    }

    /**
     * Gets the config.
     * 
     * @return the config
     * @deprecated
     * 
     *             Gets the config.
     */
    @Deprecated
    static Configuration getConfig() {
        return conf;
    }

    /**
     * Sets the config file path.
     * 
     * @param configFilePath
     *            the new config file path
     */
    public static void setConfigFilePath(String configFilePath) {
        Context.configFilePath = configFilePath;
    }

    /**
     * Gets the audit stream.
     * 
     * @return the audit stream
     */
    final static AuditOutputStream getAuditStream() {
        return auditStream;
    }

    /**
     * Gets the annotation stream.
     * 
     * @return the annotation stream
     */
    final static AnnotationAuditOutputStream getAnnotationStream() {
        return annotationAuditStream;
    }

    /**
     * Checks if is initialized.
     * 
     * @return true, if is initialized
     * 
     * @deprecated use {@link #getStatus()} instead.
     */
    @Deprecated
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Gets the running status.
     * 
     * @return the status
     * @since 2.2.0
     */
    public static RunStatus getStatus() {
        return configContext.getRunStatus();
    }

    /**
     * Private singalton.
     */
    private Context() {
        // Nothing here.
    }

}
