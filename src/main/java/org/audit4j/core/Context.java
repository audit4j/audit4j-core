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

import org.audit4j.core.command.CommandProcessor;
import org.audit4j.core.command.impl.BatchCommand;
import org.audit4j.core.command.impl.MetadataCommand;
import org.audit4j.core.command.impl.ObjectSerializerCommand;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.exception.ValidationException;
import org.audit4j.core.filter.AuditAnnotationFilter;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.io.AsyncAnnotationAuditOutputStream;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;
import org.audit4j.core.io.BatchProcessStream;
import org.audit4j.core.io.MetadataLookupStream;
import org.audit4j.core.jmx.MBeanAgent;
import org.audit4j.core.schedule.Schedulers;
import org.audit4j.core.util.EnvUtil;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;

/**
 * The Audit4j Context. This will load and execute required resources in to the
 * memory when initializing audit4j, Context makes sure necessary resources
 * provide when running audit4j and it's plugins. Also this will ensure to
 * release the memory allocated by audit4j when shutdown.
 * 
 * <p>
 * Available public methods:
 * </p>
 * <ul>
 * <li>{@link #init()} Initialize the Audit4j context.</li>
 * <li>{@link #initWithConfiguration(Configuration configuration)} Initialize
 * the Audit4j context with external configurations.</li>
 * <li>{@link #initWithConfiguration(String configFilePath)} Initialize the
 * Audit4j context with external configuration file.</li>
 * <li>{@link #stop()} Shutdown Audit4j. This will free memory allocated by the
 * Audit4j</li>
 * <li>{@link #enable()} Enable audit4j.</li>
 * <li>{@link #disable()} Disable audit4j.</li>
 * <li>{@link #terminate()} Terminate audit4j.</li>
 * <li>{@link #getConfigContext()} get initialized configurations.</li>
 * <li>{@link #setConfig(Configuration configuration)} set initial and external
 * configurations in to context.</li>
 * </ul>
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public final class Context {

	/** The Configuration instance. One time initialize. */
	private static Configuration conf;

	/** The config file path. */
	private static String configFilePath;

	/** The audit output stream. */
	private static AuditOutputStream<AuditEvent> auditStream;

	/** The config context. */
	private static ConcurrentConfigurationContext configContext;

	/** The Constant INIT_FAILED. */
	private static final String INIT_FAILED = "initialization failed.!!";

	private static final LifeCycleContext lifeCycle = LifeCycleContext.getInstance();

	private static AnnotationTransformer annotationTransformer;

	/**
	 * Private singalton.
	 */
	private Context() {
		// Nothing here. Private Constructor
	}
	
	/**
	 * Initialize the Audit4j instance. This will ensure the single audit4j
	 * instance and single Configuration repository load in to the memory.
	 */
	final static void init() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Audit4jInit");
		if (configContext == null) {
			configContext = new ConcurrentConfigurationContext();
		}

		if (lifeCycle.getStatus().equals(RunStatus.READY) || lifeCycle.getStatus().equals(RunStatus.STOPPED)) {
			Audit4jBanner banner = new Audit4jBanner();
			banner.printBanner();
			Log.info("Initializing Audit4j...");
			
			// Check system environment;
			checkEnvironment();
			
			// Load configurations to Memory
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

			// Execute commands.
			CommandProcessor.getInstance().process(conf.getCommands());

			// Load Registry configurations.
			loadRegistry();

			if (conf.getProperties() == null) {
				conf.setProperties(new HashMap<String, String>());
			} else {
				for (Map.Entry<String, String> entry : conf.getProperties().entrySet()) {
					if (System.getProperties().containsKey(entry.getValue())) {
						conf.getProperties().put(entry.getKey(), System.getProperty(entry.getValue()));
					}
				}
			}
			configContext.getProperties().putAll(conf.getProperties());

			// Initialize handlers.
			initHandlers();

			// Initialize layouts.
			initLayout();

			// Initialize annotation transformer.

			if (conf.getAnnotationTransformer() == null) {
				annotationTransformer = getDefaultAnnotationTransformer();
			} else {
				annotationTransformer = conf.getAnnotationTransformer();
			}

			// Initialize IO streams.
			initStreams();

			if (!conf.getFilters().isEmpty()) {
			    Log.info("Registoring Filters...");
            }
			for (AuditEventFilter filter : conf.getFilters()) {
				configContext.addFilter(filter);
				Log.info(filter.getClass().getName() +  " Registored...");
			}

			configContext.setMetaData(conf.getMetaData());

			// Execute Scheduler tasks.
			Log.info("Executing Schedulers...");
			Schedulers.taskRegistry().scheduleAll();

			// Initialize monitoring support if available in the configurations.
			if (conf.getJmx() != null) {
				MBeanAgent agent = new MBeanAgent();
				agent.setJmxConfig(conf.getJmx());
				agent.init();
				agent.registerMbeans();
			}

			lifeCycle.setStatus(RunStatus.RUNNING);
			lifeCycle.setStartUpTime(new Date().getTime());
			stopWatch.stop();
			Long initializationTime = stopWatch.getLastTaskTimeMillis();
			Log.info("Audit4j initialized. Total time: ", initializationTime, "ms");
		}
	}

	/**
	 * Initialize audit4j with external configurations.
	 * 
	 * This method allows to external plugins can inject the configurations.
	 * Since the security reasons, this allows to create one time configuration
	 * setting to Audit4j.
	 * 
	 * @param configuration
	 *            the configuration
	 * 
	 * @since 2.3.1
	 */
	public static void initWithConfiguration(Configuration configuration) {
		Context.setConfig(configuration);
		init();
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
	 * @since 2.3.1
	 */
	public static void initWithConfiguration(String configFilePath) {
		Context.setConfigFilePath(configFilePath);
		init();
	}

	/**
	 * The Audit4j context shutdown method. This will release the all allocated
	 * resources by the Audit4j Context initialization.
	 * 
	 * @since 2.2.0
	 */
	final static void stop() {
		if (lifeCycle.getStatus().equals(RunStatus.RUNNING) || lifeCycle.getStatus().equals(RunStatus.DISABLED)) {
			lifeCycle.setStatus(RunStatus.STOPPED);
			Log.info("Preparing to shutdown Audit4j...");

			Log.info("Closing Streams...");
			auditStream.close();

			Log.info("Shutdown handlers...");
			for (Handler handler : configContext.getHandlers()) {
				handler.stop();
				Log.info(handler.getClass().getName() + " shutdown.");
			}

			Log.info("Disposing configurations...");
			configContext = null;
			conf = null;
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
		if (lifeCycle.getStatus().equals(RunStatus.READY) || lifeCycle.getStatus().equals(RunStatus.STOPPED)) {
			init();
		} else if (lifeCycle.getStatus().equals(RunStatus.DISABLED)) {
			lifeCycle.setStatus(RunStatus.RUNNING);
		}
	}

	/**
	 * Disable Audit4j core services.
	 * 
	 * @since 2.2.0
	 */
	final static void disable() {
		Log.warn("Audit4j Disabled.!!");
		lifeCycle.setStatus(RunStatus.DISABLED);
	}

	/**
	 * Terminate Audit4j core services.
	 * 
	 * @since 2.3.0
	 */
	final static void terminate() {
		Log.warn("Audit4j Terminated due to critical error.");
		lifeCycle.setStatus(RunStatus.TERMINATED);
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
			Log.error("Your Java version (", EnvUtil.getJavaVersion(), ") is not supported for Audit4j. ",
					ErrorGuide.getGuide(ErrorGuide.JAVA_VERSION_ERROR));
			throw new InitializationException("Java version is not supported.");
		}
	}

	/**
	 * Load configuration items.
	 */
	private final static void loadConfig() {
		try {
			conf = Configurations.loadConfig(configFilePath);
		} catch (ConfigurationException e) {
			terminate();
			throw new InitializationException(INIT_FAILED, e);
		}
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
					Map<String, String> handlerproperties = new HashMap<>();
					handlerproperties.putAll(configContext.getProperties());
					handler.setProperties(handlerproperties);
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
		MetadataCommand command = (MetadataCommand) PreConfigurationContext.getCommandByName("-metadata");
		BatchCommand batchCommand = (BatchCommand) PreConfigurationContext.getCommandByName("-batchSize");
		
		AsyncAnnotationAuditOutputStream asyncAnnotationStream = new AsyncAnnotationAuditOutputStream(
                new AuditProcessOutputStream(Context.getConfigContext()), annotationTransformer);
		
        AuditProcessOutputStream processStream =  new AuditProcessOutputStream(Context.getConfigContext());
        
        
		if (command.isAsync()) {
		    MetadataLookupStream metadataStream;
		    if(batchCommand.getBatchSize() > 0) {
	            BatchProcessStream batchStream = new BatchProcessStream(processStream, batchCommand.getBatchSize());
	            metadataStream = new MetadataLookupStream(batchStream);
		    } else {
		        metadataStream = new MetadataLookupStream(processStream);
		    }
			
			AsyncAuditOutputStream asyncStream = new AsyncAuditOutputStream(metadataStream, asyncAnnotationStream);
			auditStream = new AuditEventOutputStream(asyncStream, configContext);
		} else {
		    AsyncAuditOutputStream asyncStream;
		    if(batchCommand.getBatchSize() > 0) {
                BatchProcessStream batchStream = new BatchProcessStream(processStream, batchCommand.getBatchSize());
                asyncStream = new AsyncAuditOutputStream(batchStream, asyncAnnotationStream);
            } else {
                asyncStream = new AsyncAuditOutputStream(processStream, asyncAnnotationStream);
            }
			MetadataLookupStream metadataStream = new MetadataLookupStream(asyncStream);
			auditStream = new AuditEventOutputStream(metadataStream, configContext);
		}

		Log.info("Audit Streams Initialized.");
	}

	/**
	 * Initializes and returns the default annotation transformer.
	 *
	 * @return default annotation transformer
     */
	private static AnnotationTransformer<AuditEvent> getDefaultAnnotationTransformer() {
		DefaultAnnotationTransformer defaultAnnotationTransformer = new DefaultAnnotationTransformer();
		ObjectSerializerCommand serializerCommand = (ObjectSerializerCommand) PreConfigurationContext
				.getCommandByName("-objectSerializer");
		if (serializerCommand.getSerializer() == null) {
			defaultAnnotationTransformer.setSerializer(new ObjectToFieldsSerializer());
		} else {
			defaultAnnotationTransformer.setSerializer(serializerCommand.getSerializer());
		}

		return defaultAnnotationTransformer;
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
	 * 			Gets the config.
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
	final static AuditOutputStream<AuditEvent> getAuditStream() {
		return auditStream;
	}

	/**
	 * Gets the running status.
	 * 
	 * @return the status
	 * @since 2.2.0
	 */
	public static RunStatus getStatus() {
		return lifeCycle.getStatus();
	}

}
