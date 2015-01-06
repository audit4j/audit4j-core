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

import java.util.Map;

import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.exception.ValidationException;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.io.AnnotationAuditOutputStream;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;

/**
 * The Audit4j Context. This will initialize required resources in to the memory
 * when initializing audit4j, make sure necessary resources provide when running
 * audit4j. And this will release the memory allocated by audit4j when shutdown
 * the audit4j.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public final class Context {

	/** The initialize lock. */
	private static boolean initialized = false;

	/** The status. */
	private static RunStatus status = RunStatus.READY;

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


	/**
	 * Initialize the Audit4j instance. This will ensure the single audit4j
	 * instance and single Configuration repository load in to the memory.
	 */
	final static void init() {
		if (!initialized && (status.equals(RunStatus.READY) || status.equals(RunStatus.STOPPED))) {
			configContext = new ConcurrentConfigurationContext();

			Log.info("Initializing Audit4j...");
			Log.info("Loading Configurations...");
			if (conf == null) {
				loadConfig();
			}
			Log.info("Validating Configurations...");
			if (conf == null) {
				throw new InitializationException("initialization failed.!!");
			} else {
				try {
					ValidationManager.validateConfigurations(conf);
				} catch (ValidationException e1) {
					throw new InitializationException("initialization failed.!!", e1);
				}
				if (conf.getProperties() != null) {
					for (Map.Entry<String, String> entry : conf.getProperties().entrySet()) {
						if (System.getProperties().containsKey(entry.getValue())) {
							conf.getProperties().put(entry.getKey(), System.getProperty(entry.getValue()));
						}
					}
				}

				initHandlers();
				initLayout();
				initStreams();

				configContext.setMetaData(conf.getMetaData());

				initialized = true;
				status = RunStatus.RUNNING;
				Log.info("Audit4j initialized.");
			}
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
				throw new InitializationException("Initialization failed.!!", e2);
			} catch (ConfigurationException e1) {
				throw new InitializationException("Initialization failed.!!", e1);
			}
		}
	}

	/**
	 * Initialize handlers.
	 */
	private static void initHandlers() {
		Log.info("Initializing Handlers...");
		for (Handler handler : conf.getHandlers()) {
			try {
				handler.setProperties(conf.getProperties());
				handler.init();
				configContext.addHandler(handler);
				Log.info(handler.getClass().getName() + " Initialized.");
			} catch (InitializationException e) {
				throw new InitializationException("initialization failed.!!", e);
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
			throw new InitializationException("initialization failed.!!", e);
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
	 * The Audit4j context shutdown method. This will release the all allocated
	 * resources by the Audit4j Context initialization.
	 * 
	 * @since 2.2.0
	 */
	final static void stop() {
		if (initialized) {
			Log.info("Preparing to shutdown Audit4j...");

			Log.info("Closing Streams...");
			auditStream.close();
			annotationAuditStream.close();

			Log.info("Shutdown handlers...");
			for (Handler handler : conf.getHandlers()) {
				handler.stop();
				Log.info(handler.getClass().getName() + " shutdown.");
			}

			Log.info("Disposing configurations...");
			conf = null;
			initialized = false;
			status = RunStatus.STOPPED;
			Log.info("Audit4j shutdown completed.");
		} else {
			Log.info("No active Audit4j instance. Cancelling shutdown request.");
		}
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
	 * Gets the config context.
	 *
	 * @return the config context
	 */
	static ConcurrentConfigurationContext getConfigContext() {
		return configContext;
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
	 */
	public boolean isInitialized() {
		return initialized;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 * @since 2.2.0
	 */
	public static RunStatus getStatus() {
		return status;
	}

	/**
	 * Private singalton.
	 */
	private Context() {
		// Nothing here.
	}

}
