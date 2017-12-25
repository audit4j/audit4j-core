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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.audit4j.core.exception.ConfigurationException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

/**
 * The Class YAMLConfigProvider.
 *
 * @param <T> the generic type
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @since 2.4.0
 */
public class YAMLConfigProvider<T> implements ConfigProvider<T> {

    /** The clazz. */
    private final Class<T> clazz;

    /**
     * Instantiates a new yAML config provider.
     *
     * @param clazz the clazz
     */
    public YAMLConfigProvider(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.ConfigProvider#readConfig(java.lang.String)
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public T readConfig(String filePath) throws ConfigurationException {
        try {
            YamlReader reader = new YamlReader(new FileReader(filePath));
            reader.getConfig().setClassTag(clazz.getSimpleName(), clazz);
            return (T) reader.read();
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("Configuration Exception", "CONF_001", e);
        } catch (YamlException e) {
            throw new ConfigurationException("Configuration Exception", "CONF_002", e);
        }
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.ConfigProvider#readConfig(java.io.InputStream)
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public T readConfig(InputStream fileAsStream) throws ConfigurationException {
        InputStreamReader streamReader = new InputStreamReader(fileAsStream);
        try {
            YamlReader reader = new YamlReader(streamReader);
            reader.getConfig().setClassTag(clazz.getSimpleName(), clazz);
            return (T) reader.read();
        } catch (YamlException e) {
            throw new ConfigurationException("Configuration Exception", "CONF_002", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void generateConfig(T config, String filePath) throws ConfigurationException {
        YamlWriter writer;
        try {
            writer = new YamlWriter(new FileWriter(filePath));
            writer.getConfig().setClassTag(clazz.getSimpleName(), clazz);
            writer.write(Configuration.DEFAULT);
            writer.close();
        } catch (IOException e) {
            throw new ConfigurationException("Configuration Exception", "CONF_002");
        }
    }


}
