/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
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
import org.audit4j.core.handler.Handler;

/**
 * The Class Context.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
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
		System.out.println("Loading Configurations..");
		try {
			conf = ConfigUtil.readConfig();
		} catch (ConfigurationException e) {
			try {
				TroubleshootManager.troubleshootConfiguration(e);
				conf = ConfigUtil.readConfig();
			} catch (TroubleshootException e2) {
				throw new InitializationException("Configuration initialization failed.", e2);
			} catch (ConfigurationException e1) {
				throw new InitializationException("Configuration initialization failed.", e1);
			}
		}
	}

	/**
	 * Inits the.
	 */
	private static void init() {
		if (!initialized) {
			System.out.println("Initializing Audit4j..");
			loadConfig();
			if (conf == null) {
				throw new InitializationException("Configuration initialization failed.");
			} else {
				for (Map.Entry<String, String> entry : conf.getProperties().entrySet()) {
				    if (System.getProperties().containsKey(entry.getValue())) {
						conf.getProperties().put(entry.getKey(), System.getProperty(entry.getValue()));
					}
				}
				
				System.out.println("Initializing Handlers..");
				for (Handler handler : conf.getHandlers()) {
					try {
						handler.setProperties(conf.getProperties());
						handler.init();
						System.out.println(handler.getClass().getName() + " Initialized.");
					} catch (InitializationException e) {

					}
				}
				initialized = true;
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
}
