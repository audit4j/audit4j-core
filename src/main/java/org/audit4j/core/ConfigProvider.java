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

import java.io.InputStream;

import org.audit4j.core.exception.ConfigurationException;

/**
 * The Interface ConfigProvider.
 * The provider Interface for various configuration provider implementations.
 *
 * @param <T> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public interface ConfigProvider<T> {

    /**
     * Read configuration form source.
     * 
     * @param filePath
     *            the file path
     * @return the configuration object
     * @throws ConfigurationException
     *             the configuration exception
     */
    T readConfig(final String filePath) throws ConfigurationException;

    /**
     * Read configuration from source.
     *
     * @param fileAsStream the file as stream using {@link InputStream}
     * @return the t
     * @throws ConfigurationException the configuration exception
     */
    T readConfig(final InputStream fileAsStream) throws ConfigurationException;

    /**
     * Generate dummy config.
     * 
     * @param config
     *            the config
     * @param filePath
     *            the file path
     * @throws ConfigurationException
     *             the configuration exception
     */
    void generateConfig(T config, final String filePath) throws ConfigurationException;
}
