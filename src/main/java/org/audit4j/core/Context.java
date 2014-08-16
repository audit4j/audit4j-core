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

import java.util.List;
import java.util.Map;

import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.exception.ValidationException;
import org.audit4j.core.handler.Handler;

/**
 * The Class Context.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public class Context {

	/** The initialized. */
	private static boolean initialized = false;

	/** The conf. */
	private static Configuration conf;

	/**
	 * Load config.
	 */
	private static void loadConfig() {
		Log.info("Loading Configurations...");
		try {
			conf = ConfigUtil.readConfig();
		} catch (ConfigurationException e) {
			try {
				TroubleshootManager.troubleshootConfiguration(e);
				conf = ConfigUtil.readConfig();
			} catch (TroubleshootException e2) {
				throw new InitializationException("Initialization failed.!!", e2);
			} catch (ConfigurationException e1) {
				throw new InitializationException("Initialization failed.!!", e1);
			}
		}
	}

	/**
	 * Inits the.
	 */
	public static void init() {
		if (!initialized) {
			Log.info("Initializing Audit4j...");
			loadConfig();
			Log.info("Validating Configurations...");
			
			if (conf == null) {
				throw new InitializationException("initialization failed.!!");
			} else {
				try {
					ValidationManager.validateConfigurations(conf);
				} catch (ValidationException e1) {
					throw new InitializationException("initialization failed.!!", e1);
				}
				for (Map.Entry<String, String> entry : conf.getProperties().entrySet()) {
					if (System.getProperties().containsKey(entry.getValue())) {
						conf.getProperties().put(entry.getKey(), System.getProperty(entry.getValue()));
					}
				}

				Log.info("Initializing Handlers..");
				for (Handler handler : conf.getHandlers()) {
					try {
						handler.setProperties(conf.getProperties());
						handler.init();
						Log.info(handler.getClass().getName() + " Initialized.");
					} catch (InitializationException e) {
						throw new InitializationException("initialization failed.!!", e);
					}
				}
				initialized = true;
				Log.info("Audit4j initialized.");
			}
		}
	}

	/**
	 * Gets the config.
	 * 
	 * @return the config
	 */
	public static Configuration getConfig() {
		init();
		return conf;
	}

	/**
	 * Gets the handlers.
	 * 
	 * @return the handlers
	 */
	public List<Handler> getHandlers() {
		return getConfig().getHandlers();
	}

	/**
	 * Checks if is initialized.
	 *
	 * @return true, if is initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}
}
